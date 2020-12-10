package com.payn.config;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * 数据库配置
 *
 * @author: payn
 * @date: 2020/12/10 9:11
 */
public class DataSourceUtil {

	private static final String HOST = "localhost";

	private static final int PORT = 3306;

	private static final String USER_NAME = "root";

	private static final String PASSWORD = "root";

	public static DataSource createDataSource(final String dataSourceName) {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
		druidDataSource.setUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8", HOST, PORT, dataSourceName));
		druidDataSource.setUsername(USER_NAME);
		druidDataSource.setPassword(PASSWORD);
		return druidDataSource;
	}

}
