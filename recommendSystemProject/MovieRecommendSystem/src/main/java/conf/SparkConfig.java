package conf;

import org.apache.spark.SparkConf;

import java.io.Serializable;

/**
 * 获取SparkConf实例
 */
public class SparkConfig implements Serializable{

    public static SparkConf getCommSparkConf(String ApplicationName,String master){
        SparkConf sparkConf = new SparkConf().setAppName(ApplicationName).setMaster(master);
        return sparkConf;
    }

    public static SparkConf getMongoSparkConf(String ApplicationName,String master,String mongoInputKey,String mongoInputValue,
                                              String mongoOutputKey,String mongoOutputValue){
        SparkConf sparkConf = new SparkConf().setAppName(ApplicationName).setMaster(master).
                set(mongoInputKey,mongoInputValue).set(mongoOutputKey,mongoOutputValue).
                set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        return sparkConf;
    }

}
