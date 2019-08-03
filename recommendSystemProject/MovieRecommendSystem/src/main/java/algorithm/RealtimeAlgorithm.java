package algorithm;

import com.mongodb.*;
import conf.MongoConfig;
import utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 算法之实时推荐算法的实现
 */
public class RealtimeAlgorithm {

    /**
     * 获取specifiedUserId的最近K次评分
     * @param session
     * @param K K条评分记录
     * @param specifiedUserId 指定的用户
     * @param userId 本次参与评分的用户
     * @param movieId 评分的电影
     * @param rating 评分
     * @param timestamp 时间戳
     * @return
     * 本次从mongodb取该用户最近的前K-1次评分数据，加上本次的评分数据，作为最近的K次评分数据
     */
    /**
    public static List<Document> getRecentKRatings(SparkSession session, int K, Integer specifiedUserId, String userId, String movieId, String rating, String timestamp){

        JavaSparkContext jsc = JavaSparkContext.fromSparkContext(session.sparkContext());

        //从mongodb读取评分数据，使用条件下推，过滤得到该用户的评分数据
        JavaMongoRDD<Document> documentJavaMongoRDD = MongoSpark.load(jsc).withPipeline(Collections.singletonList(Document.parse("{$match:{userId:" + specifiedUserId + "}}")));
        //对评分数据按时间戳进行降序排序
        JavaRDD<Document> documentJavaRDD = documentJavaMongoRDD.sortBy(new Function<Document, Long>() {
            @Override
            public Long call(Document document) throws Exception {
                return (Long)document.get("timestamp");
            }
        }, false, documentJavaMongoRDD.getNumPartitions());
        //取前K-1条评分数据
        List<Document> ratingList = documentJavaRDD.take(K - 1);
        //加上最新的评分数据，一共K条评分数据，即为该用户最近的K条评分数据
        Document document = new Document();
        document.append("userId",userId);
        document.append("movieId",movieId);
        document.append("rating",rating);
        document.append("timestamp",timestamp);
        ratingList.add(document);

        //将最新的评分记录写到mongodb
        Dataset<Row> newRatingRecord = session.createDataFrame(Arrays.asList(document), RatingRecord.class);
        MongoSpark.write(newRatingRecord).mode(SaveMode.Append).save();

        return ratingList;
    }
     */

//    public static List<Document> getRecentKRatings(JavaSparkContext jsc, int K, Integer specifiedUserId, String userId, String movieId, String rating, String timestamp){
//
//        //从mongodb读取评分数据，使用条件下推，过滤得到该用户的评分数据,这样能够避免获取全量数据，提高获取数据的速度，减少内存压力
//        JavaMongoRDD<Document> documentJavaMongoRDD = MongoSpark.load(jsc).withPipeline(Collections.singletonList(Document.parse("{$match:{userId:" + specifiedUserId + "}}")));
//        //对评分数据按时间戳进行降序排序
//        JavaRDD<Document> documentJavaRDD = documentJavaMongoRDD.sortBy(new Function<Document, Long>() {
//            @Override
//            public Long call(Document document) throws Exception {
//                return (Long)document.get("timestamp");
//            }
//        }, false, documentJavaMongoRDD.getNumPartitions());
//        //取前K条评分数据
//        List<Document> ratingList = documentJavaRDD.take(K);
//        //这是用户最新的评分数据
//        Document document = new Document();
//        document.append("userId",userId);
//        document.append("movieId",movieId);
//        document.append("rating",rating);
//        document.append("timestamp",timestamp);
//        //判断用户的前K条评分数据中是否已经包含当前的最新评分数据
//        //若包含了，则不再加入，否则，删除前K的评分记录中最晚的那条，并将最新的评分记录添加进集合
//        //由于在电影网站端已经做了评分数据写入mongodb的操作，所以这里才可以直接获取，且这里不能再次将最新评分数据写入mongodb了，避免重复
//        if (ratingList.contains(document)==false) {
//            ratingList.remove(K-1);
//            ratingList.add(document);
//        }
//        return ratingList;
//    }

