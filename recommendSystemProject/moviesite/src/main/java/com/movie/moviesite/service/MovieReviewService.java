package com.movie.moviesite.service;

import com.movie.moviesite.entity.CommonCondition;
import com.movie.moviesite.entity.MovieReview;
import com.movie.moviesite.entity.SelectCondition;

import java.util.List;

public interface MovieReviewService {

    /**
     * 添加影评
     * @param movieReview
     * @return 插入的记录数目，大于0，成功，否则，失败
     */
    boolean insertMovieReview(MovieReview movieReview);

    /**
     * 根据电影Id选出该电影的所有影评
     * @param condition
     * @return
     */
    List<MovieReview> selectMovieReviewByMovieId(CommonCondition condition);

    /**
     * 根据用户Id和电影Id查询唯一影评
     * @param userId
     * @param movieId
     * @return
     */
    MovieReview selectMovieReviewByMovieIdAndUserId(Integer userId, Integer movieId);

    /**
     * 删除影评
     * @param movieReview
     * @return 删除的记录数目，大于0，成功，否则，失败
     */
    boolean deleteMovieReviewByMovieIdAndUserId(MovieReview movieReview);

    /**
     * 更新影评的赞同数
     * @param movieReview
     * @return 更新的记录数目，大于0，成功，否则，失败
     */
    boolean updateUsefulCount(MovieReview movieReview);

    /**
     * 修改影评内容
     * @param movieReview
     * @return 更新的记录数目，大于0，成功，否则，失败
     */
    boolean updateReviewContent(MovieReview movieReview);

}
