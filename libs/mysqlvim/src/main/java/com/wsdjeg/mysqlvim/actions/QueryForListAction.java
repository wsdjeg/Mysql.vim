package com.wsdjeg.mysqlvim.action;

import com.wsdjeg.mysqlvim.dao.BaseDAO;
import java.util.List;

public class QueryForListAction  implements Action {
    BaseDAO bd = new BaseDAO();
    public void run(String[] args) {
        String sql = "";
        if (args.length != 0) {
            for (String  a: args) {
                sql += a;
            }
        }
        List result = bd.queryForList(sql, args);
        System.out.println("item_names :" + args);
    }
}
