package com.wsdjeg.mysqlvim;

import com.wsdjeg.mysqlvim.SQLUtils;

public class MysqlVi {
    public static String databaseName;
    public static void main(String[] args) {
        switch (args[0]) {
            case MVRequest.LOGIN:
                if (args.length==3) {
                    try {
                        System.out.println(SQLUtils.getConnection(args[1],args[2]));
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    //TODO
                }
                break;
            case MVRequest.LOGOUT:
                break;
            case MVRequest.USE:
                try {
                    System.out.println(SQLUtils.useDatabase(args[1],args[2],args[3]));
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case MVRequest.DROPDATABASE:
                try {
                    System.out.println(SQLUtils.dropDatabase(args[1],args[2],args[3]));
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case MVRequest.DROPTABLE:
                try {
                    System.out.println(SQLUtils.droptable(args[1],args[2],args[3],args[4]));
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case MVRequest.CREATEDATABASE:
                try {
                    System.out.println(SQLUtils.createDatabase(args[1],args[2],args[3]));
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case MVRequest.INSERT:
                try {
                    String argsm[] = {args[5],args[6],args[7],args[8]};
                    System.out.println(SQLUtils.insert(args[1],args[2],args[3],args[4],argsm));
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case MVRequest.INIT:
                MVRequest.init();
                break;
        }
    }
}
