package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.util.JDBCTools;

public class LoginAction implements Action {
    public String run(String[] agrs){
        String result = "";
        if (agrs.length == 2) {
            if (JDBCTools.getConnection(agrs[0],agrs[1])!=null){
                return "success";
            }
        }else if(agrs.length == 3){
            if (JDBCTools.getConnection(agrs[0],agrs[1],agrs[2])!=null){
                return "success";
            }
        }
        return result;
    }
}
