package bean;

import java.io.Serializable;

/**
 * 近期电影热度表(电影的ID，近期热度)
 */
public class RecentMovieHeatRankedList implements Serializable {

    //电影ID
    private int movieId;
    //近期movieId被评分的次数，以周为统计周期
    private int recentMovieHeat;

    public RecentMovieHeatRankedList(){}

    public int getMovieId() {
        return movieId;
    }

    public int getRecentMovieHeat() {
        return recentMovieHeat;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setRecentMovieHeat(int recentMovieHeat) {
        this.recentMovieHeat = recentMovieHeat;
    }
}
