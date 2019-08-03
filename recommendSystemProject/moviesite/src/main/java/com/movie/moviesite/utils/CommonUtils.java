package com.movie.moviesite.utils;

import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    public static Map<Integer,String> CategoryDictionary(){
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"Action");
        map.put(2,"Adventure");
        map.put(3,"Animation");
        map.put(4,"Children's");
        map.put(5,"Comedy");
        map.put(6,"Crime");
        map.put(7,"Documentary");
        map.put(8,"Drama");
        map.put(9,"Fantasy");
        map.put(10,"Film-Noir");
        map.put(11,"Horror");
        map.put(12,"Musical");
        map.put(13,"Mystery");
        map.put(14,"Romance");
        map.put(15,"Sci-Fi");
        map.put(16,"Thriller");
        map.put(17,"War");
        map.put(18,"Western");
        return map;
    }

}
