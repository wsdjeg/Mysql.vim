package com.wsdjeg.mysqlvim.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropDatabaseAction implements Action {
    public void run(String[] args) throws SQLException,ClassNotFoundException{
        String sql = "drop database `" + args[0] + "`";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?user="+ args[1] +"&password=" + args[2]);
        Statement s=conn.createStatement();
        s.executeUpdate(sql);
        conn.close();
        System.out.println(true);
    }
}
