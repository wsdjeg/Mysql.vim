package com.wsdjeg.mysqlvim;
import java.lang.reflect.Field;
public class MVRequest {
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
    public final static String INIT = "--Init";
    public static void init(){
        try {
            Class<?> clazz = Class.forName(getClassNameForStatic());
            Field[] fs = clazz.getFields();
            for(Field f:fs){
                System.out.println(f.getName()+"=="+f.get(clazz.newInstance()));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private static final String getClassNameForStatic() {
        return new Object() {
            public String getClassName() {
                String className = this.getClass().getName();
                return className.substring(0, className.lastIndexOf('$'));
            }
        }.getClassName();
    }
    private static final Class<?> getClassForStatic() {
        return new Object() {
            public Class<?> getClassForStatic() {
                return this.getClass();
            }
        }.getClassForStatic();
    }
}
