package com.payn.config;

import java.time.format.DateTimeFormatter;

/**
 * sys_log 精确分片算法。通用部分。
 * 按天分片实现
 *
 * @author: payn
 * @date: 2020/12/10 10:44
 */
public class SysLogDataTableSharding {

	protected static final String UNDERLINE = "_";
	// cure_time 日期格式
	protected static final DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	protected static final DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("yyyyMMdd");

	protected static String spliceTableName(String logicTableName, String date) {
		StringBuilder tableName = new StringBuilder();
		tableName.append(logicTableName).append(UNDERLINE).append(date);
		return tableName.toString();
	}

}
