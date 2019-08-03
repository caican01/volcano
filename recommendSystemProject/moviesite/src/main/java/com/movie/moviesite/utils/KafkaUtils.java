package com.movie.moviesite.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * kafka工具类主要是实现生产者，
 * 向kafka topic发送message,
 * 推荐模块实时系统再消费topic中的message
 */
public class KafkaUtils {

    private static final String topicName = "userRating";
    private static final String subKey = "userRatingData";
    private static Producer<String,String> producer;

    static {
        Properties props = new Properties();
        props.put("bootstrap.servers", "volcano01.cc.com:9092,volcano02.cc.com:9092,volcano03.cc.com:9092");//服务器ip:端口号，集群用逗号分隔
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    /**
     * 发送message
     **/
    public static void sendMessage(String message) throws ExecutionException, InterruptedException {
        ProducerRecord<String,String> record = new ProducerRecord<>(topicName,subKey + RandomUtils.createRandom(),message);
        producer.send(record);
    }

}
