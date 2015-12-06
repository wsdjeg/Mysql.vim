package com.wsdjeg.mysqlvim.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DropTableAction implements Action {
    public void run(String[] args) throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/"+args[0],args[2],args[3]);
        String sql = "DROP TABLE IF EXISTS " +args[1];
        Statement s = conn.createStatement();
        String sql2 = "select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='"+args[0]+"' and `TABLE_NAME`='"+args[1]+"'";
        ResultSet resultSet=s.executeQuery(sql2);
        if (resultSet.next()){
            s.executeUpdate(sql);
            resultSet.close();
            resultSet = s.executeQuery(sql2);
            if(!resultSet.next()){
                conn.close();
                resultSet.close();
                System.out.println(true);
            }else{
                System.out.println(false);
            }
        }else{
            conn.close();
            resultSet.close();
            System.out.println(false);
        }
    }
}
