package com.rip.load.pojo.nativePojo;

import com.rip.load.pojo.User;

import java.io.Serializable;

public class UserThreadLocal implements Serializable {


    private static final long serialVersionUID = 3021391470016097362L;

    private UserThreadLocal(){

    }

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<User>();

    public static void set(User user){
        LOCAL.set(user);
    }

    public static User get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
