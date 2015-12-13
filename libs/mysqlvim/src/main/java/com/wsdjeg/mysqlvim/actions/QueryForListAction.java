package com.wsdjeg.mysqlvim.action;
public class QueryForListAction  implements Action {
    public void run(String[] args) {
        if (args.length != 0) {
            String sql = "";
            for (String  a: args) {
                sql += a;
            }
        }
    }
}
