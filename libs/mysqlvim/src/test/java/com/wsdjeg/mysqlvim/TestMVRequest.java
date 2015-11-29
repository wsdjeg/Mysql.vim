package com.wsdjeg.mysqlvim;

import java.lang.reflect.Field;

public class TestMVRequest {
    public void testString(){
        try {
            Class<?> clazz = Class.forName("com.wsdjeg.mysqlvim.MVRequest");
            Field f[] = clazz.getFields();
            for(Field ff :f){
                System.out.println(ff.get(clazz.newInstance()));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
