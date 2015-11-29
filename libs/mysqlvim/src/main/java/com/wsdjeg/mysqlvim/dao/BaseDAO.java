package com.wsdjeg.mysqlvim.dao;

import com.wsdjeg.mysqlvim.util.JDBCTools;
import com.wsdjeg.mysqlvim.util.ReflectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.wsdjeg.mysqlvim.dao.DAO;
public class BaseDAO<T> implements DAO<T> {
	private QueryRunner queryRunner = new QueryRunner();
	private Class<T> clazz;
	public BaseDAO(){
		clazz = ReflectionUtils.getSuperGenericType(getClass());
	}
	public void batch(String sql, Object[] ... params){

	}
	@SuppressWarnings("unchecked")
	public <V> V getSingleVal(String sql, Object ... args){
		Connection connection = null;
		try {
			connection = JDBCTools.getConnection();
			return (V)queryRunner.query(connection,sql,new ScalarHandler(),args);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null,null,connection);
		}
		return null;

	}
	public List<T> queryForList(String sql ,Object ... args) {
		Connection connection = null;
		try {
			connection = JDBCTools.getConnection();
			return queryRunner.query(connection,sql,new BeanListHandler<>(clazz),args);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null,null,connection);
		}
		return null;

	}
	public T query(String sql,Object ... args){
		Connection connection = null;
		try {
			connection = JDBCTools.getConnection();
			return queryRunner.query(connection,sql,new BeanHandler<>(clazz),args);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null,null,connection);
		}
		return null;

	}
	public void update(String sql,Object ... args) {
		Connection connection = null;
		try {
			connection = JDBCTools.getConnection();
			queryRunner.update(connection,sql,args);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null,null,connection);
		}

	}
	public long  insert(String sql,Object ... args) {
		long id = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i + 1,args[i]);
				}
			}
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null,null,connection);
		}
		return id;
	}
}

