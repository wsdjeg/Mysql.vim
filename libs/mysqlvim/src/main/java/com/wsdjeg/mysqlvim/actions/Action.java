package com.wsdjeg.mysqlvim.action;
public interface Action {
    public static final int GETCONNECTION = 1;
    public void run(String[] agrs)throws Exception;

}
