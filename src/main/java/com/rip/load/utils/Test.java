package com.rip.load.utils;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

public class Test {

    public static void main(String[] args) {
        int i = 0 ;
        System.out.println(i++);
        //result:0

        System.out.println(i);
        //result:1

        int j = 0;
        j++;
        System.out.println(j);
        //result:1
    }
}
