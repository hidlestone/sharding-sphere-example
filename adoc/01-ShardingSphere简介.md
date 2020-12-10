# 01-ShardingSphere简介

### 概念
ShardingSphere是一套开源的分布式数据库中间件解决方案组成的生态圈，它由Sharding-JDBC、Sharding-Proxy 和 Sharding-Sidecar这3款相互独立的产品组成。  
均提供标准化的数据分片、分布式事务 和 数据库治理功能，可适用于如Java同构、异构语言、云原生等各种多样化的应用场景。

![ShardingSphere架构](./images/01.png)

### 功能
#### 数据分片
- 分库 & 分表
- 读写分离
- 分片策略定制化
- 无中心化分布式主键

#### 分布式事务
- 标准化事务接口
- XA强一致事务
- 柔性事务

#### 数据库治理
- 配置动态化
- 编排 & 治理
- 数据脱敏
- 可视化链路追踪
- 弹性伸缩(规划中)





ShardingSphere(理论)
https://www.cnblogs.com/qdhxhz/p/11629883.html