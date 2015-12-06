package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.MVRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTableAction implements Action{
    public void run(String[] args) {
        if (args.length%2==0&&args.length >= 4) {
            String sql = "create table "
                + args[3]
                + "(";
            for (int i = 4; i < args.length-1;i++ ) {
                if (i%2==0) {
                    sql += args[i]+" ";
                }else{
                    sql += args[i]+",";
                }
            }
            sql += args[args.length-1]+")";
            try {
                Connection conn =DriverManager.getConnection(MVRequest.BASEURL+args[0],args[1],args[2]);
                Statement s=conn.createStatement();
                s.executeUpdate(sql);
                conn.close();
                System.out.println(true);
            } catch(Exception e){
                System.out.println(false);
                e.printStackTrace();
            }

        }else{
            System.out.println(false);
        }
    }
}
