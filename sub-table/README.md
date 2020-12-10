# SpringBoot + shardingsphere + mysql 分表demo

### 一、项目概述
`场景` 在实际开发中，如果表的数据过大，我们可能需要把一张表拆分成多张表，这里就是通过ShardingSphere实现分表功能，但不分库。

### 二、配置
properties
```
server.port=8090

#指定mybatis信息
mybatis.config-location=classpath:mybatis-config.xml

spring.shardingsphere.datasource.names=master

# 数据源 主库
spring.shardingsphere.datasource.master.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.master.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.master.url=jdbc:mysql://localhost:3306/masterdb?characterEncoding=utf-8
spring.shardingsphere.datasource.master.username=root
spring.shardingsphere.datasource.master.password=root

#数据分表规则
#指定所需分的表
spring.shardingsphere.sharding.tables.tab_user.actual-data-nodes=master.tab_user$->{0..2}
#指定主键
spring.shardingsphere.sharding.tables.tab_user.table-strategy.inline.sharding-column=id
#分表规则为主键除以3取模
spring.shardingsphere.sharding.tables.tab_user.table-strategy.inline.algorithm-expression=tab_user$->{id % 3}

#打印sql
spring.shardingsphere.props.sql.show=true
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
        master
      # 主数据源
      master:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/masterdb?characterEncoding=utf-8
        username: root
        password: root
    sharding:      
      tables:
        tab_user: 
          actual-data-nodes: master.tab_user$->{0..2}
          table-strategy: 
            inline:
              sharding-column: id
              algorithm-expression: tab_user$->{id % 3}
    defaultDataSourceName: master
    props:
      # 开启SQL显示，默认false
      sql:
        show: true    
```


### 三、测试验证
```
http://localhost:8090/save-user
```
可以从SQL语句可以看出 tab_user1 和 tab_user2 表插入了两条数据，而 tab_user0 表中插入一条数据。

```
http://localhost:8090/list-user
```
可以看出虽然已经分表，但依然可以将多表数据聚合在一起并可以排序。     
注意 ShardingSphere并不支持CASE WHEN、HAVING、UNION (ALL)，有限支持子查询。这个官网有详细说明。


### 参考资料
sharding-jdbc数据分片配置  
https://www.cnblogs.com/xiufengchen/p/10417133.html    
ShardingSphere 4.x Sharding-JDBC 用户手册之YAML配置手册  
https://my.oschina.net/u/3777515/blog/4450623  
使用 ShardingSphere 分表  
https://blog.csdn.net/qq_34808893/article/details/103993659  