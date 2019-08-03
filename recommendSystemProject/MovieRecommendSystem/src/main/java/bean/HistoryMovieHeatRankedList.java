package bean;

import java.io.Serializable;

/**
 * 历史电影热度表(每部电影ID，热度（即被评分次数）)
 */
public class HistoryMovieHeatRankedList implements Serializable{

    //电影ID,每部电影的ID本就是独一无二的，所以这里就以movieId为主键即可
    private int movieId;
    //movieId对应的评分次数，即热度
    private int historyMovieHeat;

    public HistoryMovieHeatRankedList(){}

    public int getMovieId() {
        return movieId;
    }

    public int gethistoryMovieHeat() {
        return historyMovieHeat;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void sethistoryMovieHeat(int historyMovieHeat) {
        this.historyMovieHeat = historyMovieHeat;
    }
}
