package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.StuGrade;
import com.movie.moviesite.mapper.GradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService implements com.movie.moviesite.service.GradeService{

    @Autowired
    private GradeMapper mapper;

    @Override
    public List<StuGrade> getStuGrades(Integer k) {
        return mapper.getStuGrade(k);
    }

    @Override
    public void insertGrade(StuGrade grade) {
        mapper.insertGrade(grade);
    }
}
