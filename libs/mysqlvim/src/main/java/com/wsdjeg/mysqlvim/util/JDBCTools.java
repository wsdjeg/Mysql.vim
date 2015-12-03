package com.wsdjeg.mysqlvim.util;
import com.wsdjeg.mysqlvim.util.ReflectionUtils;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.Map;
import java.util.HashMap;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.sql.DataSource;
import java.util.Properties;
public class JDBCTools{
	//处理事务相关方法
	//提交事务
	public static void commit(Connection connection){
		if(connection != null){
			try{
				connection.commit();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//回滚事务
	public static void rollback(Connection connection){
		if(connection != null){
			try{
				connection.rollback();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//开始事务
	public static void beginTx(Connection connection){
		if(connection != null){
			try{
				connection.setAutoCommit(false);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//查找数据库内容,返回一个对象
	public static <T> T getEntity(Class<T> clazz,String sql,Object ... args){
		//在try外声明,方便release这些变量
		T entity = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			//获取数据库链接
			connection = JDBCTools.getConnection();
			//准备查询
			preparedStatement = connection.prepareStatement(sql);
			//填充sql语句中的占位符
			for(int i = 0; i < args.length ; i++){
				preparedStatement.setObject(i + 1,args[i]);
			}
			//获取ResultSet对象
			resultSet = preparedStatement.executeQuery();
			//获取ResultSetMetaData对象
			ResultSetMetaData rsmd = resultSet.getMetaData();
			//创建map
			Map<String,Object> values = new HashMap<String,Object>();
			//如果resultset不为空 则将列名和值组成键值对放入map
			if(resultSet.next()){
				for(int i = 0; i < rsmd.getColumnCount(); i++){
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(columnLabel);
					values.put(columnLabel,columnValue);
				}
			}
			//如果map不为空 获取每一对键值对赋值给对应的对象
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
	//更新数据库内容
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
	/**
	 * 关闭数据库链接相关资源
	 */
	//关闭数据库链接相关资源
	public static void release(ResultSet rs,Statement statement,Connection connection){
		if(rs != null){try{rs.close();}catch(Exception e){e.printStackTrace();}}
		if(statement != null){try{statement.close();}catch(Exception e){e.printStackTrace();}}
		//数据库连接池中的Connection对象调用close方法并不是关闭链接,只是将链接归还给数据库连接池
		if(connection != null){try{connection.close();}catch(Exception e){e.printStackTrace();}}

	}
	/**获得数据库链接
	 *
	 */
	public static Connection getConnection() throws Exception{
        return null;
	}

    public static Connection getConnection(String databaseName,String username,String password){
        return null;
    }
    public static Connection getConnection(String username,String password){
        return null;
    }
}
