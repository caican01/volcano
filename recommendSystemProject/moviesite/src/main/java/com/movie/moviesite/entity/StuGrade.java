package com.movie.moviesite.entity;

public class StuGrade {

    /**
     * 1、可以另设主键
     * 2、可以放外键
     * 3、也可以删除外键，都行的
     */
    private int stuid;
    private long grade;
    private Stu stu;
    private User user;

    public StuGrade(){}

    public int getStuid() {
        return stuid;
    }

    public void setStuid(int stuid) {
        this.stuid = stuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGrade() {
        return grade;
    }

    public void setGrade(long grade) {
        this.grade = grade;
    }

    public Stu getStu() {
        return stu;
    }

    public void setStu(Stu stu) {
        this.stu = stu;
    }
}
