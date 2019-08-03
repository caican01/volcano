package com.movie.moviesite.controller;

import com.movie.moviesite.entity.StuGrade;
import com.movie.moviesite.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    private GradeService service;

    @ResponseBody
    @RequestMapping(value = "searchGrade")
    public String getGrades(Integer k){
        List<StuGrade> stuGrades = service.getStuGrades(k);
        if (stuGrades!=null) {
            System.out.println(stuGrades.size());
            for (StuGrade g : stuGrades) {
                System.out.println(g.getStu().getStuName()+":"+g.getGrade());
            }
            return "success";
        }else
            return "fail";
    }

    @ResponseBody
    @RequestMapping(value = "insertgrade",method = RequestMethod.POST)
    public String insertGrade(int stuid,long grade){

        StuGrade stuGrade = new StuGrade();
        stuGrade.setStuid(3);
        stuGrade.setGrade(101);
        service.insertGrade(stuGrade);
        return "success";
    }

}
