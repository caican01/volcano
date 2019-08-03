package bean;

import java.io.Serializable;

/**
 * 每部电影的平均评分表(每部电影的ID，该电影的平均评分)
 */
public class EachMovieAverageRating implements Serializable {

    //电影ID
    private int movieId;
    //movieId的平均评分=所有参与对该电影评分的用户的评分之和/参与该电影评分的用户数
    private double movieAverageRating;

    public EachMovieAverageRating(){}

    public int getMovieId() {
        return movieId;
    }

    public double getMovieAverageRating() {
        return movieAverageRating;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieAverageRating(double movieAverageRating) {
        this.movieAverageRating = movieAverageRating;
    }
}
