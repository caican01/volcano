package com.movie.moviesite.mapper;

import com.movie.moviesite.entity.StuGrade;
import java.util.List;

public interface GradeMapper {

    List<StuGrade> getStuGrade(Integer k);

    void insertGrade(StuGrade grade);

}