    /**
     * 获取前K次评分
     * @param K
     * @param specifiedUserId
     * @param userId
     * @param movieId
     * @param rating
     * @param timestamp
     * @return
     */
    public static List<DBObject> getRecentKRatings(int K, Integer specifiedUserId, String userId, String movieId, String rating, String timestamp){

        //创建mongodb连接
        MongoClient mongoClient = new MongoClient(MongoConfig.host, MongoConfig.port);
        //指定数据库
        DB db = mongoClient.getDB(MongoConfig.db);
        //指定集合
        DBCollection dbCollection = db.getCollection(MongoConfig.collection);
        //封装查询条件,根据用户Id来进行获取与该用户相关的所有数据
        DBObject query = new BasicDBObject();
        query.put("userId", specifiedUserId);
        //创建按时间戳进行降序排序的条件
        DBObject sortQuery = new BasicDBObject();
        sortQuery.put("timestamp",-1);
        DBCursor cursor = dbCollection.find(query).sort(sortQuery);
        //获取最近的前K-1条数据数据，再加上本次最新的评分数据，共K条评分数据
        List<DBObject> ratingList = null;
        if (cursor.size()<=K-1)
            ratingList = cursor.toArray();
        else
            ratingList = cursor.limit(K-1).toArray();
        DBObject object = new BasicDBObject();
        //存储的数据的字段类型分别为int_int_double_long
        //字段的类型不同，得到的是不同的记录的
        object.put("userId",Integer.parseInt(userId));
        object.put("movieId",Integer.parseInt(movieId));
        object.put("rating",Double.parseDouble(rating));
        object.put("timestamp",Long.parseLong(timestamp));
        ratingList.add(object);
        dbCollection.save(object);
        return ratingList;
    }

    /**
     * 获取与movieId相似的前K部电影，将这些电影作为候选电影
     * @param tableName
     * @param movieId
     * @return
     * @throws SQLException
     * (movieId movies)
     */
    public static String getTopKSimsMovie(String tableName,String condition,Integer movieId) throws SQLException {
        ResultSet resultSet = JdbcUtil.queryFromMysql(tableName,condition, movieId);
        String movieIds = null;
        while (resultSet.next()){
            movieIds = resultSet.getString(2);
            break;
        }
        return movieIds;
    }

    /**
     * 计算对数
     * @param input
     * @return
     */
    public static double lg(int input){
        return Math.log10(Math.max(input,1));
    }

    /**
     * 计算每部候选电影的推荐强度
     * @param topKRatingRecords  评分记录
     * @param topKSimsMovies  候选电影
     * @param  minSimilarity 相似度最小阈值
     * @return
     */
    public static List<String> computeCandidateMoviesRecommendedStrength(List<DBObject> topKRatingRecords,String topKSimsMovies,Hashtable<Integer, Hashtable<Integer, Double>> hashtable,double minSimilarity,int K){

        //相似电影格式:movieId1:sim1,movieId2:sim2,movieId3:sim3,movieId4:sim4,...
        String[] split = topKSimsMovies.split(",");
        //存储计算得到推荐强度的候选电影
        List<String> list = new LinkedList<>();
        //取出最相似的前K部电影
        List<String> topKSimsMoviesList = new ArrayList<>();
        if (split.length>K) {
            for (int i=0;i<K;i++) {
                topKSimsMoviesList.add(split[i]);
            }
        }else {
            for (int i=0;i<split.length;i++) {
                topKSimsMoviesList.add(split[i]);
            }
        }
        //相似电影作为候选电影
        for (String s : topKSimsMoviesList){
            //获取本部候选电影的movieId
            int index = s.indexOf(":");
            int movieId = Integer.parseInt(s.substring(0, index));
            //获取该movieId对应的电影相似度哈希列表
            Hashtable<Integer, Double> simsMoviesTable = hashtable.get(movieId);
            //用于累加sim X rating之积
            double sum = 0.0;
            //相似度大于等于最小阈值的个数
            int simsCount = 0;
            //rating大于等于3的个数（增强因子）
            int inCount = 0;
            //评分小于3的个数（减弱因子）
            int reCount = 0;
            for (int i =0;i<topKRatingRecords.size();i++){
                DBObject object = topKRatingRecords.get(i);
                Integer mid = (Integer) (object.get("movieId"));
                double sim = simsMoviesTable.get(mid);
                //两部电影的相似度大于等于最小阈值，才认为这两部电影是相似的
                if (sim>=minSimilarity){
                    simsCount++;
                    double rating = Double.parseDouble(object.get("rating").toString());
                    //因为模型对电影的预测评分是10分制的，而用户对电影的评分是5分制的，为了统一标准，所以这里用户的评分应该乘以2。
                    sum+=sim*(rating*2);
                    if (rating>=3.0) inCount++;
                    else reCount++;
                }
            }
            if (simsCount>0) {
                list.add(movieId + ":" + String.format("%.1f", sum / simsCount + lg(inCount) - lg(reCount)));
            } else {
                list.add(movieId + ":" + 0.0);
            }
        }
        return list;
    }

