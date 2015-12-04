package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.MVRequest;

public class InitAction implements Action {
    public void run(String[] args){
        MVRequest.init();
    }
}
