package com.movie.moviesite.utils;

import java.util.Random;

public class RandomUtils {

    private static final Random random = new Random();

    /**
     * 随机数工具
     * @return
     */
    public static int createRandom(){
        return random.nextInt(3);
    }

}
