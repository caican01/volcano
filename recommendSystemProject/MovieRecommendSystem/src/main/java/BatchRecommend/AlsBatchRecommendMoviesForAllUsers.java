package BatchRecommend;

import context.mrsSparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import scala.Tuple2;
import utils.JdbcUtil;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 离线推荐部分
 * 为每个用户推荐他可能感兴趣的topN电影
 */
public class AlsBatchRecommendMoviesForAllUsers extends mrsSparkContext {

    public static void main(String[] args){

        String applicationName = "AlsBatchRecommendMoviesForAllUsers";
        String master = "local[*]";
        final String tableName = "moviesRecommendForAllUsers";
        //model在hdfs上的存储路径（现测试阶段数据存储在本地）
        String modelPath = "D:\\idea_workspace\\MovieRecommendSystem\\src\\model";
        //为每个用户推荐的电影数
        int K = 5;

        //获取javasparkcontext实例
        JavaSparkContext jsc = mrsSparkContext.getCommJavaSparkContext(applicationName, master);

        //加载训练好的模型
        /**
         * javasparkcontext 上下文实例
         * modelPath 模型的存储路径
         */
        MatrixFactorizationModel model = MatrixFactorizationModel.load(jsc.sc(), modelPath);

        try {
            //清空表
            JdbcUtil.deleteAllDataFromTable(tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //为每个用户推荐5部电影,将推荐结果保存到mysql数据库表moviesRecommendForAllUsers
        //(离线部分，对实时性要求不是很高，所以用mysql数据库就行了，没必要使用redis等缓存类数据库)
        //后台取数据时再按平均评分降序排序取出前5的电影
        model.recommendProductsForUsers(K).toJavaRDD().
        mapPartitions(new FlatMapFunction<Iterator<Tuple2<Object, Rating[]>>, Tuple2<Integer, String>>() {

            @Override
            public Iterator<Tuple2<Integer, String>> call(Iterator<Tuple2<Object, Rating[]>> tuple2Iterator) throws Exception {

                List<Tuple2<Integer, String>> list = new LinkedList<>();
                while (tuple2Iterator.hasNext()) {
                    Tuple2<Object, Rating[]> t = tuple2Iterator.next();
                    StringBuffer buffer = new StringBuffer();
                    Rating[] ratings = t._2;
                    for(int i = 0;i<ratings.length;i++){
                        buffer.append((i==ratings.length-1)?ratings[i].product()+":"+
                                String.format("%.4f",ratings[i].rating()):ratings[i].product()+":"+String.format("%.4f",ratings[i].rating())+",");
                    }
                    list.add(new Tuple2<>((Integer) t._1,buffer.toString()));
                }
                return list.iterator();
            }
        }).foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, String>>>() {
            @Override
            public void call(Iterator<Tuple2<Integer, String>> tuple2Iterator) throws Exception {
                while (tuple2Iterator.hasNext()){
                    Tuple2<Integer, String> t = tuple2Iterator.next();
                    JdbcUtil.insert2ParametersIntoMysql(tableName,t._1,t._2);
                }
            }
        });
    }
}