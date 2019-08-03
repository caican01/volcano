package conf;

import java.io.Serializable;

public class MongoConfig implements Serializable{

    public static String ratingsMongoInputKey = "spark.mongodb.input.uri";
//    public static String ratingsMongoInputValue = "mongodb://volcano01.cc.com:27017,volcano02.cc.com:27018,volcano03.cc.com:27019/RecommendSystem.RatingsCollection";
    public static String ratingsMongoInputValue = "mongodb://volcano01.cc.com:27017/RecommendSystem.RatingsCollection";
    public static String ratingsMongoOutputKey = "spark.mongodb.output.uri";
//    public static String ratingsMongoOutputValue = "mongodb://volcano01.cc.com:27017,volcano02.cc.com:27018,volcano03.cc.com:27019/RecommendSystem.RatingsCollection";
    public static String ratingsMongoOutputValue = "mongodb://volcano01.cc.com:27017/RecommendSystem.RatingsCollection";
    public static String host = "volcano01.cc.com";
    public static int port = 27017;
    public static String db = "RecommendSystem";
    public static String collection = "RatingsCollection";
}
