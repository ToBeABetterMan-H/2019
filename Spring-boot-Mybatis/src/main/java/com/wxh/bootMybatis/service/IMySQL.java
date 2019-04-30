package com.wxh.bootMybatis.service;

public interface IMySQL {
	/**
	 * 参照数据库设计文档造记录
	 * 
	 * @param tableName 目标表名
	 * @param fileName  读取文档全路径
	 * @param count     要造的数据量
	 * 
	 * @author wxh
	 * @return 数据库插入语句
	 */
	String cRecordDependOnDocument(String tableName, String fileName, Integer count);
}
