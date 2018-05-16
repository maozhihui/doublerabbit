#版本
 - kafka server版本 `kafka_2.11-1.1.0`
 - spring boot kafka 版本 `1.0.6.RELEASE`
 
#验证功能项
 - 与spring boot 集成，生产者与消费者实现；
 - 单版版，单个用户组多个消费者，一个topic中只有一个分区情况下，只有一个消息者能够接收到全部数据；
 - 单版版，多个用户组多个消费者，每个用户组只有一个消费者，一个topic中只有一个分区情况下，每个消费者均能够收到全部数据，这也就是发布订阅模式；
 - 集群版，单个用户组多个消费者，一个topic中有多个分区，分区数量等于消费者数量，各个消费者接收到消息数量之和等于生产者生产消息总数，这也就是队列模式；
 - 集群版，多个用户组多个消费者，每个用户组只有一个消费者，一个topic中有多个分区情况下，每个消费者均能接收到全部数据；
 
#问题 
1，spring boot kafka报错org.apache.kafka.common.errors.TimeoutException: Failed to update metadata after 60000 ms
原因可能有两种，一种是kafka client与kafka server的版本不一致，一种是IP与域名的问题，将kafka配置文件server.properties中的默认listeners=PLAINTEXT://:9092
改成listeners=PLAINTEXT://10.10.107.104:9092具体IP形式，而客户端的配置bootstrap-servers=10.10.107.104:9092
2，如何修改topic的分区数量？
```./kafka-topics.sh --zookeeper 127.0.0.1:2181 --alter --topic register --partitions 2``
3，如何查看topic的状态信息？
```./kafka-topics.sh --describe --zookeeper 127.0.0.1:2181 --topic register``
4，如何查看到zookeeper的相关信息？
如果使用的是`kafka`自带的zookeeper，进行`bin`目录下，执行命令`./zookeeper-shell.sh 10.10.107.104:2181`可进入CLI命令行,然后使用`help`命令即可查看到支持的相关的命令.
