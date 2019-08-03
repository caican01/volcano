package context;

import conf.SparkConfig;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;

/**
 * 获取SparkContext实例
 */
public class mrsSparkContext extends SparkConfig implements Serializable{

    public static JavaSparkContext getCommJavaSparkContext(String ApplicationName, String master){
        JavaSparkContext sparkContext = new JavaSparkContext(SparkConfig.getCommSparkConf(ApplicationName, master));
        return sparkContext;
    }

    public static JavaSparkContext getMongoJavaSparkContext(String ApplicationName, String master,String mongoInputKey,String mongoInputValue,
                                                            String mongoOutputKey,String mongoOutputValue){
        JavaSparkContext sparkContext = new JavaSparkContext(SparkConfig.getMongoSparkConf(ApplicationName, master,
                mongoInputKey,mongoInputValue,mongoOutputKey,mongoOutputValue));
        return sparkContext;
    }

}
