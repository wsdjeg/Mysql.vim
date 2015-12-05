package com.wsdjeg.mysqlvim.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UseDatabaseAction implements Action {
    public void run(String[] args)
    throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/information_schema",args[1],args[2]);
        String sql = "SELECT SCHEMA_NAME from SCHEMATA where schema_name = '" + args[0] + "';";
        Statement s=conn.createStatement();
        ResultSet resultSet=s.executeQuery(sql);
        if (resultSet.next()){
            conn.close();
            resultSet.close();
            System.out.println(true);
        }
        conn.close();
        resultSet.close();
        System.out.println(false);

    }
}
