package com.movie.moviesite.service.Impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.movie.moviesite.common.MongoConfig;
import com.movie.moviesite.entity.MovieRating;
import com.movie.moviesite.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class MovieRatingService implements com.movie.moviesite.service.MovieRatingService{

    @Override
    public MovieRating selectByUserIdAndMovieId(Integer userId, Integer movieId) {
        DBObject query = new BasicDBObject();
        query.put("userId",userId);
        query.put("movieId",movieId);
        DBCursor dbCursor = MongoDBUtils.readFromMongodb(MongoConfig.host, MongoConfig.port, MongoConfig.dbName, MongoConfig.collectionName, query);
        List<DBObject> dbObjects = dbCursor.toArray();
        MovieRating rating = null;
        if (dbObjects!=null&&dbObjects.size()>0){
            DBObject object = dbObjects.get(0);
            rating = new MovieRating();
            rating.setUserId((Integer)object.get("userId"));
            rating.setMovieId((Integer)object.get("movieId"));
            rating.setRating((Double)object.get("rating"));
            rating.setTimestamp(((Long)object.get("timestamp"))*1000);
        }
        return rating;
    }

    @Override
    public List<MovieRating> selectByUserId(Integer userId) {
        DBObject query = new BasicDBObject();
        query.put("userId",userId);
        DBCursor dbCursor = MongoDBUtils.readFromMongodb(MongoConfig.host, MongoConfig.port, MongoConfig.dbName, MongoConfig.collectionName, query);
        List<MovieRating> ratingList = new LinkedList<>();
        while (dbCursor.hasNext()) {
            DBObject object = dbCursor.next();
            MovieRating movieRating = new MovieRating();
            movieRating.setUserId((Integer)object.get("userId"));
            movieRating.setMovieId((Integer)object.get("movieId"));
            movieRating.setRating((Double)object.get("rating"));
            movieRating.setTimestamp(((Long)object.get("timestamp"))*1000);
            ratingList.add(movieRating);
        }
        return ratingList;
    }
}
