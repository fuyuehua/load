package com.rip.load.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Play {

    public static String play1(){
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        list.add("333");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        return null;
    }

    public static void main(String[] args) {
        String kk = play1();
    }
}
