package com.movie.moviesite.mapper;

import com.movie.moviesite.entity.CommonCondition;
import com.movie.moviesite.entity.MovieReview;
import com.movie.moviesite.entity.SelectCondition;

import java.util.List;

/**
 * 电影评论
 */
public interface MovieReviewMapper {

    /**
     * 添加影评
     * @param movieReview
     * @return 插入的记录数目，大于0，成功，否则，失败
     */
    int insertMovieReview(MovieReview movieReview);

    /**
     * 添加影评
     * @param movieReview
     * @return 插入的记录数目，大于0，成功，否则，失败
     */
    int insertMovieReviewSelective(MovieReview movieReview);

    /**
     * 根据电影Id选出该电影的所有影评
     * @param condition
     * @return
     */
    List<MovieReview> selectMovieReviewByMovieId(CommonCondition condition);

    /**
     * 根据用户Id和电影Id查询唯一影评
     * @param movieReview
     * @return
     */
    MovieReview selectMovieReviewByMovieIdAndUserId(MovieReview movieReview);

    /**
     * 删除影评
     * @param movieReview
     * @return 删除的记录数目，大于0，成功，否则，失败
     */
    int deleteMovieReviewByMovieIdAndUserId(MovieReview movieReview);

    /**
     * 更新影评的赞同数
     * @param movieReview
     * @return 更新的记录数目，大于0，成功，否则，失败
     */
    int updateUsefulCount(MovieReview movieReview);

    /**
     * 修改影评内容
     * @param movieReview
     * @return 更新的记录数目，大于0，成功，否则，失败
     */
    int updateReviewContent(MovieReview movieReview);


    /**
     * 对于插入、删除、修改操作而言，返回值为int类型表示这些操作结果影响的行数，
     * 如果大于0，说明操作成功，否则失败。
     */

}
