package com.payn.config;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 范围分片
 * RangeShardingAlgorithm：用于处理使用单一键作为分片键的BETWEEN AND、>、<、>=、<=进行分片的场景
 *
 * @author: payn
 * @date: 2020/12/10 10:53
 */
public class SysLogDataTableRangeShardingAlgorithm extends SysLogDataTableSharding implements RangeShardingAlgorithm<String> {

	@Override
	public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
		// 逻辑表名称
		String logicTableName = rangeShardingValue.getLogicTableName();
		Range<String> ranges = rangeShardingValue.getValueRange();
		// 获取时间范围
		try {
			String lower = ranges.lowerEndpoint();
			String upper = ranges.upperEndpoint();
			LocalDateTime startTime = LocalDateTime.parse(lower, dtfTime);
			LocalDateTime endTime = LocalDateTime.parse(upper, dtfTime);
			Collection<String> rangeTables = getRangeTables(logicTableName, startTime, endTime);
			System.out.println(rangeTables);
			return rangeTables;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collection;
	}

	/**
	 * LocalDateTime 计算时间表范围
	 *
	 * @param logicTableName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private static Collection<String> getRangeTables(String logicTableName, LocalDateTime startTime, LocalDateTime endTime) {
		Collection<String> tables = new LinkedHashSet<>();
		while (startTime.isBefore(endTime)) {
			String yyyyMMdd = startTime.toLocalDate().format(dtfDate);
			String realTableName = spliceTableName(logicTableName, yyyyMMdd);
			tables.add(realTableName);
			startTime = startTime.plusDays(1);
		}
        /*当 startTime 的 HH:mm:ss 大于 endTime 时，day + 1 会出现 startTime 大于 endTime
        导致 endTime 对应的表没有添加到 tables 列表中 */
		String endDate = endTime.toLocalDate().format(dtfDate);
		String lastTable = spliceTableName(logicTableName, endDate);
		if (!tables.contains(lastTable)) {
			tables.add(lastTable);
		}
		return tables;
	}

}
