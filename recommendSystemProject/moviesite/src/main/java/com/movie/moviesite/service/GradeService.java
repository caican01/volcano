package com.movie.moviesite.service;

import com.movie.moviesite.entity.StuGrade;

import java.util.List;

public interface GradeService {

    List<StuGrade> getStuGrades(Integer k);

    void insertGrade(StuGrade grade);

}
