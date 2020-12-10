package com.payn.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 表分片规则算法
 *
 * @author: payn
 * @date: 2020/12/10 9:16
 */
public class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
		for (String each : tableNames) {
			if (each.endsWith(shardingValue.getValue() % 2 + "")) {
				return each;
			}
		}
		throw new UnsupportedOperationException();
	}
}
