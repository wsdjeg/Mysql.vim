package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.MVRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertAction implements Action {
    public void run(String[] args){
        if (args.length%2==0 && args.length >= 4) {
            String  sql = "insert into " + args[3]+ "(";
            for (int i = 4; i < 4 + (args.length-4)/2; i++) {
                if (i == (args.length-4)/2 + 3) {
                    sql += args[i];
                }else{
                    sql += args[i]+",";
                }
            }
            sql += ") values(";
            for (int i = (args.length-4)/2 + 4; i<args.length; i++) {
                if (i == args.length - 1) {
                    sql += "'"+args[i]+"'";
                }else{
                    sql += "'"+args[i]+"',";
                }
            }
            sql += ")";
            System.out.println(sql);
            try {
                Connection conn =DriverManager.getConnection(MVRequest.BASEURL+args[0],args[1],args[2]);
                Statement s=conn.createStatement();
                s.executeUpdate(sql);
                conn.close();
                System.out.println(true);
            } catch(Exception e){
                e.printStackTrace();
                System.out.println(false);
            }
        }else{
            System.out.println(false);
        }

    }
}
