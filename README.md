# 社区项目

![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-brightgreen.svg) ![Java](https://img.shields.io/badge/Java-1.8-blue.svg) ![Database](https://img.shields.io/badge/Database-MySQL-lightgrey.svg)

## 项目简介

一个基本功能完整的论坛项目，实现了基本的注册、登录、发帖、评论、回复、私信等功能，同时使用前缀树实现敏感词过滤；使用 Redis 实现点赞与关注；使用 Kafka 处理发送评论、点赞和关注等系统通知；使用 Elasticsearch 实现全文搜索，关键词高亮显示；使用 wkhtmltopdf 生成长图和 PDF；实现网站 UV 和 DAU 统计；[在线演示地址](http://43.142.11.98/)

## 核心技术  

```
- Spring Boot、SSM
- Redis、Kafka、ElasticSearch
- Spring Security、Quatz、Caffeine
```

## 测试账号

| 用户类型 | 用户名    | 密码   |
| -------- | --------- | ------ |
| 普通用户 | test      | 123    |
| 版主     | moderator | 123456 |
| 管理员   | admin     | 123456 |

## 功能列表

### 已经实现了的功能

- [x] 邮件发送
- [x] 注册
- [x] 验证码
- [x] 登录
- [x] 修改头像
- [x] 修改密码
- [x] 敏感词过滤
- [x] 发布帖子
- [x] 我的帖子
- [x] 帖子详情
- [x] 评论
- [x] 私信
- [x] 统一异常处理
- [x] 统一日志处理
- [x] 点赞
- [x] 关注
- [x] 系统通知
- [x] 搜索
- [x] 权限控制
- [x] 置顶、加精、删除
- [x] 网站统计
- [x] 定时执行任务计算热门帖子
- [x] 生成长图
- [x] 监控

### TO DO List

- [ ] 积分模块
- [ ] 收藏模块
- [ ] 浏览量
- [ ] 限流

## 功能简介

- 使用 Redis 的 set 实现点赞，zset 实现关注，并使用 Redis 存储登录ticket和验证码，解决分布式 Session 问题，使用 Redis 的高级数据类型 HyperLogLog 统计 UV (Unique Visitor)，使用 Bitmap 统计 DAU (Daily Active User)。
- 使用Redis Cell模块对用户发帖进行限流，防止恶意灌水。
- 使用 Kafka 处理发送评论、点赞、关注等系统通知、将新发布的帖子异步传输至Elasticsearch服务器，并使用事件进行封装，构建了强大的异步消息系统。
- 使用Elasticsearch做全局搜索，增加关键词高亮显示等功能。
- 热帖排行模块，使用本地缓存 Caffeine作为一级缓存和分布式缓存 Redis作为二级缓存构建多级缓存，避免了缓存雪崩，同时使用使用压测工具测试优化前后性能，将 QPS 提升了4.4倍 (7.6/sec -> 33.5/sec)，大大提升了网站访问速度。
- 使用 Quartz 定时更新热帖排行。
- 使用 Spring Security 做权限控制，替代拦截器的拦截控制，并使用自己的认证方案替代 Security 认证流程，使权限认证和控制更加方便灵活。

## 技术栈

| 技术            | 链接                                                         | 版本           |
| --------------- | ------------------------------------------------------------ | -------------- |
| Spring Boot     | https://spring.io/projects/spring-boot                       | 2.6.7          |
| Spring          | https://spring.io/projects/spring-framework                  | 5.3.19         |
| Spring MVC      | https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#spring-web | 5.3.19         |
| MyBatis         | http://www.mybatis.org/mybatis-3                             | 3.5.9          |
| Redis           | https://redis.io/                                            | 7.0.0          |
| Kafka           | http://kafka.apache.org/                                     | 2.8.0          |
| Elasticsearch   | https://www.elastic.co/cn/elasticsearch/                     | 7.13.4         |
| Spring Security | https://spring.io/projects/spring-security                   | 5.6.3          |
| Spring Quartz   | https://www.baeldung.com/spring-quartz-schedule              | 2.3.2          |
| wkhtmltopdf     | https://wkhtmltopdf.org                                      | 0.12.6         |
| kaptcha         | https://github.com/penggle/kaptcha                           | 2.3.2          |
| Thymeleaf       | https://www.thymeleaf.org/                                   | 3.0.15.RELEASE |
| MySQL           | https://www.mysql.com/                                       | 5.7.19         |
| JDK             | https://www.oracle.com/java/technologies/javase-downloads.html | 1.8            |

## 系统架构

![技术架构图](https://github.com/Liu-Vince/drawing-bed/blob/main/img/%E6%8A%80%E6%9C%AF%E6%9E%B6%E6%9E%84%E5%9B%BE.png?raw=true)

![网站架构图](https://github.com/Liu-Vince/drawing-bed/blob/main/img/%E7%BD%91%E7%AB%99%E6%9E%B6%E6%9E%84%E5%9B%BE.png?raw=true)

## 数据库初始化

```sql
create database community;
use community;
source /path/to/sql/init_schema.sql;
source /path/to/sql/init_data.sql;
source /path/to/sql/tables_mysql_innodb.sql;
```

## 运行

1. 安装JDK，Maven

2. 克隆代码到本地

   ```bash
   git clone https://github.com/Liu-Vince/community.git
   ```

3. 配置mysql、kafka、ElasticSearch

4. 启动zookeeper

5. 启动Kafka

6. 启动Elasticsearch

7. 运行打包命令

   ```bash
   mvn package
   ```

8. 运行项目

   ```bash
   java -jar xxx.jar
   ```

9. 访问项目

   ```
   http://localhost:8080
   ```

## 运行效果展示

#### 发帖

![发帖](https://github.com/Liu-Vince/drawing-bed/blob/main/img/%E5%8F%91%E5%B8%96.png?raw=true)

#### 帖子详情、评论

![帖子详情](https://github.com/Liu-Vince/drawing-bed/blob/main/img/%E5%B8%96%E5%AD%90%E8%AF%A6%E6%83%85%E8%AF%84%E8%AE%BA.png?raw=true)

#### 私信

![私信](https://github.com/Liu-Vince/drawing-bed/blob/main/img/%E7%A7%81%E4%BF%A1.png?raw=true)

#### 系统通知

![系统通知](https://github.com/Liu-Vince/drawing-bed/blob/main/img/%E7%B3%BB%E7%BB%9F%E9%80%9A%E7%9F%A5.png?raw=true)

#### 搜索

![搜索](https://github.com/Liu-Vince/drawing-bed/blob/main/img/%E6%90%9C%E7%B4%A2.png?raw=true)

#### 网站统计

![网站统计](https://github.com/Liu-Vince/drawing-bed/blob/main/img/%E7%BD%91%E7%AB%99%E7%BB%9F%E8%AE%A1.png?raw=true)

## 更新日志

* 2021-4-8 创建项目
* 2021-5-26部署
