package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.MVRequest;

public class ActionFactory {
    public final static String LOGIN = "--login";
    public final static String LOGOUT = "--logout";
    public final static String USE = "--use";
    public final static String CREATEDATABASE = "--createdatabase";
    public final static String CREATETABLE = "--createtable";
    public final static String QUERY = "--query";
    public final static String INSERT = "--insert";
    public final static String BASEURL = "jdbc:mysql://localhost/";
    public final static String DROPDATABASE = "--dropdatabase";
    public final static String DROPTABLE = "--droptable";
    public static Action getAction(String action){
        switch (action) {
            case LOGIN:
                return new LoginAction();
            case MVRequest.INIT:
                return new InitAction();
            case USE:
                return new UseDatabaseAction();
            case CREATEDATABASE:
                return new CreateDatabaseAction();
            case DROPDATABASE:
                return new DropDatabaseAction();
            case CREATETABLE:
                return new CreateTableAction();
            case DROPTABLE:
                return new DropTableAction();
            case INSERT:
                return new InsertAction();
            case QUERY:
                return new QueryForListAction();
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
