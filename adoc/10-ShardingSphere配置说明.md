# 10-ShardingSphere 配置说明

### 配置示例
```yaml
spring:
  shardingsphere:
    datasource:   #数据源配置
      names:
        ds0,ds1
      # 主数据源
      ds0:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/ds0?characterEncoding=utf-8
        username: root
        password: root
      ds1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/ds1?characterEncoding=utf-8
        username: root
        password: root
    sharding:      
      tables:
        tab_user:     #逻辑表
          actual-data-nodes: ds$->{0..1}.tab_user$->{0..1}  #表数量配置  
          database-strategy:
            inline:
              sharding-column: age  #分库分片键
              algorithm-expression: ds$->{age % 2}
          table-strategy: 
            inline:
              sharding-column: id   #分表分片键
              algorithm-expression: tab_user$->{id % 3}
#          key-generator:    #id生成策略
#            column: id
#            type: SNOWFLAKE    
    props:
      # 开启SQL显示，默认false
      sql:
        show: true   
```

**tab_user** ：是逻辑表名称，分表后创建的表应该是 tab_user_xxx。   
**actual-data-nodes** ：是数据节点由数据源和数据表组成，也就是真实表，使用行表达式  {0..1} 表示有 tab_user0 到 tab_user1 共2张表；shardingsphere 不会自动创建表，需要使用 脚本定时或手动提前创建好。  
**table-strategy** ：是分片策略，根据需求实现具体的分片策略，inline 为行表达式， sharding-column 为自定义分片。  
**algorithm-expression** ：是算法表达式，根据 id 取模尾数为 n 的路由到后缀为 n 的表中（tab_usern）。  
**key-generator** ：是主键生成，SNOWFLAKE 是Twitter的分布式 ID 生成算法。  

### 时间字段分片（如日志表）
```yaml
sharding:
  tables:
    sys_log: # 逻辑表
      actual-data-nodes: ds0.sys_log_20200116 # 默认表配置
      table-strategy:
        standard:
          sharding-column: log_time # 分片列
          preciseAlgorithmClassName: com.payn.config.SysLogDataTableShardingAlgorithm #精确分片
          rangeAlgorithmClassName:  com.payn.config.SysLogDataTableShardingAlgorithm #范围分片
      key-generator: #  id 生成策略
        column: id
        type: SNOWFLAKE
```
**preciseAlgorithmClassName** 和 **rangeAlgorithmClassName** 为分片支持和实现请查看 [分片算法](https://shardingsphere.apache.org/document/current/cn/features/sharding/concept/sharding/#%E5%88%86%E7%89%87%E7%AE%97%E6%B3%95) ，下面是按天分片实现，按月分片只需要改动部分代码即可

#### 关于查询：
- 分片后，如果查询 where 条件没有带分片字段的话会去扫描配置的所有真实数据表 = actual-data-nodes，最后将匹配到的数据合并为一个结果集返回，保存数据时找不到对应的表会报表不存在。
- 复杂SQL或者UNION可能会不支持，具体 [SQL使用规范](https://shardingsphere.apache.org/document/current/cn/features/sharding/use-norms/sql/)。