    /**
     * 获取用户上一次的推荐结果
     * @param tableName
     * @param userId
     * @return
     */
    public static String getTopKRecommendedMovies(String tableName,String condition,Integer userId) throws SQLException {
        ResultSet resultSet = JdbcUtil.queryFromMysql(tableName,condition,userId);
        String topKRecommendedMovies = null;
        while (resultSet.next()){
            topKRecommendedMovies = resultSet.getString(2);
        }
        return topKRecommendedMovies;
    }

    /**
     * 将计算后的候选电影与userId上一次的推荐结果合并，按推荐强度倒序排名，得到推荐给userId用户最新的K部电影,并更新到mysql数据库
     * @param candidateMovies 计算好了推荐强度的候选电影集
     * @param lastRecommendedResult 用户上一次的推荐结果集
     * @param specifiedUserId 指定的用户
     * @param tableName 数据库表
     *
     * 输入：计算好了推荐强度的候选电影集  用户上一次的推荐结果集
     * 两个集合合并，若有重复的，则候选集的元素取代上一次的推荐结果集元素（因为推荐强度可能有所变化），再按推荐强度（也可以认为是评分）进行降序排序
     */
    public static void updateRecommendedResult(List<String> candidateMovies,String lastRecommendedResult,Integer specifiedUserId,String tableName) throws SQLException {
        //查询条件字段
        String conditionKey = "user_id";
        //所要更新的字段
        String key = "movie_ids";
        //拼接字符串（更新的推荐电影）
        StringBuffer buffer = new StringBuffer();
        //切分上一次的推荐结果
        String[] split = lastRecommendedResult.split(",");
        //推荐电影的去重，更新，合并 （movieId:rating）
        for (int i=0;i<split.length;i++){
            for(int j=0;j<candidateMovies.size();j++){
                if (candidateMovies.get(j).substring(0,candidateMovies.get(j).indexOf(":")).equals(split[i].substring(0,split[i].indexOf(":")))){
                    split[i]=candidateMovies.get(j);
                    candidateMovies.remove(candidateMovies.get(j));
                }
            }
            candidateMovies.add(split[i]);
        }
        //按电影推荐强度降序排序
        Collections.sort(candidateMovies, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int index1 = o1.indexOf(":")+1;
                int index2 = o2.indexOf(":")+1;
                if (Double.parseDouble(o1.substring(index1,o1.length()))>Double.parseDouble(o2.substring(index2,o2.length()))) return -1;
                else if (Double.parseDouble(o1.substring(index1,o1.length()))<Double.parseDouble(o2.substring(index2,o2.length()))) return 1;
                else return 0;
            }
        });
        //取出排名前K的推荐结果
        List<String> updatedResult = candidateMovies.size()>=split.length?candidateMovies.subList(0, split.length):candidateMovies.subList(0,candidateMovies.size());
        for (int i=0;i<updatedResult.size();i++){
            if(i==updatedResult.size()-1) buffer.append(updatedResult.get(i));
            else buffer.append(updatedResult.get(i)+",");
        }
        //将用户新的推荐结果更新到数据库
        JdbcUtil.updateToMysql(tableName,specifiedUserId,buffer.toString());//之前在这个地方报错，卡了好久，一直找不到原因。这里做的事一个更新操作，mysql更新操作的sql语句是:
                                                                            //update tableName set xxx=xxx where yyy=yyy;
                                                                            //后来才发现，因为xxx字段的值是字符串类型的，它的值要放在单引号或者双引号里才行，不然就是语法错误。
                                                                            //但是在java中，字段值都是用变量表示的，所以也不能直接放在单/双引号里啊，
                                                                            //所以这里使用占位符来解决：update tableName set xxx=? where yyy=?;
                                                                            //然后再分别设置占位符的值：详情可以看下面：
                                                                            /**
                                                                             * PreparedStatement updatePreparedStatement = conn.prepareStatement("UPDATE " + tableName + " set  movie_ids = ? WHERE user_id = ?");
                                                                             * updatePreparedStatement.setString(1,value);
                                                                             * updatePreparedStatement.setInt(2,conditionValue);
                                                                             *
                                                                             * ps:这里的下标不是指该字段在数据表中字段位置的下标，而是指在这个sql语句中该字段所对应的占位符的位置，
                                                                             *    占位符在sql语句中的位置按从左往右的方向计算，起始值为1。
                                                                             */
    }
}