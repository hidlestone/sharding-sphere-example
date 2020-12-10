package com.payn.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * TODO 基于Java配置
 * shardingsphere 连接数据库信息
 *
 * @author: payn
 * @date: 2020/12/10 9:13
 */
//@Configuration
//@ComponentScan(basePackageClasses = DalModule.class)
//@MapperScan(basePackages = "com.payn.mapper")
public class DalModule {

	/**
	 * SqlSessionFactory 实体
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setFailFast(true);
		sessionFactory.setMapperLocations(resolver.getResources("classpath:/mapper/*Mapper.xml"));
		return sessionFactory.getObject();
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
		shardingRuleConfig.getBindingTableGroups().add("tab_user");
//		shardingRuleConfig.getBroadcastTables().add("t_config");
		//TODO 根据ID分表，一共分为3张表
		shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "master${id % 3}"));
		shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new PreciseModuloShardingTableAlgorithm()));
		return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
	}

	private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
		KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
		return result;
	}

	TableRuleConfiguration getOrderTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration("tab_user", "master.tab_user${0..2}");
		result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
		return result;
	}


	Map<String, DataSource> createDataSourceMap() {
		Map<String, DataSource> result = new HashMap<>();
		result.put("master", DataSourceUtil.createDataSource("masterdb"));
//		result.put("slave", DataSourceUtil.createDataSource("slavedb"));
		return result;
	}

}
