package com.lifeng.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class EnumHandler implements TypeHandler<Object>{
	
	/** 
	 * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型 
	 * @param rs 当前的结果集 
	 * @param columnName 当前的字段名称 
	 * @return 转换后的Java对象 
	 * @throws SQLException 
	 */ 
	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}
	 /** 
     * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型 
     * @param rs 当前的结果集 
     * @param columnIndex 当前字段的位置 
     * @return 转换后的Java对象 
     * @throws SQLException 
     */ 
	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}
	/** 
     * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型 
     * @param cs 当前的CallableStatement执行后的CallableStatement 
     * @param columnIndex 当前输出参数的位置 
     * @return 
     * @throws SQLException 
     */ 
	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}
	 /** 
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型 
     * @param ps 当前的PreparedStatement对象 
     * @param i 当前参数的位置 
     * @param parameter 当前参数的Java对象 
     * @param jdbcType 当前参数的数据库类型 
     * @throws SQLException 
     */  
	@Override
	public void setParameter(PreparedStatement ps, int arg1, Object arg2, JdbcType type) throws SQLException {
		
	}
}
