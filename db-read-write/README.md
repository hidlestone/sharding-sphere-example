# SpringBoot + shardingsphere + mysql 读写分离demo

### 一、项目概述
#### 1.1、技术架构
> SpringBoot2.1.0 + shardingsphere4.0.0-RC1 + Maven3.5.4  + MySQL + lombok(插件)

#### 1.2、项目说明
场景 如果实际项目中Mysql是 Master-Slave (主从)部署的，那么数据保存到Master库，Master库数据同步数据到Slave库，数据读取到Slave库， 
这样可以减缓数据库的压力。 

#### 1.3、数据库设计
这个项目中Mysql服务器并没有实现主从部署,而是同一个服务器建立两个库，一个当做Master库，一个当做Slave库。所以这里是不能实现的功能就是Master库新增数据主动同步到Slave库。  
这里在同一个服务器建两个数据库来模拟主从数据库。

master_user.sql  
slave_user.sql  

### 二、配置
yml
```
server:
  port: 8080
  
#指定mybatis 配置文件位置
mybatis:
  config-location: classpath:mybatis-config.xml
  
spring:
  shardingsphere:
    datasource:
      names:
        master,slave
      # 主数据源
      master:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/masterdb?characterEncoding=utf-8
        username: root
        password: root
      # 从数据源
      slave:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/slavedb?characterEncoding=utf-8
        username: root
        password: root
    masterslave:
      # 读写分离配置
      load-balance-algorithm-type: round_robin
      # 最终的数据源名称
      name: dataSource
      # 主库数据源名称
      master-data-source-name: master
      # 从库数据源名称列表，多个逗号分隔
      slave-data-source-names: slave
    props:
      # 开启SQL显示，默认false
      sql:
        show: true

```
properties
```
erver.port=8080
#指定mybatis信息
mybatis.config-location=classpath:mybatis-config.xml

spring.shardingsphere.datasource.names=master,slave0

# 数据源 主库
spring.shardingsphere.datasource.master.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.master.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.master.url=jdbc:mysql://localhost:3306/masterdb?characterEncoding=utf-8
spring.shardingsphere.datasource.master.username=root
spring.shardingsphere.datasource.master.password=root
# 数据源 从库
spring.shardingsphere.datasource.slave0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.slave0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.slave0.url=jdbc:mysql://localhost:3306/slavedb?characterEncoding=utf-8
spring.shardingsphere.datasource.slave0.username=root
spring.shardingsphere.datasource.slave0.password=root

# 读写分离
spring.shardingsphere.masterslave.load-balance-algorithm-type=round_robin
spring.shardingsphere.masterslave.name=ms
spring.shardingsphere.masterslave.master-data-source-name=master
spring.shardingsphere.masterslave.slave-data-source-names=slave0
#打印sql
spring.shardingsphere.props.sql.show=true
```


### 三、测试验证
查询：
```
http://localhost:8080/list-user
```
保存：
```
http://localhost:8080/save-user
```
