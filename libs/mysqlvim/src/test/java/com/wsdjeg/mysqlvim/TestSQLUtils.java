package com.wsdjeg.mysqlvim;

import com.wsdjeg.mysqlvim.SQLUtils;

public class TestSQLUtils {
    public void testGetConnection(){
        try {
            SQLUtils.getConnection("root","1234");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void testCreateTable(){
        String[] a = {"price","int"};
        SQLUtils.createTable("test","root","1234","product",a);
    }
}
