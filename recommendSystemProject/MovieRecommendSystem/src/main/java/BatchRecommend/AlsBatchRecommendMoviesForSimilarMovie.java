package BatchRecommend;

import context.mrsSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;
import utils.JdbcUtil;

import java.sql.SQLException;
import java.util.*;

/**
 * 离线推荐部分
 * 为每部电影推荐与其最相似的N部电影
 */
public class AlsBatchRecommendMoviesForSimilarMovie extends mrsSparkContext {

    /**
     * 获取每部电影的topK相似电影
     * 按同一电影与其他电影的相似度降序排序
     * @param simsRDD
     * @return
     */
    public static JavaRDD<Tuple2<Integer, String>> getTopFiveSimilarMovies(JavaRDD<String> simsRDD,final int K){

        JavaRDD<Tuple2<Integer, String>> topKSimsMovie = simsRDD.map(new Function<String, Tuple2<Integer, String>>() {
            @Override
            public Tuple2<Integer, String> call(String input) throws Exception {
                //数据格式形如: 1_2:0.8,3:0.5,4:0.6,5:0.5,6:0.7
                String[] keyAndValue = input.split("_");
                StringBuffer buffer = new StringBuffer();
                Integer key = Integer.parseInt(keyAndValue[0]);
                String[] split = keyAndValue[1].split(",");
                Arrays.sort(split, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        int index1 = o1.indexOf(":");
                        int index2 = o2.indexOf(":");
                        if (Double.parseDouble(o1.substring(index1 + 1)) > Double.parseDouble(o2.substring(index2 + 1)))
                            return -1;
                        else if (Double.parseDouble(o1.substring(index1 + 1)) < Double.parseDouble(o2.substring(index2 + 1)))
                            return 1;
                        else return 0;
                    }
                });
                int length = split.length;
                if (length <= K) {
                    for (int i = 0; i < length; i++)
                        if (key != Integer.parseInt(split[i].substring(0, split[i].indexOf(":")))) {
                            if (i == length - 1) buffer.append(split[i]);
                            else buffer.append(split[i] + ",");
                        }
                } else {
                    int count = 0;
                    for (int i = 0; i < split.length; i++) {
                        if (key != Integer.parseInt(split[i].substring(0, split[i].indexOf(":")))) {
                            count++;
                            if (count == K) {
                                buffer.append(split[i]);
                                break;
                            } else buffer.append(split[i] + ",");
                        }
                    }
                }
                return new Tuple2<>(key, buffer.toString());
            }
        });
        return topKSimsMovie;
    }

    /**
     * 将每部电影的topK的相似电影保存到mysql数据库
     * 数据的格式: movieId  movieId1:sim1,movieId2:sim2,movieId3:sim3,...
     * @param topFiveSimilarityMoviesRDD
     * @param tableName
     */
    public static void saveMovieSimilarity(JavaRDD<Tuple2<Integer, String>> topFiveSimilarityMoviesRDD, final String tableName){

        topFiveSimilarityMoviesRDD.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, String>>>() {
            @Override
            public void call(Iterator<Tuple2<Integer, String>> iteratorIterator) throws Exception {
                while (iteratorIterator.hasNext()){
                    Tuple2<Integer, String> eachMovieSimilarity = iteratorIterator.next();
                    //保存每条相似度记录到mysql数据库中
                    JdbcUtil.insert2ParametersIntoMysql(tableName,eachMovieSimilarity._1,eachMovieSimilarity._2);
                }
            }
        });
    }

    public static void main(String[] args){

        String application = "AlsBatchRecommendMoviesForSimilarMovie"; //应用名称
        String master = "local[*]";
        String tableName = "topKSimilarMovies"; //数据库表
        //电影相似度在hdfs上的存储路径
        String simsPath = "D:\\idea_workspace\\MovieRecommendSystem\\src\\similarity";
        //取topK的相似电影
        //这里给推荐10部，然后电影网站那边的操作取前5即可。
        int K = 10;

        //获取JavaSparkContext实例
        JavaSparkContext jsc = mrsSparkContext.getCommJavaSparkContext(application, master);
        //从hdfs读取电影相似度数据（测试阶段是在本地读取，因为数据存储在本地）
        JavaRDD<String> simsRDD = jsc.textFile(simsPath);
        //获取每部电影的topK相似电影
        JavaRDD<Tuple2<Integer, String>> topTenSimilarMovies = getTopFiveSimilarMovies(simsRDD,K);

        try {
            //清空表
            JdbcUtil.deleteAllDataFromTable(tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //将每部电影的topK相似电影保存到mysql数据库
        saveMovieSimilarity(topTenSimilarMovies,tableName);
    }
}