package com.rip.load.utils;

import javax.management.ObjectName;
import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

public class Test {

    public static void main(String[] args) {
        int i = 0 ;
        int k[] = new int[]{1,2,3};

        int length = k.length;
        Object o = new Object();
        o.notify();
        System.out.println(i++);
        //result:0

        System.gc();

        System.out.println(i);
        //result:1

        int j = 0;
        j++;
        System.out.println(j);

        List<String> list = new ArrayList<>();
        list.add("1");

        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);


        //result:1
    }
}
