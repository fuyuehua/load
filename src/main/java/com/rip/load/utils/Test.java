package com.rip.load.utils;

import javax.management.ObjectName;
import javax.sql.rowset.CachedRowSet;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

public class Test {

    public static double getPerMonthPrincipalInterest(double invest, double yearRate, int totalmonth) {
        double monthRate = yearRate;
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_DOWN);
        return monthIncome.doubleValue();
    }
    private static int j;
    public int get(){
        return j;
    }

    public Test(int j ){
        this.j = j;
    }

    static {
        j = 3;
    }

    public static void main(String[] args) {
        Test test1 = new Test(1);
        Test test2 = new Test(2);


        System.out.println(test1.get());
        System.out.println(test2.get());
    }

}
