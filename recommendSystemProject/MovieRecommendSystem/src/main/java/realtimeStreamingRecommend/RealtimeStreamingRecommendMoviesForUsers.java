package realtimeStreamingRecommend;

import algorithm.RealtimeAlgorithm;
import com.mongodb.DBObject;
import context.mrsSparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;
import java.util.*;

/**
 * 实时推荐部分
 * 每当用户对电影进行评分后即会触发实时推荐（调用实时推荐算法）
 */
public class RealtimeStreamingRecommendMoviesForUsers extends mrsSparkContext {

    /**
     * 将电影相似度加载到内存（二维哈希表中）
     * @param simsRDD
     * @return
     */
    public static Hashtable<Integer,Hashtable<Integer,Double>> saveMoviesSimilarityToHashTable(JavaRDD<String> simsRDD){

        //二维哈希表
        Hashtable<Integer,Hashtable<Integer,Double>> table = new Hashtable<>();
        //执行action算子，将数据加载到内存（二维哈希表）中
        List<String> list = simsRDD.collect();

        for (String str : list){
            Hashtable<Integer,Double> subTable = new Hashtable<>();
            String[] keyAndValue = str.split("_");
            if (keyAndValue.length==2) {
                Integer key = Integer.parseInt(keyAndValue[0].trim());
                String[] split = keyAndValue[1].split(",");
                for (String s : split) {
                    String[] simMovie = s.split(":");
                    subTable.put(Integer.parseInt(simMovie[0]), Double.parseDouble(simMovie[1]));
                }
                table.put(key, subTable);
            }
        }
        return table;
    }

    public static void main(String[] args){

        //应用名称
        String application = "RealTimeStreamingRecommendMoviesForUsers";
        String master = "local[*]";
        //电影相似度在hdfs上存储路径（现阶段测试，数据存储在本地上）
        String simsPath = "D:\\idea_workspace\\MovieRecommendSystem\\src\\similarity";
        //检查点路径设置在hdfs上（现阶段测试，设置在本地）
        String checkpointDir = "D:\\idea_workspace\\MovieRecommendSystem\\src\\checkpoint";
        //zookeeper地址
        String zk = "volcano01.cc.com:2181";
        //消费者组
        String groupId = "sparkRealTimeRecommend";
        //topic(key->topic名称，value->topic分区数)
        Map<String,Integer> topics = new HashMap<>();
        topics.put("userRating",3);  //只创建一个topic来存储评分数据：topic名称->userRating

        /**
        //获取SparkSession实例
        final SparkSession session = SparkSession.builder()
                .appName(application)
                .master(master)
                .config(MongoConfig.ratingsMongoInputKey, MongoConfig.ratingsMongoInputValue)
                .config(MongoConfig.ratingsMongoOutputKey, MongoConfig.ratingsMongoOutputValue)
                .getOrCreate();

        //获取JavaSparkContext实例,通过JavaSparkContext.fromSparkContext()方法将sparkContext转换为JavaSparkContext
        JavaSparkContext jsc = JavaSparkContext.fromSparkContext(session.sparkContext());
         */

        //取JavaSparkContext实例
        final JavaSparkContext jsc = mrsSparkContext.getCommJavaSparkContext(application, master);
        //获取JavaStreamingContext实例    Batch Duration大小为5s
        JavaStreamingContext jssc = new JavaStreamingContext(jsc, Durations.seconds(5));

        //从hdfs中读取电影相似度数据（先测试阶段从本地读取）
        JavaRDD<String> simsRDD = jsc.textFile(simsPath);
        //将电影相似度数据加载到二维哈希表中，并创建广播变量广播出去（这里为了查找的效率，将相似度数据以二维哈希表的形式加载到内存中，典型的以空间换取时间）
        Hashtable<Integer, Hashtable<Integer, Double>> hashtable = saveMoviesSimilarityToHashTable(simsRDD);
        //创建电影相似度广播变量
        final Broadcast<Hashtable<Integer, Hashtable<Integer, Double>>> broadcast = jsc.broadcast(hashtable);
        //设置检查点
        jssc.checkpoint(checkpointDir);

        //创建实时流处理对象，接收Kafka的message(用户评分数据)
        JavaPairReceiverInputDStream<String, String> DStream = KafkaUtils.createStream(jssc, zk, groupId, topics, StorageLevel.MEMORY_AND_DISK());

        //Kafka的message是键值对形式的，所以得到的DStream中的RDD中的记录也是键值对形式的
        DStream.foreachRDD(new VoidFunction<JavaPairRDD<String, String>>() {

            @Override
            public void call(JavaPairRDD<String, String> stringStringJavaPairRDD) throws Exception {

                stringStringJavaPairRDD.foreachPartition(new VoidFunction<Iterator<Tuple2<String, String>>>() {

                    @Override
                    public void call(Iterator<Tuple2<String, String>> tuple2Iterator) throws Exception {
                        int K = 5; //推荐的电影数目
                        String topKSimilarMoviesTableName = "topKSimilarMovies"; //相似度电影表名
                        String moviesRecommendForAllUsersTableName = "moviesRecommendForAllUsers"; //用户推荐表名
                        double minSimilarity = 0.6; //相似度下界（即相似度应不小于该值）
                        while (tuple2Iterator.hasNext()){
                            //Kafka的消息是键值对形式的，键是"UserRatingData0/1/2"，值是评分数据
                            Tuple2<String, String> userLogRecord = tuple2Iterator.next();
                            //过滤得到用户日志中的评分数据（topic有三个分区，每个分区的键是唯一的）
                            if (userLogRecord._1.equals("userRatingData0")||
                                userLogRecord._1.equals("userRatingData1")||
                                userLogRecord._1.equals("userRatingData2"))
                            {
                                //得到评分记录，格式为:userId_movieId_rating_timestamp
                                String[] split = userLogRecord._2.split("_");
                                //最近的K次评分
                                List<DBObject> recentKRatings = RealtimeAlgorithm.getRecentKRatings(K, Integer.parseInt(split[0]),split[0],split[1],split[2],split[3]);
                                //最相似的K部电影
                                String topKSimsMovies = RealtimeAlgorithm.getTopKSimsMovie(topKSimilarMoviesTableName, "movie_id", Integer.parseInt(split[1]));
                                //计算得到候选电影的推荐强度
                                List<String> candidateMoviesRecommendStrength = RealtimeAlgorithm.computeCandidateMoviesRecommendedStrength(recentKRatings, topKSimsMovies, broadcast.value(), minSimilarity,K);
                                for (String str:candidateMoviesRecommendStrength)
                                    System.out.println("候选电影推荐强度: "+str);
                                //获取上一次的推荐结果（这里用户推荐表的存储我使用了mysql，其实对于实时系统而言在数据量不大的情况下，redis才是更好的选择，后期如果有精力的话，再修改架构，使用redis替换掉mysql）
                                String lastTopKRecommendedMovies = RealtimeAlgorithm.getTopKRecommendedMovies(moviesRecommendForAllUsersTableName, "user_id", Integer.parseInt(split[0]));
                                //更新推荐结果
                                RealtimeAlgorithm.updateRecommendedResult(candidateMoviesRecommendStrength,lastTopKRecommendedMovies,Integer.parseInt(split[0]),moviesRecommendForAllUsersTableName);
                            }
                        }
                    }
                });
            }
        });

        try {
            //开始执行spark streaming程序
            jssc.start();
            //阻塞，等待程序执行结束
            jssc.awaitTermination();
            //释放资源
            jssc.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
