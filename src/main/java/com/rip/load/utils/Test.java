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

    public static void main(String[] args) {

        double perMonthPrincipalInterest = Test.getPerMonthPrincipalInterest(100, 0.01, 6);
        System.out.println("1:   " + perMonthPrincipalInterest);


        double A = 100;
        double p = 0.01;
        double k = 6.00;

            System.out.println(
                    (A*p*Math.pow((1+p), k))/
                            (Math.pow((1+p), k) - 1)
            );

        System.out.println(new BigDecimal(106).divide(new BigDecimal(6), 2, BigDecimal.ROUND_DOWN));
    }

}
