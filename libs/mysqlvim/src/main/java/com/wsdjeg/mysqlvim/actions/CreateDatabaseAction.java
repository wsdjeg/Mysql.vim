package com.wsdjeg.mysqlvim.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabaseAction implements Action {
    public void run(String[] args) throws SQLException,ClassNotFoundException{
        String sql = "CREATE DATABASE IF NOT EXISTS `"
            + args[0]
            + "` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?user="
                + args[1]
                + "&password="
                + args[2]);
        Statement s=conn.createStatement();
        s.executeUpdate(sql);
        conn.close();
        System.out.println(true);
    }
}
