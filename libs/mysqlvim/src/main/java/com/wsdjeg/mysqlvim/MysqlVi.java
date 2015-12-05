package com.wsdjeg.mysqlvim;

import com.wsdjeg.mysqlvim.SQLUtils;
import com.wsdjeg.mysqlvim.action.ActionFactory;
import java.util.ArrayList;
import java.util.List;

public class MysqlVi {
    public static String databaseName;
    public static void main(String[] args) {
        try {
            ActionFactory.getAction(args[0]).run(ActionFactory.getArgs(args));
        } catch(Exception e){
            //System.out.println(false);
            e.printStackTrace();
        }
    }
}
