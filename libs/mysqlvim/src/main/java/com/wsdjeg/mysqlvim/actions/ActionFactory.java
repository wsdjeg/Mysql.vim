package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.MVRequest;

public class ActionFactory {
    public static Action get(String action){
        switch (action) {
            case MVRequest.LOGIN:
                return new LoginAction();
        }
        return null;
    }
}
