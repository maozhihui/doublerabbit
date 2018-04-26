# 概述
本工程用于测试研究spring boot cache缓存接口以及其它开源实现。
 - spring boot与redis集成；
 - spring cache与redis集成;
 - 验证发布订阅模式。
## 关键指标
 - 缓存命中率
 - 缓存更新策略，如FIFO（先进先出）、LFU（最少使用）、LRU（最久未使用）、TTL（存活期）、TTI（空闲期）
 - 最大缓存容量
## 核心接口概念
 - Cache 提供了核心的几个缓存的操作接口，get,put,clear,evict等，该接口有多种不同的实现，如
 - CacheManager接口提供了缓存管理器的接口，如getCache，getCacheNames 
 - CacheResolver缓存解析器
 - KeyGenerator主键生成器
 - CacheConfig缓存配置
## 特性
 - 除了GuavaCacheManager之外，其它的Cache均是支持事务的，如果事务回滚了，缓存的数据也会恢复；
 - spring不进行缓存策略的维护，交由具体的Cache实现，它只是提供一个Wrapper，以统一友好的API呈现；
 - 提供了CompositeCacheManager来综合管理CacheManager
## 注解说明
 - @Cacheable可以标记在一个类上，也可以是在方法上，标记在类上表明该类的所有方法均是支持级存的，在方法上表明只有
 该方法支持。对应的是查询操作。对于注解了的方法而言，spring会在调用后将方法的返回值缓存起来，以便下次再调用该方法时可从缓存中取。
 至于缓存的key的话，支持默认与自定义两种策略。该注解包含三个属性（value,key,condition）
 value表时指明缓存名称，在spring配置中进行配置；
 key表明缓存的键，如果指定按SpEL表达式形式，如#userName，如果不指定则根据方法的参数进行组合；
 condition表示缓存的条件，可以用SpEL表达式，为true或者false，只有为true时才能缓存
 - @CachePut标记在方法上，每次执行方法时均会去更新缓存，对应的是插入与更新操作
 放入缓存；
 - @CacheEvict表时清除缓存，对应的是删除操作；
 - @Caching标注在类与方法上面，表明可以指定多个Cache 
 
# redis
## 配置加载
 - 使用默认的RedisAutoConfiguration类加载properties文件，以spring.redis开头的，并且注入了两个模板类RedisTemplate与StringRedisTemplate；
 - 手动写代码加载
 - 使用XML导入加载；
## CLI命令
 - select num 选择数据库
 - get set 命令，有固定的提示输入
 - keys * 列出当前所有的键值
 - flushdb 删除当前选择数据库中的所有key 
 - type key 用来查明key的类型
 参考文档：https://www.cnblogs.com/kongzhongqijing/p/6867960.html
## 问题
 - redis与ehcache的区别
 1，redis是独立运行的进程，可方便测试，而ehcache是与自身程序绑在一起的,ehcache使用起来非常简单；
 - 连接时出现connection abort socket write error
 或者使用telent时出现protected mode is enabled
解决方法：https://blog.csdn.net/fly43108622/article/details/52972433
如果仍然连接不上，检查防火墙，将bind端口由127.0.0.1改为具体IP 
 