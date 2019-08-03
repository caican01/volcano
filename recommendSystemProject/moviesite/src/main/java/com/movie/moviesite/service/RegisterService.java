package com.movie.moviesite.service;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.EachUserLikedMovie;
import com.movie.moviesite.entity.User;

public interface RegisterService {

    /**
     * 注册用户
     * @param user
     * @return 返回该记录的主键ID
     */
    int insertUserMessage(User user);

    /**
     * 注册信息的格式检查应该在前端完成而不应提交到后台完成，因为这样将会引起网络传输，产生一定的延时，对性能有所损耗。
     * 所以注册信息在前端格式检验通过了，再提交到后台，后台检验该注册信息是否已经注册过了，如果
     */

    /**
     * 检测注册信息是否已经存在（邮箱和用户名同时检测）
     * @param email
     * @param userName
     * @return
     */
    E3Result checkEmailAndUserName(String email, String userName);

    /**
     * 检测邮箱或用户名是否存在
     * @param param email 或 userName
     * @param type 1->email  2->userName
     * @return
     */
    E3Result cheEmailOrUserName(String param, int type);

    /**
     * 注册时选择喜欢的电影并保存到数据库
     * @param eachUserLikedMovie
     * @return
     */
    boolean selectUserLikedMovies(EachUserLikedMovie eachUserLikedMovie);

}
