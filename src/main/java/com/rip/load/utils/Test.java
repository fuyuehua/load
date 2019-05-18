package com.rip.load.utils;

import javax.management.ObjectName;
import javax.sql.rowset.CachedRowSet;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

public class Test {
    /*public static void main(String[] args) {
        String name;
        if(args.length > 0)
            name = args[0];
        else {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date): ");
            name = in.next();
        }

        try {
            Class cl = Class.forName(name);
            Class supercl = cl.getSuperclass();
            String modifiers = Modifier.toString(cl.getModifiers());
            if(modifiers.length() > 0 ){
                System.out.println("类的修饰符： " + modifiers + "  ");
            }
            System.out.println("类名:  " + name);
            if(supercl != null && supercl != Object.class){
                System.out.println(" 父类类名 " + supercl.getName());
            }

            System.out.println("****构造器***************************************");
            printConstructors(cl);
            System.out.println("****方法**************************************");
            printMethods(cl);
            System.out.println("****域**************************************");
            printFields(cl);
            System.out.println("********************************************");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);



    }

    public static void printConstructors(Class cl){
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor c : constructors){
            String name = c.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(name + "(");

            Class[] paramTypes = c.getParameterTypes();
            for(int j = 0; j < paramTypes.length; j++){
                if(j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    public static void printMethods(Class cl){
        Method[] methods = cl.getDeclaredMethods();
        for (Method m : methods){
            Class retType = m.getReturnType();
            String name = m.getName();

            System.out.print("  ");

            String modifiers = Modifier.toString(m.getModifiers());

            if(modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(retType.getName() + " " + name + "(");

            Class[] parameterTypes = m.getParameterTypes();
            for(int j = 0; j < parameterTypes.length; j++){
                if(j > 0 ) System.out.print(", ");
                System.out.print(parameterTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    public static void printFields(Class cl){
        Field[] fields = cl.getDeclaredFields();
        for(Field f : fields){
            Class type = f.getType();
            String name = f.getName();
            System.out.print("  ");
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " " );
            System.out.println(type.getName() + " " + name + ";");
        }

    }*/

    public static void main(String[] args) {
        String str1 = "2019-5-15最新测试4task20190515174314720";

//        str1 = MD5Util.md5BySingleParamda(str1);

        try {

            // 编码
            String asB64 = Base64.getEncoder().encodeToString(str1.getBytes("utf-8"));
            System.out.println(asB64); // 输出为: c29tZSBzdHJpbmc=

            // 解码
            byte[] asBytes = Base64.getDecoder().decode(asB64);
            String str2= new String(asBytes, "utf-8");
            System.out.println(new String(asBytes, "utf-8")); // 输出为: some string



            System.out.println("utf-8编码下"+str2+"所占的字节数:" + str2.getBytes("utf-8").length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

}
