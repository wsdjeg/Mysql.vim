package com.wsdjeg.mysqlvim.util;
import com.wsdjeg.mysqlvim.MVRequest;
import com.wsdjeg.mysqlvim.util.ReflectionUtils;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
public class JDBCTools{
	public static void commit(Connection connection){
		if(connection != null){
			try{
				connection.commit();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void rollback(Connection connection){
		if(connection != null){
			try{
				connection.rollback();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void beginTx(Connection connection){
		if(connection != null){
			try{
				connection.setAutoCommit(false);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static <T> T getEntity(Class<T> clazz,String sql,Object ... args){
		T entity = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0; i < args.length ; i++){
				preparedStatement.setObject(i + 1,args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			Map<String,Object> values = new HashMap<String,Object>();
			if(resultSet.next()){
				for(int i = 0; i < rsmd.getColumnCount(); i++){
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(columnLabel);
					values.put(columnLabel,columnValue);
				}
			}
			if(values.size() > 0){
				entity = clazz.newInstance();
				for(Map.Entry<String,Object> entry : values.entrySet()){
					String fieldName = entry.getKey();
					Object fieldValue = entry.getValue();
					ReflectionUtils.setFieldValue(entity,fieldName,fieldValue);
				}
			}
			return entity;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCTools.release(resultSet,preparedStatement,connection);
		}
		return entity;
	}
	public static void update(String sql,Object ... args){
		Connection connection= null;
		PreparedStatement preparedStatement = null;
		try{
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0 ; i < args.length ; i++){
				preparedStatement.setObject(i + 1, args[i]);
			}
			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			release(null,preparedStatement,connection);
		}
	}
	public static void release(ResultSet rs,Statement statement,Connection connection){
		if(rs != null){try{rs.close();}catch(Exception e){e.printStackTrace();}}
		if(statement != null){try{statement.close();}catch(Exception e){e.printStackTrace();}}
		if(connection != null){try{connection.close();}catch(Exception e){e.printStackTrace();}}

	}
	public static Connection getConnection() throws Exception{
        return null;
	}

    public static Connection getConnection(String username,String password)
        throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(MVRequest.BASEURL+"information_schema",username,password);
        return conn;
    }
    public static Connection getConnection(String databaseName,String username,String password)
        throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(MVRequest.BASEURL+databaseName,username,password);
        return conn;
    }
}
