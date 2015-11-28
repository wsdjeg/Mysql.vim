package com.wsdjeg.mysqlvim;
public class UserInfo {
    private static UserInfo user;
    private static String username;
    private static String password;
    public static void init(String username,String password){
        UserInfo.username = username;
        UserInfo.password = password;
    }
    private UserInfo(){
    }
}
