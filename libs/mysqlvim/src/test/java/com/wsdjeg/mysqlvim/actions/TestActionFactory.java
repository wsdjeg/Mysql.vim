package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.MVRequest;
import java.lang.reflect.Method;

public class TestActionFactory {
    public void testRun() {
        String str = "com.wsdjeg.mysqlvim.action."+MVRequest.INIT.substring(2,MVRequest.INIT.length())+"Action";
        try {
            Class<?> clazz = Class.forName(str);
            Method[] md = clazz.getDeclaredMethods();
            System.out.println(md[0].getName());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
