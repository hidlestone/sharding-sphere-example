# sharding-sphere-example
> ShardingSphere 分科分表/读写分离 示例

shardingsphere 中文官网：  
https://shardingsphere.apache.org/document/current/cn/overview/


## 一、项目概述
#### 1.1、技术架构
项目总体技术选型
```
SpringBoot2.1.0 + shardingsphere4.0.0-RC1 + Maven3.5.4  + MySQL + lombok(插件)
```

#### 1.2、项目说明

`场景` 在实际开发中，如果数据库压力大我们可以通过  **分库分表**  的基础上进行 **读写分离**，来减缓数据库压力。

#### 1.3、项目整体结构

```makefile
sharding-sphere-example # 父工程
 
  | #实现读写分离功能
  ---db-read-write 
 
  | #实现分表功能
  ---sub-table
    
  | #实现分库分表功能
  ---sub-db-table                      
     
  | #实现分表 + 读写分离
  ---sub-table-read-write                                        
  
   | #实现分库分表 + 读写分离
  ---sub-db-table-read-write                  
                        
```
