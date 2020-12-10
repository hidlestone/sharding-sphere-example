# SpringBoot + shardingsphere + mysql 分库分表demo

### 一、项目概述

#### 1.1、项目说明
**场景**：在实际开发中，如果表的数据过大我们需要把一张表拆分成多张表，也可以垂直切分把一个库拆分成多个库，这里就是通过ShardingSphere实现`分库分表`功能。

#### 1.2、数据库设计
`分库` ds一个库分为 **ds0库** 和 **ds1库**。  
`分表`  tab_user一张表分为**tab_user0表** 和 **tab_user1表**。  

### 二、配置
properties
```
server.port=8090

#========这里换种方式，采用java配置实现分库分表==================

#指定mybatis信息
mybatis.config-location=classpath:mybatis-config.xml
##打印sql
spring.shardingsphere.props.sql.show=true

spring.shardingsphere.datasource.names=ds0,ds1

spring.shardingsphere.datasource.ds0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.ds0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds0.url=jdbc:mysql://localhost:3306/ds0?characterEncoding=utf-8
spring.shardingsphere.datasource.ds0.username=root
spring.shardingsphere.datasource.ds0.password=root

spring.shardingsphere.datasource.ds1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.ds1.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds1.url=jdbc:mysql://localhost:3306/ds1?characterEncoding=utf-8
spring.shardingsphere.datasource.ds1.username=root
spring.shardingsphere.datasource.ds1.password=root

#根据年龄分库
spring.shardingsphere.sharding.default-database-strategy.inline.sharding-column=age
spring.shardingsphere.sharding.default-database-strategy.inline.algorithm-expression=ds$->{age % 2}
#根据id分表
spring.shardingsphere.sharding.tables.tab_user.actual-data-nodes=ds$->{0..1}.tab_user$->{0..1}
spring.shardingsphere.sharding.tables.tab_user.table-strategy.inline.sharding-column=id
spring.shardingsphere.sharding.tables.tab_user.table-strategy.inline.algorithm-expression=tab_user$->{id % 2}
```
yml
```
server:
  port: 8090
  
#指定mybatis 配置文件位置
mybatis:
  config-location: classpath:mybatis-config.xml
  
spring:
  shardingsphere:
    datasource:
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
        tab_user: 
          actual-data-nodes: ds$->{0..1}.tab_user$->{0..1}
          database-strategy:
            inline:
              sharding-column: age
              algorithm-expression: ds$->{age % 2}
          table-strategy: 
            inline:
              sharding-column: id
              algorithm-expression: tab_user$->{id % 3}
    props:
      # 开启SQL显示，默认false
      sql:
        show: true         
```


### 三、测试验证
```
http://localhost:8090/save-user
```
从SQL语句可以看出 **ds0** 和 **ds1** 库中都插入了数据。

```
http://localhost:8090/list-user
``` 
可以看出虽然已经分库分表，但依然可以将多表数据聚合在一起并可以支持按**age排序**。  
`注意` ShardingSphere并不支持`CASE WHEN`、`HAVING`、`UNION (ALL)`，`有限支持子查询`。这个官网有详细说明。

### 参考资料
sharding-jdbc数据分片配置  
https://www.cnblogs.com/xiufengchen/p/10417133.html    
ShardingSphere 4.x Sharding-JDBC 用户手册之YAML配置手册  
https://my.oschina.net/u/3777515/blog/4450623  
使用 ShardingSphere 分表  
https://blog.csdn.net/qq_34808893/article/details/103993659  