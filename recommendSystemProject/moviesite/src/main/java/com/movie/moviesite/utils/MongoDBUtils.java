package com.movie.moviesite.utils;

import com.mongodb.*;

/**
 * 连接mongodb,指定数据库及数据集合
 */
public class MongoDBUtils {

    /**
     * 读取mongodb数据
     * @param host mongodb所在主机ip
     * @param port mongod服务所占用端口
     * @param object 查询条件
     * @param dbName mongodb数据库名称       
     * @param collectionName mongo数据集合名称
     * @return
     */
    public static DBCursor readFromMongodb(String host, int port,String dbName,String collectionName, DBObject object){
        MongoClient client = new MongoClient(host,port);
        DB db = client.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);
        return collection.find(object);
    }
    
}
