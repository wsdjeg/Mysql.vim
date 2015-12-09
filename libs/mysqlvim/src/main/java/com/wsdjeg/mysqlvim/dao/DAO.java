package com.wsdjeg.mysqlvim.dao;

import java.util.List;
public interface DAO<T> {
	/**
	 * 执行插入操作,返回执行以后的ID
	 * @param sql
	 * @param args
	 * @return
	 */
	long insert(String sql,Object ... args);
	/**
	 * 执行update操作 包括insert delete update 但是没有返回值
	 * @param sql
	 * @param args
	 */
	void update(String sql,Object ... args);
	/**
	 * 查询 返回 指定类的实例
	 */
	T query(String sql,Object ... args);
	/**
	 * 查询 返回 指定类实例的集合
	 */
	List<T> queryForList(String sql,Object ... args);
	/**
	 * 返回某一字段
	 */
	<V> V getSingleVal(String sql,Object ... args);
	/**
	 * 批量处理
	 */
	void batch(String sql,Object[] ... params);
}
