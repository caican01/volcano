package BatchRecommend;

import conf.MongoConfig;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import context.mrsSparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import org.bson.Document;
import scala.Tuple2;
import utils.JdbcUtil;
import utils.MongoDBUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 离线推荐部分
 * 根据用户电影评分数据统计电影信息，包括历史最热、近期最热、电影平均评分
 */
public class statisticRecommendMovies extends mrsSparkContext {

    //说明：
    /**
     * 建一张电影表：除了表的原有字段，还要添加历史热度、平均评分这两个字段，默认值分别为0，0.0，
     * 然后所计算出的历史热度、平均评分的值再更新到电影表movie中。
     */
    /**
     * 求出每部电影的历史热度         //实现中似乎有一点问题，因为现在计算出了每部电影热度后直接更新到电影表相应的字段中了，然后取历史热度的前五名也在电影网站项目中直接对电影表按历史热度字段倒序排序即可得到，所以没必要在这里做排序，这里只需要好好做好统计然后更新到数据库的工作就行了。
     * @param RatingsRDD
     */
    public static void rankMoviesForHistoryHeat(JavaRDD<Document> RatingsRDD){

        JavaPairRDD<Integer, Integer> historyMovieHeatPairRDD = RatingsRDD.mapPartitionsToPair(new PairFlatMapFunction<Iterator<Document>, Integer, Integer>() {//String->(Integer,Integer)
            @Override
            public Iterator<Tuple2<Integer, Integer>> call(Iterator<Document> stringIterator) throws Exception {
                List<Tuple2<Integer, Integer>> list = new LinkedList<>();
                while (stringIterator.hasNext()) {
                    Document document = stringIterator.next();
                    if (document.size() == 5)
                        list.add(new Tuple2<>(Integer.parseInt(document.get("movieId").toString()), 1));
                }
                return list.iterator();
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {//(Integer,Integer)->(Integer,Integer)  (movieId,historyMovieHeat)
            @Override
            public Integer call(Integer num1, Integer num2) throws Exception {
                return num1 + num2;
            }
        });
        //将结果保存到mysql数据库表movie
        historyMovieHeatPairRDD.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, Integer>>>() {
            @Override
            public void call(Iterator<Tuple2<Integer, Integer>> tuple2Iterator) throws Exception {
                while(tuple2Iterator.hasNext()){
                    Tuple2<Integer,Integer> t = tuple2Iterator.next();
                    JdbcUtil.updateToMysql("movie","movie_id",t._1,"history_heat",t._2);
                }
            }
        });
    }

    /**
     * 求出每部电影的近期热度（以月为一个统计单位，获取本月所有播放过的所有电影，并按电影ID分组，统计每部电影的播放次数，按播放次数倒序排序，播放次数即热度）
     * 在服务器上编辑好更新近期热度电影的脚本，在每月的倒数第十分钟自动执行该脚本，以更新近期电影热度信息
     * @param RatingsRDD
     */
    public static void rankMoviesForRecentHeat(JavaRDD<Document> RatingsRDD){

        //近期全部记录
//        JavaPairRDD<Integer, Integer> sortedRecentMovieHeatPairRDD =
        //近期前20的记录
        List<Tuple2<Integer, Integer>> list = RatingsRDD.filter(new Function<Document, Boolean>() {  //过滤，得到本月所有的评分数据
            @Override
            public Boolean call(Document document) throws Exception {
                Date currentDate = null;
                Date ratingDate = null;
                Boolean flag = false;
                if (document.size() == 5) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //当前时间,单位为毫秒
                    long currentTimeMillis = System.currentTimeMillis();
                    currentDate = new Date(currentTimeMillis);
                    String currentDateFormat = formatter.format(ratingDate);
                    //每条评分数据产生的时间（单位为秒,需转换为毫秒再做下一步操作）
                    long ratingTimeMillis = Long.parseLong(document.get("timestamp").toString());
                    ratingDate = new Date(Long.parseLong(String.valueOf(ratingTimeMillis * 1000)));
                    String ratingDateFormat = formatter.format(ratingDate);
                    //基于上面的描述,这里的过滤规则是判断当前评分数据是否是本月产生的
                    //具体规则：获取当前时间(即脚本执行时的时间)，提取年份和月份（yyyy-MM）,将其与每条评分数据中的年月份进行比较，若相同返回true,否则返回false
                    //以此获取所有本月产生的评分数据
                    if (currentDate != null && ratingDate != null && currentDateFormat.substring(0, 7).equals(ratingDate.toString().substring(0, 7)))
//                    if (ratingDate != null && "1998-04".equals(ratingDateFormat.substring(0, 7)))
                        flag = true;
                }
                return flag;
            }
        }).mapPartitionsToPair(new PairFlatMapFunction<Iterator<Document>, Integer, Integer>() { //String->(movieId,1)
            @Override
            public Iterator<Tuple2<Integer, Integer>> call(Iterator<Document> stringIterator) throws Exception {
                List<Tuple2<Integer, Integer>> list = new LinkedList<>();
                while (stringIterator.hasNext()) {
                    Document document = stringIterator.next();
                    if (document.size() == 5) {
                        list.add(new Tuple2<>(Integer.parseInt(document.get("movieId").toString()), 1));
                    }
                }
                return list.iterator();
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {  //(movieId,1)->(movieId,sum)
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).mapToPair(new PairFunction<Tuple2<Integer, Integer>, Integer, Integer>() {  //(movieId,sum)->(sum,movieId)
            @Override
            public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> integerIntegerTuple2) throws Exception {
                return new Tuple2<>(integerIntegerTuple2._2, integerIntegerTuple2._1);
            }
        }).sortByKey(false). //按播放次数降序排序
                mapToPair(new PairFunction<Tuple2<Integer, Integer>, Integer, Integer>() {//(sum,movieId)->(movieId,sum)
            @Override
            public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> integerIntegerTuple2) throws Exception {
                return new Tuple2<>(integerIntegerTuple2._2, integerIntegerTuple2._1);
            }
        }).take(20);

        try {
            //插入数据前先删除之前的所有记录
            JdbcUtil.deleteAllDataFromTable("recentMovieHeatRankedList");
            //这是取前20
            for (Tuple2<Integer,Integer> t:list){
                JdbcUtil.insert2ParametersIntoMysql("recentMovieHeatRankedList",t._1,t._2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //将结果保存到mysql数据库表recentMovieHeatRankedList(这是将近期的所有电影按降序排序后存入数据库)
//        sortedRecentMovieHeatPairRDD.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, Integer>>>() {
//            @Override
//            public void call(Iterator<Tuple2<Integer, Integer>> tuple2Iterator) throws Exception {
//                while(tuple2Iterator.hasNext()){
//                    Tuple2<Integer, Integer> t = tuple2Iterator.next();
//                    JdbcUtil.insert2ParametersIntoMysql("recentMovieHeatRankedList",t._1,t._2);
//                }
//            }
//        });

    }

    /**
     * 统计每部电影的平均评分（每个用户对该电影的评分之和/对该电影进行评分的用户数）
     * @param RatingsRDD
     */
    public static void computeEachMovieAverageRating(JavaRDD<Document> RatingsRDD){
        RatingsRDD.mapPartitionsToPair(new PairFlatMapFunction<Iterator<Document>, Integer, Double>() {  //String->(movieId,avgRating)
            @Override
            public Iterator<Tuple2<Integer, Double>> call(Iterator<Document> stringIterator) throws Exception {
                List<Tuple2<Integer, Double>> list = new LinkedList<>();
                while(stringIterator.hasNext()){
                    Document document = stringIterator.next();
                    if (document.size() == 5)
                        list.add(new Tuple2<Integer, Double>(Integer.parseInt(document.get("movieId").toString()),Double.parseDouble(document.get("rating").toString())));
                }
                return list.iterator();
            }
        }).groupByKey().mapToPair(new PairFunction<Tuple2<Integer,Iterable<Double>>, Integer, Double>() {
            @Override
            public Tuple2<Integer, Double> call(Tuple2<Integer, Iterable<Double>> integerIterableTuple2) throws Exception {
                double sum = 0.0;  //总评分
                int count = 0;  //参评用户数
                Iterator<Double> ratings = integerIterableTuple2._2().iterator();
                while(ratings.hasNext()){
                    sum += ratings.next();
                    count++;
                }
                //平均分保留一位小数
                return new Tuple2<Integer,Double>(integerIterableTuple2._1,Double.parseDouble(String.format("%.1f",sum/count)));
            }
        }).foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, Double>>>() {
            @Override
            public void call(Iterator<Tuple2<Integer, Double>> tuple2Iterator) throws Exception {
                while (tuple2Iterator.hasNext()){
                    Tuple2<Integer, Double> t = tuple2Iterator.next();
                    //将数据保存到mysql数据库表movie
                    JdbcUtil.updateToMysql("movie","movie_id",t._1,"average_rating",t._2);
                }
            }
        });
    }

    public static void main(String[] args){

        String applicationName = "statisticRecommendMovies";
        String master = "local[*]";

        //获取JavaSparkContext实例
        JavaSparkContext jsc = mrsSparkContext.getMongoJavaSparkContext(applicationName,master, MongoConfig.ratingsMongoInputKey,MongoConfig.ratingsMongoInputValue,MongoConfig.ratingsMongoOutputKey,MongoConfig.ratingsMongoOutputValue);
        //获取完整的电影评分数据
        JavaRDD<Document> RatingsRDD = MongoDBUtil.getFromMongoDB(jsc);

        //调用rankMoviesForHistoryHeat方法，求出每部电影的历史热度
        rankMoviesForHistoryHeat(RatingsRDD);
        //调用rankMoviesForRecentHeat方法，求出每部电影的近期热度
//        rankMoviesForRecentHeat(RatingsRDD);
        //调用computeEachMovieAverageRating方法，统计每部电影的平均评分
        computeEachMovieAverageRating(RatingsRDD);

        //释放资源
        jsc.stop();
    }
}