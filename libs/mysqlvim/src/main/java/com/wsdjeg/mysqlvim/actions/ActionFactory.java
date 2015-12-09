package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.MVRequest;

public class ActionFactory {
    public static Action getAction(String action){
        switch (action) {
            case MVRequest.LOGIN:
                return new LoginAction();
            case MVRequest.INIT:
                return new InitAction();
            case MVRequest.USE:
                return new UseDatabaseAction();
            case MVRequest.CREATEDATABASE:
                return new CreateDatabaseAction();
            case MVRequest.DROPDATABASE:
                return new DropDatabaseAction();
            case MVRequest.CREATETABLE:
                return new CreateTableAction();
            case MVRequest.DROPTABLE:
                return new DropTableAction();
            case MVRequest.INSERT:
                return new InsertAction();
        }
        return null;
    }
    public static String[] getArgs(String[] args){
        if (args.length>=2) {
            String[] a = new String[args.length-1];
            for (int i = 1; i < args.length; i++) {
                a[i-1]=args[i];
            }
            return a;
        }
        return null;
    }
}
