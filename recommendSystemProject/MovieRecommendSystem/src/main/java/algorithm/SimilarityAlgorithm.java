package algorithm;

import context.mrsSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.jblas.DoubleMatrix;
import scala.Tuple2;
import scala.Tuple3;
import java.util.*;

/**
 * 算法之每部电影与其他所有电影之间的相似度计算实现
 */
public class SimilarityAlgorithm {

    /**
     * 通过电影特征矩阵，计算两部电影的相似度，计算公式是：余弦相似度=两个向量的点积/（两个向量的模的乘积）
     * @param vector1 电影1的特征矩阵
     * @param vector2 电影2的特征矩阵
     * @return
     */
    public static double computeCosineSimilarity(DoubleMatrix vector1, DoubleMatrix vector2){
        return vector1.dot(vector2)/(vector1.norm2()*vector2.norm2());
    }

    /**
     * 计算每部电影与其他所有电影的相似度
     * @param model
     */
    public static JavaRDD<Tuple3<Integer, Integer, Double>> computeEachMovieToOthersCosineSimilarity(MatrixFactorizationModel model){

        //获取每部电影的特征矩阵
        JavaRDD<Tuple2<Integer, DoubleMatrix>>  productsVectorRDD = model.productFeatures().toJavaRDD().
                //(Object,double[])->(Integer,DoubleMatrix)
                mapPartitions(new FlatMapFunction<Iterator<Tuple2<Object, double[]>>, Tuple2<Integer, DoubleMatrix>>() {
                    @Override
                    public Iterator<Tuple2<Integer, DoubleMatrix>> call(Iterator<Tuple2<Object, double[]>> tuple2Iterator) throws Exception {
                        List<Tuple2<Integer, DoubleMatrix>> list = new LinkedList<>();
                        while (tuple2Iterator.hasNext()) {
                            Tuple2<Object, double[]> t = tuple2Iterator.next();
                            DoubleMatrix vector = new DoubleMatrix(t._2);
                            list.add(new Tuple2<>((Integer) t._1, vector));
                        }
                        return list.iterator();
                    }
                });

        //cartesian()计算笛卡尔积，productsVectorRDD X productsVectorRDD
        JavaRDD<Tuple3<Integer, Integer, Double>> productSimilarityRDD = productsVectorRDD.cartesian(productsVectorRDD).
        //计算两部电影之间的相似度
        map(new Function<Tuple2<Tuple2<Integer, DoubleMatrix>, Tuple2<Integer, DoubleMatrix>>, Tuple3<Integer, Integer, Double>>() {
            @Override
            public Tuple3<Integer, Integer, Double> call(Tuple2<Tuple2<Integer, DoubleMatrix>, Tuple2<Integer, DoubleMatrix>> tuple2Tuple2Tuple2) throws Exception {
                double cosineSimilarity = 0.0;
                //计算每部电影与其他电影的相似度，所以这里要排除掉自己与自己的相似度计算
                if (tuple2Tuple2Tuple2._1._1 != tuple2Tuple2Tuple2._2._1) {
                    cosineSimilarity = computeCosineSimilarity(tuple2Tuple2Tuple2._1._2, tuple2Tuple2Tuple2._2._2);
                }
                return new Tuple3<>(tuple2Tuple2Tuple2._1._1, tuple2Tuple2Tuple2._2._1, cosineSimilarity);
            }
        });

        return productSimilarityRDD;
    }

    /**
     * 将电影的相似度数据保存到hdfs
     * movieId,string
     * @param productSimilarityRDD
     */
    public static void saveSimilarityToHdfs(JavaRDD<Tuple3<Integer, Integer, Double>> productSimilarityRDD,String simsSavePath){

        /**
         * 因为productSimilarityRDD得到的结果是形如：
         * 1    2   0.8
         * 1    3   0.5
         * 1    4   0.6
         * 1    5   0.5
         * 1    6   0.7
         * 2    1   0.8
         * 2    3   0.6
         * ...
         * 这样的数据，现在想将这些数据按第一个字段分组，然后将每一组的记录整合成一条记录，如：
         * 1    2   0.8
         * 1    3   0.5
         * 1    4   0.6
         * 1    5   0.5
         * 1    6   0.7
         * 整合成：1_2:0.8,3:0.5,4:0.6,5:0.5,6:0.7
         *
         */
        JavaRDD<String> simsJavaRDD = productSimilarityRDD.groupBy(new Function<Tuple3<Integer, Integer, Double>, Integer>() {
            @Override
            public Integer call(Tuple3<Integer, Integer, Double> integerIntegerDoubleTuple3) throws Exception {
                return integerIntegerDoubleTuple3._1();
            }
        }).map(new Function<Tuple2<Integer, Iterable<Tuple3<Integer, Integer, Double>>>, String>() {
            @Override
            public String call(Tuple2<Integer, Iterable<Tuple3<Integer, Integer, Double>>> integerIterableTuple2) throws Exception {
                Iterator<Tuple3<Integer, Integer, Double>> sims = integerIterableTuple2._2.iterator();
                StringBuffer buffer = new StringBuffer();
                buffer.append(integerIterableTuple2._1+"_");
                //数据格式:1_2:0.8,3:0.5,4:0.6,5:0.5,6:0.7
                while (sims.hasNext()) {
                    Tuple3<Integer, Integer, Double> t = sims.next();
                    if (t._1()!=t._2()) {
                        buffer.append(t._2() + ":" + String.format("%.4f", t._3()) + ",");
                    }
                }
                String s = buffer.toString();
                return s.substring(0, s.length() - 1);
            }
        });

        //将电影相似度保存到hdfs
        simsJavaRDD.saveAsTextFile(simsSavePath);
    }

    public static void main(String[] args){

        String application = "ComputeMoviesSimilarity";
        String master = "local[*]";
        //model保存到hdfs上（现阶段测试，保存路径是在本地）
        String modelPath = "D:\\idea_workspace\\MovieRecommendSystem\\src\\model";
        //相似度数据保存到hdfs上（现阶段测试先保存到本地）
        String simSavePath = "D:\\idea_workspace\\MovieRecommendSystem\\src\\similarity";

        //获取JavaSparkContext实例
        JavaSparkContext jsc = mrsSparkContext.getCommJavaSparkContext(application, master);
        //加载训练好的模型
        MatrixFactorizationModel model = MatrixFactorizationModel.load(jsc.sc(), modelPath);
        //计算并得到电影相似度
        JavaRDD<Tuple3<Integer, Integer, Double>> productSimilarityRDD = computeEachMovieToOthersCosineSimilarity(model);
        //将电影的相似度数据保存到hdfs
        saveSimilarityToHdfs(productSimilarityRDD,simSavePath);
    }

}