package recommendModel;

import conf.MongoConfig;
import context.mrsSparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.bson.Document;
import scala.Tuple2;
import utils.MongoDBUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TrainAndEvaluateRecommendModel extends mrsSparkContext{

    /**
     * 对模型进行评估
     * @param model
     * @param testFilePath
     * @return
     */
    public static double EvaluateTrainedModel(JavaSparkContext jsc, MatrixFactorizationModel model, String testFilePath){

        //获取测试数据集
        JavaRDD<String> stringTestRDD = jsc.textFile(testFilePath,5);

        //将测试数据集转换为Rating类型
        JavaRDD<Rating> ratingTestRDD = stringTestRDD.mapPartitions(new FlatMapFunction<Iterator<String>, Rating>() {
            @Override
            public Iterator<Rating> call(Iterator<String> stringIterator) throws Exception {
                List<Rating> list = new LinkedList<>();
                while (stringIterator.hasNext()) {
                    String[] split = stringIterator.next().split("\t");
                    if (split.length == 4) {
                        list.add(new Rating(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Double.parseDouble(split[2])));
                    }
                }
                return list.iterator();
            }
        });
        //Rating(userId,movieId,rating)->(userId,movieId)
        JavaPairRDD<Integer, Integer> userProductsPairRDD = ratingTestRDD.mapPartitionsToPair(new PairFlatMapFunction<Iterator<Rating>, Integer, Integer>() {
            @Override
            public Iterator<Tuple2<Integer, Integer>> call(Iterator<Rating> ratingIterator) throws Exception {
                List<Tuple2<Integer, Integer>> list = new LinkedList<>();
                while (ratingIterator.hasNext()) {
                    Rating rating = ratingIterator.next();
                    list.add(new Tuple2<>(rating.user(),rating.product()));
                }
                return list.iterator();
            }
        });
        //(userId,movieId)->((userId,movieId),rating)
        JavaPairRDD<Tuple2<Integer, Integer>, Double> predictedUserProductRatingsRDD = model.predict(userProductsPairRDD).
                mapPartitionsToPair(new PairFlatMapFunction<Iterator<Rating>, Tuple2<Integer, Integer>, Double>() {
                    @Override
                    public Iterator<Tuple2<Tuple2<Integer, Integer>, Double>> call(Iterator<Rating> ratingIterator) throws Exception {
                        List<Tuple2<Tuple2<Integer, Integer>, Double>> list = new LinkedList<>();
                        while (ratingIterator.hasNext()) {
                            Rating rating = ratingIterator.next();
                            list.add(new Tuple2<>(new Tuple2<>(rating.user(), rating.product()), rating.rating()));
                        }
                        return list.iterator();
                    }
                });
        //Rating(userId,movieId,rating)->((userId,movieId),rating)
        JavaPairRDD<Tuple2<Integer, Integer>, Double> realUserProductRatingsRDD = ratingTestRDD.mapPartitionsToPair(
                new PairFlatMapFunction<Iterator<Rating>, Tuple2<Integer, Integer>, Double>() {
            @Override
            public Iterator<Tuple2<Tuple2<Integer, Integer>, Double>> call(Iterator<Rating> ratingIterator) throws Exception {
                List<Tuple2<Tuple2<Integer, Integer>, Double>> list = new LinkedList<>();
                while (ratingIterator.hasNext()) {
                    Rating rating = ratingIterator.next();
                    list.add(new Tuple2<>(new Tuple2<>(rating.user(), rating.product()), rating.rating()));
                }
                return list.iterator();
            }
        });
        //((userId,movieId),rating)与((userId,movieId),rating)进行内连接->((userId,movieId),(predictedRating,realRating))->得到最后的均方差MSE
        Double MSE = predictedUserProductRatingsRDD.join(realUserProductRatingsRDD).mapPartitions(new FlatMapFunction<Iterator<Tuple2<Tuple2<Integer, Integer>, Tuple2<Double, Double>>>, Double>() {
            @Override
            public Iterator<Double> call(Iterator<Tuple2<Tuple2<Integer, Integer>, Tuple2<Double, Double>>> tuple2Iterator) throws Exception {
                List<Double> list = new LinkedList<>();
                while (tuple2Iterator.hasNext()) {
                    Tuple2<Tuple2<Integer, Integer>, Tuple2<Double, Double>> t = tuple2Iterator.next();
                    double err = t._2._1 - t._2._2;
                    list.add(err * err);
                }
                return list.iterator();
            }
        }).reduce(new Function2<Double, Double, Double>() {
            @Override
            public Double call(Double predictRating, Double realRating) throws Exception {
                return predictRating + realRating;
            }
        });

        long count = ratingTestRDD.count();

        return MSE/count;
    }

    public static void main(String[] args){

        String applicationName = "TrainAndEvaluateRecommendModel";
        String master = "local[*]";
        //movieLens数据集中原本就有训练集和测试集了，
        //训练集存储到mongodb（就是用来统计历史热度以及平均评分的那份数据）,测试集存储在hdfs（仅供测试之用）
        //这是测试集在hdfs上的存储路径（现阶段我将它放在本地，通过本地读取来测试）
        String testFilePath = "D:\\idea_workspace\\MovieRecommendSystem\\src\\data\\u1.test";
        String modelPath = "D:\\idea_workspace\\MovieRecommendSystem\\src\\model"; //模型存储路径（这里先存储在本地，后期再转到hdfs上）

        //获取JavaSparkContext对象
        JavaSparkContext jsc = mrsSparkContext.getMongoJavaSparkContext(applicationName, master, MongoConfig.ratingsMongoInputKey,MongoConfig.ratingsMongoInputValue,MongoConfig.ratingsMongoOutputKey,MongoConfig.ratingsMongoOutputValue);

        //从mongodb中获取训练集评分数据文件,并进行重分区，改变RDD分区个数
        JavaRDD<Document> TrainRDD = MongoDBUtil.getFromMongoDB(jsc).repartition(15);

        //将字符串类型的评分数据转换为Rating类型
        JavaRDD<Rating> RatingTrainRDD = TrainRDD.mapPartitions(new FlatMapFunction<Iterator<Document>, Rating>() {
            @Override
            public Iterator<Rating> call(Iterator<Document> stringIterator) throws Exception {
                List<Rating> list = new LinkedList<>();
                while (stringIterator.hasNext()) {
                    Document document = stringIterator.next();
                    if (document.size() == 5)
                        list.add(new Rating(Integer.parseInt(document.get("userId").toString()), Integer.parseInt(document.get("movieId").toString()),
                                Double.parseDouble(document.get("rating").toString())));
                }
                return list.iterator();
            }
        });


        //进行模型训练
        int rank = 120;  //隐藏因子的个数，表示用户特征矩阵的列数或者物品特征矩阵的行数
        int iterations = 15;  //迭代的次数
        double lambda = 0.001;  //正则化参数
        //使用MLlib库的ALS.train()方法进行模型训练
        MatrixFactorizationModel model = ALS.train(RatingTrainRDD.rdd(), rank, iterations, lambda);

        //对训练好的模型进行评估
        double MSE = EvaluateTrainedModel(jsc, model, testFilePath);
        //保存模型,save()方法中使用的上下文参数是SparkContext类型的，但是我的程序中用的是JavaSparkContext类型的，
        //通过sc()方法将JavaSparkContext转换为SparkContext类型，会不会有什么问题？？？
        //应该不会有问题的，应为sc是由jsc调用sc()方法得到的。
        //模型中保存到集群环境中，选择主节点进行保存

        //测试时注释掉，到生产环境中再放开,因为要将训练好并且评估好的模型保存到hdfs上
        if (MSE<=0.05)
            model.save(jsc.sc(),modelPath);

//        System.out.println("模型评估结果MSE : "+MSE);

    }
}
