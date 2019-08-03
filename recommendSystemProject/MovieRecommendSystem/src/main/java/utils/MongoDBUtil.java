package utils;

import com.mongodb.*;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import conf.MongoConfig;
import com.mongodb.spark.MongoSpark;
import context.mrsSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.bson.Document;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static conf.MongoConfig.collection;
import static conf.MongoConfig.host;
import static conf.MongoConfig.port;

/**
 * mongodb操作工具
 */
public class MongoDBUtil {

    /**
     * 获取JavaSparkContext实例
     * @return
     */
//    private static JavaSparkContext jsc = new JavaSparkContext(
//        new SparkConf().
//        setAppName("MongoDB").
//        setMaster("local[*]").
//        //指定mongodb的输入路径（读取数据的位置）
//        set("spark.mongodb.input.uri", "mongodb://volcano02.cc.com/RecommendSystem.RatingsCollection").
//        //指定mongodb的输出路径（写入数据的路径）
//        set("spark.mongodb.output.uri", "mongodb://volcano02.cc.com/RecommendSystem.RatingsCollection")
//    );


    /**
     * 从hdfs读取原始评分文件，并转换成Document文档类型的JavaRDD,
     * 调用MongoSpark.save(JavaRDD)方法存储到mongodb
     * @param filePath
     *
     * 这个方法要最先开始执行，这样mongodb中才有数据，其他需要用到mongodb中的数据的方法才能正确执行
     *
     */
    public static void saveToMongoDB(JavaSparkContext jsc,String filePath){
        JavaRDD<String> ratingsRDD = jsc.textFile(filePath);
        JavaRDD<Document> documentJavaRDD = ratingsRDD.mapPartitions(new FlatMapFunction<Iterator<String>, Document>() {
            @Override
            public Iterator<Document> call(Iterator<String> stringIterator) throws Exception {
                List<Document> list = new LinkedList<>();
                while (stringIterator.hasNext()) {
                    String[] split = stringIterator.next().split("\t");
                    if (split.length == 4) {
                        Document document = new Document();
                        document.append("userId", split[0]);
                        document.append("movieId", split[1]);
                        document.append("rating", split[2]);
                        document.append("timestamp", split[3]);
                        list.add(document);
                    }
                }
                return list.iterator();
            }
        });
        MongoSpark.save(documentJavaRDD);
    }

    /**
     * 获取mongodb中的数据
     * @return
     */
    public static JavaRDD<Document> getFromMongoDB(JavaSparkContext jsc){
        return MongoSpark.load(jsc);
    }

    public static void main(String[] args){

//        String applicationName = "SaveToMongoDB";
//        String master = "local[*]";
//        //训练数据存储路径，待导入mongodb数据
//        String trainFilePath = "";
//
//        //获取JavaSparkContext对象
//        JavaSparkContext jsc = mrsSparkContext.getMongoJavaSparkContext(applicationName, master, MongoConfig.ratingsMongoInputKey,MongoConfig.ratingsMongoInputValue,MongoConfig.ratingsMongoOutputKey,MongoConfig.ratingsMongoOutputValue);
//
//        saveToMongoDB(jsc,trainFilePath);

//        MongoClient client = new MongoClient(host,port);
//        DB db = client.getDB(MongoConfig.db);
//        DBCollection dbCollection = db.getCollection(collection);
//        DBObject object = new BasicDBObject();
//        object.put("userId",6);
//        object.put("movieId",555);
//        object.put("rating",5);
//        object.put("timestamp",Long.parseLong("1552485108"));
//        dbCollection.save(object);
//        DBObject object1 = new BasicDBObject();
//        object1.put("userId",6);
//        object1.put("movieId",555);
//        DBCursor cursor = dbCollection.find(object1);
//        if (cursor.hasNext()) {
//            DBObject next = cursor.next();
//            System.out.println(next.get("timestamp"));
//        }
//        int size = cursor.size();
//        System.out.println("记录数："+size);


    }

}