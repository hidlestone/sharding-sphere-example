package com.payn.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.time.LocalDate;
import java.util.Collection;

/**
 * 精确分片
 * PreciseShardingAlgorithm：用于处理使用单一键作为分片键的 = 与 IN 进行分片的场景
 *
 * @author: payn
 * @date: 2020/12/10 10:47
 */
public class SysLogDataTableShardingAlgorithm extends SysLogDataTableSharding implements PreciseShardingAlgorithm<String> {

	@Override
	public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
		// 逻辑表名称
		String logicTableName = preciseShardingValue.getLogicTableName();
		// cure_time = preciseShardingValue.getValue();
		String cure_time = preciseShardingValue.getValue();
		// 将时间字符串转换为日期类型
		LocalDate parseDate = LocalDate.parse(cure_time, dtfTime);
		// 获取日期 yyyyMMdd
		String yyyyMMdd = parseDate.format(dtfDate);
		// 实际表名称
		String realTableName = spliceTableName(logicTableName, yyyyMMdd);
		System.out.println(realTableName);
		return realTableName;
	}
}
