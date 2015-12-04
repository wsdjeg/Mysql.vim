package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.util.JDBCTools;
import java.sql.SQLException;

public class LoginAction implements Action {
    public void run(String[] agrs)throws SQLException,ClassNotFoundException{
        if (agrs.length == 2) {
            if (JDBCTools.getConnection(agrs[0],agrs[1])!=null){
                System.out.println(true);
            }
        }else if(agrs.length == 3){
            if (JDBCTools.getConnection(agrs[0],agrs[1],agrs[2])!=null){
                System.out.println(true);
            }
        }
        System.out.println(false);
    }
}
