/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/8/3 16:13:25                            */
/*==============================================================*/
CREATE DATABASE IF NOT EXISTS iotstp;
USE iotstp;

DROP TABLE IF EXISTS `AREA`;

DROP TABLE IF EXISTS `ATTRIBUTES_TEMPLATE`;

DROP TABLE IF EXISTS `AUDIT_LOG`;

DROP TABLE IF EXISTS `CATEGORY`;

DROP TABLE IF EXISTS `CITY`;

DROP TABLE IF EXISTS `CONFIG_ATTRIBUTES`;

DROP TABLE IF EXISTS `DEVICE`;

DROP TABLE IF EXISTS `DEVICE_TEMPLATE`;

DROP TABLE IF EXISTS `DEVICE_TOKEN`;

DROP TABLE IF EXISTS `HISTORY_DATA`;

DROP TABLE IF EXISTS `LATEST_DATA`;

DROP TABLE IF EXISTS `ORGANIZATION`;

DROP TABLE IF EXISTS `PLUGIN`;

DROP TABLE IF EXISTS PRODUCT;

DROP TABLE IF EXISTS `PROVINCE`;

DROP TABLE IF EXISTS `RULE`;

DROP TABLE IF EXISTS `RULE_DEVICE`;

DROP TABLE IF EXISTS `TELEMETRY_ATTRIBUTES`;

DROP TABLE IF EXISTS `TENANT`;

DROP TABLE IF EXISTS `USER`;

DROP TABLE IF EXISTS `USER_DEVICE`;

DROP TABLE IF EXISTS `FORWARD_RULE`;

DROP TABLE IF EXISTS DEVICE_CAMERA;

DROP TABLE IF EXISTS ALARM_RULE;

DROP TABLE IF EXISTS ALARM_CONTENT;

DROP TABLE IF EXISTS ACTIVE_ALARM;

DROP TABLE IF EXISTS HISTORY_ALARM;

/*==============================================================*/
/* Table: `ATTRIBUTES_TEMPLATE`                                 */
/*==============================================================*/
CREATE TABLE `ATTRIBUTES_TEMPLATE`
(
   `ID`                 VARCHAR(36) NOT NULL COMMENT '主键标识',
   `DEVICE_TEMPLATE_ID` VARCHAR(36),
   `NAME`               CHAR(20) NOT NULL COMMENT '参数名称',
   `VALUE_TYPE`         VARCHAR(20) COMMENT '参数的数据类型',
   `IS_TELEMETRY`       BOOL DEFAULT FALSE COMMENT '是否为遥测参数',
   `READ_ONLY`          BOOL DEFAULT 2 COMMENT '读与写权限，0-读，1-写，2-读写',
   `IS_OPTIONAL`        BOOL COMMENT '是否可选',
   `UNIT`               VARCHAR(10) COMMENT '单位',
   `VALUE`              VARCHAR(36),
   `DESCRIPTION`        VARCHAR(200) COMMENT '参数描述',
   `CREATE_TIME`        DATETIME,
   `UPDATE_TIME`        DATETIME,
   PRIMARY KEY (`ID`)
);

ALTER TABLE `ATTRIBUTES_TEMPLATE` COMMENT '属性参数模板，包括控制与遥测两类，是否读写进行区分';

/*==============================================================*/
/* Table: `AUDIT_LOG`                                           */
/*==============================================================*/
CREATE TABLE `AUDIT_LOG`
(
   `USE_ID`             VARCHAR(36),
   `ID`                 VARBINARY(36),
   `CREATE_BY`          VARBINARY(36),
   `CREATE_DATE`        DATETIME,
   `DESCRIPTION`        VARCHAR (255),
   `REQUEST_IP`         VARBINARY(20),
   `TYPE`               INT,
   `USER_ID`            VARBINARY(36)
);

/*==============================================================*/
/* Table: `CATEGORY`                                            */
/*==============================================================*/
CREATE TABLE `CATEGORY`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `PARENT_ID`          VARCHAR(36),
   `CODE`               CHAR(20) COMMENT '分类编码，大、中、小类',
   `NAME`               VARCHAR(30) COMMENT '类别名称',
   `LEVEL_FLAG`         SMALLINT COMMENT '关联层级',
   PRIMARY KEY (`ID`)
);

ALTER TABLE `CATEGORY` COMMENT '产品分类表，大类如智慧医疗、智能家居等';


/*==============================================================*/
/* Table: `CONFIG_ATTRIBUTES`                                   */
/*==============================================================*/
CREATE TABLE `CONFIG_ATTRIBUTES`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `DEV_ID`             VARCHAR(36),
   `ATTRIBUTE_SCOPE`    SMALLINT NOT NULL COMMENT '属性类别，client,shared,server',
   `ATTRIBUTE_TEMPLATE_ID`     VARCHAR(36) COMMENT '属性Id',
   `VALUE_TYPE`         VARCHAR(20) COMMENT '属性值',
   `READ_ONLY`          BOOL COMMENT '属性描述',
   `IS_OPTIONAL`        BOOL COMMENT '是否可选',
   `DESCRIPTION`        VARCHAR(200),
   `CREATE_TIME`        DATETIME,
   `UPDATE_TIME`        DATETIME,
   `INT_VALUE`          INT,
   `STRING_VALUE`       VARCHAR(20),
   `FLOAT_VALUE`        FLOAT,
   `VALUE`              VARCHAR(36),
   PRIMARY KEY (`ID`)
);

ALTER TABLE `CONFIG_ATTRIBUTES` COMMENT '设备配置属性';

/*==============================================================*/
/* Table: `DEVICE`                                              */
/*==============================================================*/
CREATE TABLE `DEVICE`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `TENANT_ID`          VARCHAR(36),
   `PRODUCT_ID`         VARCHAR(36),
   `NAME`               VARCHAR(30) NOT NULL COMMENT '设备名称',
   `HARD_IDENTITY`      VARCHAR(50) COMMENT '设备唯一标识',
   `SN`                 VARCHAR(50) NOT NULL COMMENT '设备编号',
   `USER_NAME`          VARCHAR(20) DEFAULT 'test' COMMENT '鉴权用户名',
   `SECRET_KEY`         VARCHAR(100) DEFAULT '123456' COMMENT '鉴权密钥',
   `CATEGORY`           VARCHAR(50) COMMENT '设备分类',
   `DESCRIPTION`        VARCHAR(200) COMMENT '设备描述',
   `IS_GATEWAY`         SMALLINT NOT NULL COMMENT '是否为网关',
   `GATEWAY_ID`         VARCHAR(36) DEFAULT '',
   `CREATE_TIME`        DATETIME,
   `UPDATE_TIME`        DATETIME,
   `DEVICE_TEMPLATE_ID` VARCHAR(36),
   `STATUS`             SMALLINT DEFAULT 0,
  `POSITION`           VARCHAR(100) COMMENT '位置信息',
   `DEVICE_CAMERA_ID`   VARCHAR(36) COMMENT '摄像头id',
   `is_lora`   VARCHAR(36) COMMENT '是否同步lora',
   `app_key`   VARCHAR(36) COMMENT 'appkey',
   PRIMARY KEY (`ID`)
);

ALTER TABLE `DEVICE` COMMENT '设备';


/*==============================================================*/
/* Table: DEVICE_CAMERA                                         */
/*==============================================================*/
CREATE TABLE DEVICE_CAMERA
(
  ID                   VARCHAR(36) NOT NULL,
  DEV_ID               VARCHAR(36) COMMENT '设备id',
  USER_NAME            VARCHAR(36) COMMENT '用户名',
  PWD                  VARCHAR(36) COMMENT '密码',
  SERVER_IP            VARCHAR(100) COMMENT '服务器ip',
  PORT                 INT(10) COMMENT '端口',
  PROXY_IP             VARCHAR(36) COMMENT '代理ip',
  PRIMARY KEY (ID)
);

ALTER TABLE DEVICE_CAMERA COMMENT '摄像头拓展字段';

/*==============================================================*/
/* Table: `DEVICE_TEMPLATE`                                     */
/*==============================================================*/
CREATE TABLE `DEVICE_TEMPLATE`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `CATEGORY_ID`        VARCHAR(36),
   `NAME`               VARCHAR(50) NOT NULL COMMENT '模板名称',
   `CREATE_TIME`        DATETIME,
   `UPDATE_TIME`        DATETIME,
   `DESCRIPTION`        VARCHAR(36),
   PRIMARY KEY (`ID`)
);

ALTER TABLE `DEVICE_TEMPLATE` COMMENT '设备模板表';

/*==============================================================*/
/* Table: `DEVICE_TOKEN`                                        */
/*==============================================================*/
CREATE TABLE `DEVICE_TOKEN`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `DEV_ID`             VARCHAR(36),
   `TOKEN`              VARCHAR(36),
   `TOKEN_CREATETIME`   DATETIME,
   `SESSION_UPDATETIME` DATETIME,
   PRIMARY KEY (`ID`)
);

/*==============================================================*/
/* Table: `HISTORY_DATA`                                        */
/*==============================================================*/
CREATE TABLE `HISTORY_DATA`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `ATTRIBUTE_ID`       VARCHAR(36) NOT NULL,
   `INT_VALUE`          INT,
   `STRING_VALUE`       VARCHAR(50),
   `FLOAT_VALUE`        FLOAT,
   `VALUE`              VARCHAR(40),
   `UPDATE_TIME`        TIMESTAMP NOT NULL,
   `DEV_ID`             VARCHAR(36),
   `ATTRIBUTE_NAME`     VARCHAR(36),
   PRIMARY KEY (`ID`)
);

/*==============================================================*/
/* Table: `HISTORY_DATA`                                        */
/*==============================================================*/
CREATE TABLE `LATEST_DATA`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `ATTRIBUTE_ID`       VARCHAR(36) NOT NULL,
   `INT_VALUE`          INT,
   `STRING_VALUE`       VARCHAR(50),
   `FLOAT_VALUE`        FLOAT,
   `VALUE`              VARCHAR(40),
   `UPDATE_TIME`        TIMESTAMP NOT NULL,
   `DEV_ID`             VARCHAR(36),
   `ATTRIBUTE_NAME`     VARCHAR(36),
   PRIMARY KEY (`ID`)
);

/*==============================================================*/
/* Table: `ORGANIZATION`                                        */
/*==============================================================*/
CREATE TABLE `ORGANIZATION`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `PARENT_ID`          VARCHAR(36),
   `CODE`               VARCHAR(20),
   `NAME`               VARCHAR(30),
   `TENANT_ID`          VARCHAR(50),
   `LEVEL_FLAG`         SMALLINT,
   PRIMARY KEY (`ID`)
);

/*==============================================================*/
/* Table: `PLUGIN`                                              */
/*==============================================================*/
CREATE TABLE `PLUGIN`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `TENANT_ID`          VARCHAR(36),
   `NAME`               VARCHAR(50) COMMENT '插件名称',
   `CLAZZ`              VARCHAR(120) COMMENT '插件处理类名',
   `CONFIGURATION`      TEXT COMMENT '插件配置信息',
   `PUBLICACCESS`       VARCHAR(10),
   `ADDITIONALINFO`     VARCHAR(100),
   `CREATE_TIME`        DATETIME,
   `UPDATE_TIME`        DATETIME,
   PRIMARY KEY (`ID`)
);

ALTER TABLE `PLUGIN` COMMENT '插件配置表';

/*==============================================================*/
/* Table: PRODUCT                                               */
/*==============================================================*/
CREATE TABLE `PRODUCT`
(
   `ID`                   VARCHAR(36) NOT NULL,
   `CATEGORY_ID`          VARCHAR(36),
   `TENANT_ID`            VARCHAR(36),
   `NAME`                 VARCHAR(20) COMMENT '产品名称',
   `BRIEF`                VARCHAR(300) COMMENT '产品简介',
   `ACCESS_PROTOCOL`      VARCHAR(20) COMMENT '接入协议',
   `CREATE_TIME`          DATETIME,
   `UPDATE_TIME`          DATETIME,
   `TYPE`				  SMALLINT COMMENT '产品类型：1，通用类型，2，用户自定义类型',
   `MODEL`				  VARCHAR(36) COMMENT '产品型号',
   PRIMARY KEY (ID)
);


/*==============================================================*/
/* Table: `RULE`                                                */
/*==============================================================*/
CREATE TABLE `RULE`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `TENANT_ID`          VARCHAR(36),
   `NAME`               VARCHAR(50) COMMENT '规则名称',
   `PLUGIN_ID`          VARCHAR(36),
   `FILTERS`            TEXT COMMENT '过滤配置',
   `PROCESSOR`          TEXT COMMENT '预处理器配置',
   `ACTION`             TEXT COMMENT '规则与插件适配器',
   `ADDITIONALINFO`     VARCHAR(200),
   `CREATE_TIME`        DATETIME,
   `UPDATE_TIME`        DATETIME,
   `PRODUCT_ID`          VARCHAR(36) COMMENT '产品Id ',
   PRIMARY KEY (`ID`)
);

ALTER TABLE `RULE` COMMENT '规则表';

/*==============================================================*/
/* Table: `RULE_DEVICE`                                         */
/*==============================================================*/
CREATE TABLE `RULE_DEVICE`
(
   `RULE_ID`            VARCHAR(36) NOT NULL,
   `DEV_ID`             VARCHAR(36) NOT NULL,
   PRIMARY KEY (`RULE_ID`, `DEV_ID`)
);

/*==============================================================*/
/* Table: `TELEMETRY_ATTRIBUTES`                                */
/*==============================================================*/
CREATE TABLE `TELEMETRY_ATTRIBUTES`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `DEV_ID`             VARCHAR(36),
   `ATTRIBUTE_NAME`     VARCHAR(30) NOT NULL COMMENT '遥测属性名',
   `UNIT`               VARCHAR(10) COMMENT '单位',
   `VALUE_TYPE`         VARCHAR(20) NOT NULL COMMENT '值类型',
   `UPDATE_TIME`        TIMESTAMP,
   `DESCRIPTION`        VARCHAR(50),
   PRIMARY KEY (`ID`)
);

ALTER TABLE `TELEMETRY_ATTRIBUTES` COMMENT '设备遥测属性';

/*==============================================================*/
/* Table: `TENANT`                                              */
/*==============================================================*/
CREATE TABLE `TENANT`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `NAME`               VARCHAR(50),
   `ADDITIONAL_INFO`    VARCHAR(100) COMMENT 'tenant类型，system_tenant与tenant',
   PRIMARY KEY (`ID`)
);

/*==============================================================*/
/* Table: `USER`                                                */
/*==============================================================*/
CREATE TABLE `USER`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `AREA_ID`            VARCHAR(36),
   `TENANT_ID`          VARCHAR(36),
   `USER_NAME`          VARCHAR(50) COMMENT '用户名',
   `ACCOUNT`            VARCHAR(25) NOT NULL,
   `PWD`                VARCHAR(50) COMMENT '密码',
   `EMAIL`              VARCHAR(50) COMMENT '邮箱',
   `MSISDN`             VARCHAR(20) COMMENT '手机号码',
   `DETAIL_ADDRESS`     VARCHAR(200) COMMENT '详细地址',
   `STATE_FLAG`         SMALLINT COMMENT '用户状态标识',
   `AUTHORITY`          VARCHAR(20) COMMENT '用户鉴权类型，sys_admin,tenant_admin,customer_user',
   `ADDITIONAL_INFO`    VARCHAR(128),
   `CREATE_TIME`        DATETIME COMMENT '注册时间',
   `UPDATE_TIME`        DATETIME COMMENT '更新时间',
   `TYPE`               INT(10),
   PRIMARY KEY (`ID`)
);

/*==============================================================*/
/* Table: `USER_DEVICE`                                         */
/*==============================================================*/
CREATE TABLE `USER_DEVICE`
(
   `DEV_ID`             VARCHAR(36) NOT NULL,
   `USER_ID`            VARCHAR(36) NOT NULL,
   PRIMARY KEY (`DEV_ID`, `USER_ID`)
);

/*==============================================================*/
/* Table: `FORWARD_RULE`                                         */
/*==============================================================*/
CREATE TABLE `FORWARD_RULE`
(
   `ID`                 VARCHAR(36) NOT NULL,
   `TENANT_ID`          VARCHAR(36) NOT NULL,
   `NAME`               VARCHAR(36) NOT NULL,
   `EVENT`              VARCHAR(36) NOT NULL,
   `TYPE`               VARCHAR(36) NOT NULL,
   `DST`                VARCHAR(150) NOT NULL,
   `CREATE_TIME`        DATETIME COMMENT '创建时间',
   `UPDATE_TIME`        DATETIME COMMENT '更新时间',
   PRIMARY KEY (`ID`)
);

CREATE TABLE app_version
(
  ID          VARCHAR(36)  NOT NULL
    PRIMARY KEY,
  VERSION     VARCHAR(36)  NULL
  COMMENT '版本号',
  FILE_NAME   VARCHAR(100) NULL
  COMMENT '文件名',
  FILE_SIZE   VARCHAR(100) NULL
  COMMENT '文件大小',
  FILE_DESC   VARCHAR(100) NULL
  COMMENT '文件描述',
  CREATE_TIME DATETIME     NULL
  COMMENT '创建时间',
  PATH        VARCHAR(36)  NULL
  COMMENT '上传路径',
  tenant_id   VARCHAR(36)  NULL
);

CREATE TABLE upgrade_record
(
  id          VARCHAR(36) NOT NULL
    PRIMARY KEY,
  version_id  VARCHAR(36) NULL
  COMMENT '升级版本id',
  task_id     VARCHAR(36) NULL
  COMMENT '任务id',
  tenant_id   VARCHAR(36) NULL,
  device_id   VARCHAR(36) NULL
  COMMENT '设备id',
  status      INT(5)      NULL
  COMMENT '升级状态',
  create_time DATETIME    NULL
  COMMENT '创建时间'
);

CREATE TABLE upgrade_task
(
  id          VARCHAR(36)  NOT NULL PRIMARY KEY,
  name        VARCHAR(36)  NULL COMMENT '任务名称',
  version_id  VARCHAR(36)  NULL COMMENT '版本id',
  status      INT(5)       NULL COMMENT '任务下发状态 1-未下发 2-已下发',
  device_num  INT(10)      NULL COMMENT '升级设备总数',
  success_num INT(10)      NULL COMMENT '升级成功数',
  task_desc   VARCHAR(100) NULL COMMENT '描述',
  create_time DATETIME     NULL COMMENT '创建时间',
  tenant_id   VARCHAR(36)  NULL COMMENT '租户id',
  dev_ids     VARCHAR(100) NULL COMMENT '设备id列表'
);

/*==============================================================*/
/* Table: ALARM_RULE                                            */
/*==============================================================*/
CREATE TABLE ALARM_RULE
(
  ID                   VARCHAR(36) NOT NULL,
  TENANT_ID            VARCHAR(36) COMMENT '租户id',
  PRODUCT_ID           VARCHAR(36) COMMENT '产品Id ',
  NAME                 VARCHAR(50) COMMENT '规则名称',
  ATTRIBUTE            VARCHAR(36) COMMENT '属性',
  RULE_CONDITION       VARCHAR(36) COMMENT '条件',
  VALUE                VARCHAR(36) COMMENT '对比值',
  TYPE                 INT(5) COMMENT '类型，1，告警通知，2，告警恢复',
  ALARM_ID             VARCHAR(36) COMMENT '告警id',
  ALARM_CONTENT        VARCHAR(36) COMMENT '告警内容',
  CREATE_TIME          DATETIME COMMENT '创建时间',
  UPDATE_TIME          DATETIME COMMENT '更新时间',
  PRIMARY KEY (ID)
);

ALTER TABLE ALARM_RULE COMMENT '告警规则';

/*==============================================================*/
/* Table: ALARM_CONTENT                                         */
/*==============================================================*/
CREATE TABLE ALARM_CONTENT
(
  ID                   VARCHAR(36) NOT NULL,
  ALARM_ID             VARCHAR(36) COMMENT '告警id',
  ALARM_NAME           VARCHAR(100) COMMENT '告警名称',
  ALARM_CONTENT        TEXT COMMENT '告警内容',
  ALARM_LEVEL          INT(10) COMMENT '告警等级',
  CREATE_TIME          DATETIME COMMENT '告警时间',
  PRIMARY KEY (ID)
);

ALTER TABLE ALARM_CONTENT COMMENT '告警类型';

/*==============================================================*/
/* Table: ACTIVE_ALARM                                          */
/*==============================================================*/
CREATE TABLE ACTIVE_ALARM
(
  ID                   VARCHAR(36) NOT NULL,
  TENANT_ID            VARCHAR(36) COMMENT '租户id',
  PRODUCT_ID           VARCHAR(36) COMMENT '产品Id ',
  DEV_ID               VARCHAR(36) COMMENT '设备id',
  ALARM_ID             VARCHAR(36) COMMENT '告警id',
  ALARM_CONTENT        VARCHAR(36) COMMENT '告警内容',
  START_TIME           DATETIME COMMENT '告警时间',
  PRIMARY KEY (ID)
);

ALTER TABLE ACTIVE_ALARM COMMENT '活动告警';

/*==============================================================*/
/* Table: HISTORY_ALARM                                         */
/*==============================================================*/
CREATE TABLE HISTORY_ALARM
(
  ID                   VARCHAR(36) NOT NULL,
  TENANT_ID            VARCHAR(36) COMMENT '租户id',
  PRODUCT_ID           VARCHAR(36) COMMENT '产品Id ',
  DEV_ID               VARCHAR(36) COMMENT '设备id',
  ALARM_ID             VARCHAR(36) COMMENT '告警id',
  ALARM_CONTENT        VARCHAR(36) COMMENT '告警内容',
  START_TIME           DATETIME COMMENT '告警时间',
  CONFIRM_TIME         DATETIME COMMENT '告警恢复时间',
  PRIMARY KEY (ID)
);

ALTER TABLE HISTORY_ALARM COMMENT '历史告警';

/* 配置平台管理员**/
INSERT INTO `tenant` VALUES('ffffffffffffffffffffffffffffffff','system admin','平台超级管理员'); 
INSERT INTO `user` (`id`,`tenant_id`,`user_name`,`account`,`pwd`,`authority`,`create_time`,`type`,`state_flag`) VALUES('cf553381782511e7319067db7fdd09cd','ffffffffffffffffffffffffffffffff','系统管理员','admin','1a9d5953d506d9fc384fe68c0e61e25f','sys_admin','2017-06-30 13:52:29',1,1);
/* 配置默认分类**/
INSERT INTO `category` VALUES ('8a8aeb3b6078585a0160785f197b001b','8a8aebaf5f76a189015f76a64756000a','100207','窗帘',4),('8a8aeb3b6091d45f016091d5b0990002','ff8080815fa3deb2015fb848f92011aa','110102','地锁',4),('8a8aeb8e5dc682db015dc6852e9c0003','8a8aeb955ce30d4c015ce3167a260001','11','智慧城市',2),('8a8aeb955ce30d4c015ce3167a260001','-1','999999','总类',1),('8a8aebaf5f76a189015f76a2ae110002','8a8aeb955ce30d4c015ce3167a260001','10','智能家居',2),('8a8aebaf5f76a189015f76a4ab940004','8a8aebaf5f76a189015f76a2ae110002','1001','空气质量',3),('8a8aebaf5f76a189015f76a55bd80006','8a8aebaf5f76a189015f76a4ab940004','100103','室外温湿度',4),('8a8aebaf5f76a189015f76a582410008','8a8aebaf5f76a189015f76a4ab940004','100102','室外PM2.5',4),('8a8aebaf5f76a189015f76a64756000a','8a8aebaf5f76a189015f76a2ae110002','1002','安防设备',3),('8a8aebaf5f76a189015f76a67009000c','8a8aebaf5f76a189015f76a64756000a','100206','门禁',4),('8a8aebaf5f76a189015f76a6e232000e','8a8aebaf5f76a189015f76a64756000a','100202','摄像头',4),('8a8aebaf5f76a189015f76a70ff40010','ff808081620e8a6101621e1f8e1d3eca','100203','室内烟感器',4),('8b8aebb25f148f90015f14edcdff0001','8a8aeb955ce30d4c015ce3167a260001','99','其它',2),('8b8aebb25f148f90015f14edcdff0023','8b8aebb25f148f90015f14edcdff0001','9901','其它',3),('8b8aebb25f148f90015f14edcdff0033','8b8aebb25f148f90015f14edcdff0023','990101','其它',4),('ff8080815dc50f47015dc5b1500f0008','8b8aebb25f148f90015f14edcdff0023','990102','网关',4),('ff8080815fa3deb2015fb848f92011aa','8a8aeb8e5dc682db015dc6852e9c0003','1101','智慧停车',3),('ff8080815fa3deb2015fb84918f811ac','ff8080815fa3deb2015fb848f92011aa','110101','地磁',4),('ff808081601f579b01601f62bf2e0002','8a8aebaf5f76a189015f76a64756000a','100204','红外探测器',4),('ff808081601f579b01601f64337d0005','8a8aebaf5f76a189015f76a64756000a','100205','智能家居网关',4),('ff80808160b0c0d10160b0d0fd8b0003','8a8aebaf5f76a189015f76a64756000a','100208','开关',4),('ff80808160fd880d01610267bbbc09af','ff8080815fa3deb2015fb848f92011aa','110103','超声波',4),('ff80808161ff92e801620343b42d1028','8a8aebaf5f76a189015f76a64756000a','100209','光照',4),('ff8080816204814b01620891c49a0c49','8a8aebaf5f76a189015f76a64756000a','100210','情景面板',4),('ff8080816204814b0162089e15bd0c77','8a8aebaf5f76a189015f76a64756000a','100211','水浸',4),('ff808081620e8a6101620ecc4a35316c','8a8aebaf5f76a189015f76a64756000a','100212','空调',4),('ff808081620e8a6101621d580e4636c1','8a8aeb8e5dc682db015dc6852e9c0003','1102','城市交通',3),('ff808081620e8a6101621d58298636d1','ff808081620e8a6101621d580e4636c1','110201','井盖',4),('ff808081620e8a6101621e1872353a41','8a8aebaf5f76a189015f76a64756000a','100215','排风扇',4),('ff808081620e8a6101621e1f8e1d3eca','8a8aebaf5f76a189015f76a2ae110002','1003','安防设备2',3),('ff808081620e8a6101621e21ccad406c','ff808081620e8a6101621e1f8e1d3eca','100301','红外遥控器',4),('ff808081620e8a6101621e21e54d407e','ff808081620e8a6101621e1f8e1d3eca','100302','空气开关',4),('ff808081620e8a6101622c90d1b64fde','ff808081620e8a6101621e1f8e1d3eca','100201','室外烟感器',4),('ff808081623844f501623bde0a7a7d27','8a8aebaf5f76a189015f76a4ab940004','100101','室内温湿度',4),('ff808081624839b201625098281a5648','8a8aebaf5f76a189015f76a64756000a','100216','插头',4),('ff808081624839b20162509860c756aa','8a8aebaf5f76a189015f76a64756000a','100217','电子秤',4);
/* 配置数据入库的规则与插件**/
INSERT INTO `plugin` VALUES ('8a8aeb955ce352d1015ce35e26870005','ffffffffffffffffffffffffffffffff','telemetry_storage_plugin','com.comba.server.extensions.core.plugin.telemetry.TelemetryStoragePlugin','{}','','','2017-07-13 21:07:21','2017-07-13 21:07:21');
INSERT INTO `plugin` VALUES ('8a8aeb955ce3b7ec015ce3ba50290001','ffffffffffffffffffffffffffffffff','sms_send_plugin','com.comba.server.extensions.core.plugin.sms.SmsPlugin','{}','','','2017-07-13 21:07:21','2017-07-13 21:07:21');

INSERT INTO `rule` (`id`,`tenant_id`,`name`,`plugin_id`,`filters`,`processor`,`action`,`additionalinfo`,`create_time`,`update_time`) VALUES ('8a8aeb585d34777d015d3489fa110008','ffffffffffffffffffffffffffffffff','telmetry_storage_rule','8a8aeb955ce352d1015ce35e26870005','[{"clazz": "com.comba.server.extensions.core.filter.MsgTypeFilter","name": "TelemetryFilter","configuration": {"messageTypes": ["POST_TELEMETRY","POST_ATTRIBUTES","GET_ATTRIBUTES"]}}]','true','{"configuration":{},"name":"TelemetryPluginAction","clazz":"com.comba.server.extensions.core.action.telemetry.TelemetryPluginAction"}','{"description":"xxxx"}','2017-07-13 21:07:21','2017-07-13 21:07:21');

/* 配置一条网关设备与通用设备的参数模板**/
INSERT INTO `device_template` VALUES ('8a8aeb3b606e236801606e2763340003','8a8aebaf5f76a189015f76a582410008','室内PM25',NULL,'2018-03-13 10:52:04','气体浓度检测'),('8a8aeb3b6078585a0160785f84d1001d','8a8aeb3b6078585a0160785f197b001b','窗帘模板',NULL,'2018-03-26 18:42:56',''),('8a8aeb3b607ba95e01607bb08549001d','ff808081601f579b01601f64337d0005','飞比网关模板',NULL,'2018-03-20 14:53:39',''),('8a8aeb3b6091d45f016091d61f2f0004','8a8aeb3b6091d45f016091d5b0990002','泊享地锁',NULL,'2018-01-17 13:53:53',''),('8a8aeb8e5dc5d396015dc5dd5f8c0002','ff8080815dc50f47015dc5b1500f0008','网关参数模板',NULL,'2018-03-09 17:43:22',''),('8a8aeb9b60452cc5016045309cdd0003','ff8080815fa3deb2015fb84918f811ac','探测器模板','2017-12-11 18:48:22','2017-12-11 18:48:22','探测器'),('8a8aeb9d5ffc2cb5015ffc3359a80005','8a8aebaf5f76a189015f76a67009000c','门禁参数模板',NULL,'2018-03-08 09:13:26',''),('ff8080815e5675f4015e567897f30002','8b8aebb25f148f90015f14edcdff0033','通用模板','2017-09-06 17:14:57','2017-09-06 17:14:57',''),('ff8080815ea88f17015ea8aaa85c0037','ff8080815dc50f47015dc5b1500f0008','LORA网关',NULL,'2018-03-09 17:12:26',''),('ff8080815fa3deb2015fb84496941187','ff808081623844f501623bde0a7a7d27','室内温湿度模板',NULL,'2018-03-19 09:27:44','室内室外共用模板'),('ff8080816011462401601156edfd0016','ff808081620e8a6101622c90d1b64fde','室外烟感',NULL,'2018-03-16 10:11:33','LORA'),('ff808081602abd430160357a86780019','8a8aebaf5f76a189015f76a70ff40010','室内烟感',NULL,'2018-03-16 14:19:23','ZIGBEE'),('ff80808160b09fce0160b0b560ba0004','ff80808160b0c0d10160b0d0fd8b0003','开关模板',NULL,'2018-03-07 15:43:26',''),('ff80808161ff92e80162032834220f45','ff808081601f579b01601f62bf2e0002','人体红外模板',NULL,'2018-03-19 09:45:08',''),('ff80808161ff92e801620341235d101b','ff80808161ff92e801620343b42d1028','飞比光照参数模板',NULL,'2018-03-08 09:40:17',''),('ff80808161ff92e801620346ec541048','ff8080816204814b0162089e15bd0c77','水浸参数模板',NULL,'2018-03-13 10:40:01',''),('ff8080816204814b0162086712500b43','ff80808160fd880d01610267bbbc09af','超声波模板','2018-03-09 09:36:35','2018-03-09 09:36:35',''),('ff8080816204814b016208917ce20c40','ff8080816204814b01620891c49a0c49','情景面板参数模板',NULL,'2018-03-09 10:23:22',''),('ff8080816204814b016208c813230cf3','ff8080815fa3deb2015fb84918f811ac','唯传地磁','2018-03-09 11:22:33','2018-03-09 11:22:33',''),('ff808081620e8a6101620ecc9af431a5','ff808081620e8a6101620ecc4a35316c','空调模板','2018-03-10 15:25:13','2018-03-10 15:25:13',''),('ff808081620e8a6101621d46b0302a88','8a8aebaf5f76a189015f76a582410008','室外PM25','2018-03-13 10:53:15','2018-03-13 10:53:15','室外'),('ff808081620e8a6101621d5a50d43863','ff808081620e8a6101621d58298636d1','井盖参数模板',NULL,'2018-03-13 11:15:47',''),('ff808081620e8a6101621e18f2633a85','ff808081620e8a6101621e1872353a41','排风扇','2018-03-13 14:42:54','2018-03-13 14:42:54',''),('ff808081620e8a6101621e221a66408b','ff808081620e8a6101621e21e54d407e','空气开关',NULL,'2018-03-13 14:56:36',''),('ff808081620e8a6101621e25b04542dd','ff808081620e8a6101621e21ccad406c','红外遥控器',NULL,'2018-03-13 14:57:54',''),('ff808081623844f501623bded0597d4c','8a8aebaf5f76a189015f76a55bd80006','室外温湿度','2018-03-19 09:28:01','2018-03-19 09:28:01',''),('ff808081624839b201624b60bb637218','8a8aebaf5f76a189015f76a6e232000e','摄像头模板','2018-03-22 09:44:13','2018-03-22 09:44:13',''),('ff808081624839b201625098b2b0571f','ff808081624839b20162509860c756aa','电子秤','2018-03-23 10:03:27','2018-03-23 10:03:27',''),('ff808081624839b201625098fbcd5728','ff808081624839b201625098281a5648','插头','2018-03-23 10:03:46','2018-03-23 10:03:46','');
INSERT INTO `attributes_template` VALUES ('8a8aeb8e5dc5fe0d015dc60579fe0002','8a8aeb8e5dc5d396015dc5dd5f8c0002','internet.type','string',0,1,NULL,'',NULL,'输入“4g”,\"wifi\", \"eth\"三个的一种',NULL,'2017-09-04 14:50:43'),('8a8aeb8e5dc5fe0d015dc60615b10004','8a8aeb8e5dc5d396015dc5dd5f8c0002','internet.ip','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 14:54:53'),('8a8aeb8e5dc5fe0d015dc606b8dc0006','8a8aeb8e5dc5d396015dc5dd5f8c0002','internet.mask','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 15:59:24'),('8a8aeb8e5dc5fe0d015dc60727620008','8a8aeb8e5dc5d396015dc5dd5f8c0002','internet.gw','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 15:59:32'),('8a8aeb8e5dc5fe0d015dc607e67d000a','8a8aeb8e5dc5d396015dc5dd5f8c0002','internet.dns1','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 15:59:41'),('8a8aeb8e5dc5fe0d015dc609d3cd000c','8a8aeb8e5dc5d396015dc5dd5f8c0002','internet.dns2','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 15:59:46'),('8a8aeb8e5dc5fe0d015dc60a2c42000e','8a8aeb8e5dc5d396015dc5dd5f8c0002','wifi.enable','string',0,1,NULL,'',NULL,'\"on\",\"off\"',NULL,'2017-09-04 15:14:39'),('8a8aeb8e5dc5fe0d015dc60a7b6a0010','8a8aeb8e5dc5d396015dc5dd5f8c0002','wifi.txpower','string',0,0,NULL,'dBm',NULL,'',NULL,'2017-09-04 15:15:54'),('8a8aeb8e5dc5fe0d015dc60ad3150012','8a8aeb8e5dc5d396015dc5dd5f8c0002','wifi.ssid','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 15:59:52'),('8a8aeb8e5dc5fe0d015dc60d3bc50014','8a8aeb8e5dc5d396015dc5dd5f8c0002','wifi.mode','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 15:59:57'),('8a8aeb8e5dc5fe0d015dc60da45d0016','8a8aeb8e5dc5d396015dc5dd5f8c0002','wifi.pwd','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 16:00:05'),('8a8aeb8e5dc5fe0d015dc60df24e0018','8a8aeb8e5dc5d396015dc5dd5f8c0002','4g.status','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 16:00:09'),('8a8aeb8e5dc5fe0d015dc60e4473001a','8a8aeb8e5dc5d396015dc5dd5f8c0002','4g.mode','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 16:00:15'),('8a8aeb8e5dc5fe0d015dc60ec182001c','8a8aeb8e5dc5d396015dc5dd5f8c0002','4g.careioperater','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 16:00:20'),('8a8aeb8e5dc5fe0d015dc612aeee002a','8a8aeb8e5dc5d396015dc5dd5f8c0002','lora.enable','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 15:19:19'),('8a8aeb8e5dc5fe0d015dc61383fb002e','8a8aeb8e5dc5d396015dc5dd5f8c0002','zigbee.channel','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 16:00:27'),('8a8aeb8e5dc5fe0d015dc613be6f0030','8a8aeb8e5dc5d396015dc5dd5f8c0002','zigbee.txpower','string',0,1,NULL,'dBm',NULL,'输入1－4，1：“-32.5dBm”，2：“-20.5dBm”，3：“-9.0dBm”，4：“2.5dBm”',NULL,'2017-09-04 15:43:40'),('8a8aeb8e5dc5fe0d015dc613fa180032','8a8aeb8e5dc5d396015dc5dd5f8c0002','zigbee.panid','string',0,1,NULL,'',NULL,'范围：0~65535',NULL,'2017-09-04 15:27:37'),('8a8aeb8e5dc5fe0d015dc6142ee40034','8a8aeb8e5dc5d396015dc5dd5f8c0002','zigbee.netid','string',0,1,NULL,'',NULL,'范围：0~65535',NULL,'2017-09-04 15:27:52'),('8a8aeb8e5dc5fe0d015dc61483aa0036','8a8aeb8e5dc5d396015dc5dd5f8c0002','zigbee.mode','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 16:00:39'),('8a8aeb8e5dc5fe0d015dc614c1640038','8a8aeb8e5dc5d396015dc5dd5f8c0002','zigbee.txmode','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 16:00:43'),('8a8aeb8e5dc5fe0d015dc614fd36003a','8a8aeb8e5dc5d396015dc5dd5f8c0002','dhcp.enable','string',0,1,NULL,'','on','','2017-08-09 16:20:51','2017-08-09 16:20:51'),('8a8aeb8e5dc5fe0d015dc6154447003c','8a8aeb8e5dc5d396015dc5dd5f8c0002','dhcp.startip','string',0,1,NULL,'','10.10.1.1','','2017-08-09 16:21:09','2017-08-09 16:21:09'),('8a8aeb8e5dc5fe0d015dc61590d1003e','8a8aeb8e5dc5d396015dc5dd5f8c0002','dhcp.endip','string',0,1,NULL,'','10.10.1.253','','2017-08-09 16:21:28','2017-08-09 16:21:28'),('8a8aeb8e5dc5fe0d015dc615d6620040','8a8aeb8e5dc5d396015dc5dd5f8c0002','dhcp.mask','string',0,1,NULL,'','255.255.255.0','','2017-08-09 16:21:46','2017-08-09 16:21:46'),('8a8aeb8e5dc5fe0d015dc6161cb80042','8a8aeb8e5dc5d396015dc5dd5f8c0002','dhcp.lease','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 15:28:19'),('8a8aeb9262519913016251a28e0c0005','ff80808160b09fce0160b0b560ba0004','switch','int',1,NULL,NULL,'',NULL,'','2018-03-23 14:53:50','2018-03-23 14:53:50'),('ff8080815e4b7774015e4bab319b000a','8a8aeb8e5dc5d396015dc5dd5f8c0002','internet.mode','string',0,1,NULL,'',NULL,'\"pppoe\",\"dhcp\", \"static\"','2017-09-04 14:54:24','2017-09-04 14:54:24'),('ff8080815e4b7774015e4bac297e000d','8a8aeb8e5dc5d396015dc5dd5f8c0002','mgteth.ip','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 14:57:06'),('ff8080815e4b7774015e4bac62e2000f','8a8aeb8e5dc5d396015dc5dd5f8c0002','mgteth.mask','string',0,1,NULL,'',NULL,'',NULL,'2017-09-04 14:56:56'),('ff8080815e4b7774015e4bc116db0021','8a8aeb8e5dc5d396015dc5dd5f8c0002','lora.channel','string',0,1,NULL,'',NULL,'','2017-09-04 15:18:19','2017-09-04 15:18:19'),('ff8080815e4b7774015e4bcad62c002d','8a8aeb8e5dc5d396015dc5dd5f8c0002','dhcp.gw','string',0,1,NULL,'',NULL,'','2017-09-04 15:28:58','2017-09-04 15:28:58'),('ff8080815e4b7774015e4bcb0207002f','8a8aeb8e5dc5d396015dc5dd5f8c0002','dhcp.dns1','string',0,1,NULL,'',NULL,'','2017-09-04 15:29:09','2017-09-04 15:29:09'),('ff8080815e4b7774015e4bcb26a60031','8a8aeb8e5dc5d396015dc5dd5f8c0002','dhcp.dns2','string',0,1,NULL,'',NULL,'','2017-09-04 15:29:18','2017-09-04 15:29:18'),('ff8080815e4b7774015e4bcb87140033','8a8aeb8e5dc5d396015dc5dd5f8c0002','ntp.enable','string',0,1,NULL,'',NULL,'','2017-09-04 15:29:43','2017-09-04 15:29:43'),('ff8080815e4b7774015e4bcbaebd0035','8a8aeb8e5dc5d396015dc5dd5f8c0002','ntp.serverip','string',0,1,NULL,'',NULL,'','2017-09-04 15:29:53','2017-09-04 15:29:53'),('ff8080815e4b7774015e4bccc7c10037','8a8aeb8e5dc5d396015dc5dd5f8c0002','info.mac','string',0,0,NULL,'',NULL,'',NULL,'2017-09-04 15:38:13'),('ff8080815e4b7774015e4bcd96da0039','8a8aeb8e5dc5d396015dc5dd5f8c0002','info.version','string',0,0,NULL,'',NULL,'',NULL,'2017-09-04 15:38:19'),('ff8080815e4b7774015e4bcdf47b003b','8a8aeb8e5dc5d396015dc5dd5f8c0002','info.corever','string',0,0,NULL,'',NULL,'',NULL,'2017-09-04 15:38:43'),('ff8080815e4b7774015e4bce86b6003d','8a8aeb8e5dc5d396015dc5dd5f8c0002','info.location','string',0,0,NULL,'',NULL,'',NULL,'2017-09-04 15:38:01'),('ff8080815e4b7774015e4bceebdb003f','8a8aeb8e5dc5d396015dc5dd5f8c0002','info.cpuload','string',0,0,NULL,'',NULL,'',NULL,'2017-09-04 15:37:52'),('ff8080815e4b7774015e4bd1435d0041','8a8aeb8e5dc5d396015dc5dd5f8c0002','info.memory.total','string',0,0,NULL,'KB',NULL,'',NULL,'2017-09-04 15:37:44'),('ff8080815e4b7774015e4bd214a90043','8a8aeb8e5dc5d396015dc5dd5f8c0002','info.runtime','string',0,0,NULL,'',NULL,'','2017-09-04 15:36:53','2017-09-04 15:36:53'),('ff8080815e4b7774015e4bd477a4004c','8a8aeb8e5dc5d396015dc5dd5f8c0002','flow.rx','string',0,0,NULL,'',NULL,'','2017-09-04 15:39:29','2017-09-04 15:39:29'),('ff8080815e4b7774015e4bd4c3e4004e','8a8aeb8e5dc5d396015dc5dd5f8c0002','flow.packetrx','string',0,1,NULL,'',NULL,'','2017-09-04 15:39:49','2017-09-04 15:39:49'),('ff8080815e4b7774015e4bd4eac50050','8a8aeb8e5dc5d396015dc5dd5f8c0002','flow.packettx','string',0,1,NULL,'',NULL,'','2017-09-04 15:39:59','2017-09-04 15:39:59'),('ff8080815e4b7774015e4bd50af00052','8a8aeb8e5dc5d396015dc5dd5f8c0002','flow.tx','string',0,1,NULL,'',NULL,'','2017-09-04 15:40:07','2017-09-04 15:40:07'),('ff8080815e4caac2015e4cd0b09f003c','8a8aeb8e5dc5d396015dc5dd5f8c0002','info.memory.used','string',0,0,NULL,'',NULL,'','2017-09-04 20:14:59','2017-09-04 20:14:59'),('ff8080815e4caac2015e4cd4047f0074','8a8aeb8e5dc5d396015dc5dd5f8c0002','wifi.channel','string',0,0,NULL,'',NULL,'','2017-09-04 20:18:37','2017-09-04 20:18:37'),('ff80808161732eb7016173306c180002','8a8aeb8e5dc5d396015dc5dd5f8c0002','lora.txpower','int',0,1,NULL,'dBm',NULL,'功率0:auto,14-25dBm','2018-02-08 10:13:29','2018-02-08 10:13:29'),('ff80808161732eb701617330e5550004','8a8aeb8e5dc5d396015dc5dd5f8c0002','lora.serverip','string',0,1,NULL,'',NULL,'loraserver服务器IP地址','2018-02-08 10:14:00','2018-02-08 10:14:00'),('ff80808161732eb70161733131050006','8a8aeb8e5dc5d396015dc5dd5f8c0002','lora.gatewayid','string',0,1,NULL,'',NULL,'18个字符串的gwid','2018-02-08 10:14:19','2018-02-08 10:14:19'),('ff80808161732eb7016173318f6e0008','8a8aeb8e5dc5d396015dc5dd5f8c0002','lora.portup','int',0,1,NULL,'',NULL,'loraserver服务器上行端口','2018-02-08 10:14:43','2018-02-08 10:14:43'),('ff80808161732eb701617331da23000a','8a8aeb8e5dc5d396015dc5dd5f8c0002','lora.portdown','int',0,1,NULL,'',NULL,'loraserver服务器下行端口','2018-02-08 10:15:02','2018-02-08 10:15:02'),('ff80808161732eb7016173326331000c','8a8aeb8e5dc5d396015dc5dd5f8c0002','zigbee.power','string',0,1,NULL,'',NULL,'开关，只有”on”与”off”两个参数','2018-02-08 10:15:38','2018-02-08 10:15:38'),('ff80808161732eb701617332baa0000e','8a8aeb8e5dc5d396015dc5dd5f8c0002','log.level','string',0,1,NULL,'',NULL,'debug,info,warn, error','2018-02-08 10:16:00','2018-02-08 10:16:00'),('ff80808161ff92e80161ffc6eda8010f','8a8aeb9d5ffc2cb5015ffc3359a80005','voltageState','int',1,NULL,NULL,'',NULL,'1-电池电压低 0-电池电压正常',NULL,'2018-03-07 17:24:57'),('ff80808161ff92e80161ffc774f30112','8a8aeb9d5ffc2cb5015ffc3359a80005','voltage','float',1,NULL,NULL,'',NULL,'电池当前电压值,电压值单位为100mv， 32即32*100mv = 3.2v','2018-03-07 17:25:17','2018-03-07 17:25:17'),('ff80808161ff92e80161ffc7c8930114','8a8aeb9d5ffc2cb5015ffc3359a80005','undervoltage','int',1,NULL,NULL,'',NULL,'电压欠压报警信息,0-电压正常1-电池欠压','2018-03-07 17:25:39','2018-03-07 17:25:39'),('ff80808161ff92e801620328ccac0f47','ff80808161ff92e80162032834220f45','infrared','int',1,NULL,NULL,'',NULL,'1:有人，0:无人','2018-03-08 09:10:28','2018-03-08 09:10:28'),('ff80808161ff92e80162032917e40f49','ff80808161ff92e80162032834220f45','voltageState','int',1,NULL,NULL,'',NULL,'1-电池电压低 0-电池电压正常','2018-03-08 09:10:48','2018-03-08 09:10:48'),('ff80808161ff92e80162032c91b50f6d','8a8aeb9d5ffc2cb5015ffc3359a80005','switch','int',1,NULL,NULL,'',NULL,'1:开门，0:关门','2018-03-08 09:14:35','2018-03-08 09:14:35'),('ff80808161ff92e80162032f165f0f8f','ff80808161ff92e80162032e64970f8d','switch','int',0,1,NULL,'',NULL,'1-开,0-关,2-停','2018-03-08 09:17:20','2018-03-08 09:17:20'),('ff80808161ff92e80162033a74d20fdb','8a8aeb3b60a1875a0160a1928741000c','irId','int',0,1,NULL,'',NULL,'1电源键，2菜单键，3上，4下，5左，6右，7ENTER(投影仪遥控)','2018-03-08 09:29:45','2018-03-08 09:29:45'),('ff80808161ff92e80162033d68be0ff8','ff8080816011462401601156edfd0016','alarmStatus','string',1,NULL,NULL,'',NULL,'true/false是否告警',NULL,'2018-03-16 10:11:14'),('ff80808161ff92e80162033daff80ffa','ff8080816011462401601156edfd0016','voltage','float',1,NULL,NULL,'',NULL,'电压',NULL,'2018-03-13 11:23:37'),('ff80808161ff92e80162033f2c3e1000','ff8080815fa3deb2015fb84496941187','temperature','float',1,NULL,NULL,'',NULL,'温度',NULL,'2018-03-08 09:35:20'),('ff80808161ff92e80162033f6b0f1002','ff8080815fa3deb2015fb84496941187','humidity','float',1,NULL,NULL,'',NULL,'湿度','2018-03-08 09:35:11','2018-03-08 09:35:11'),('ff80808161ff92e80162034176b1101d','ff80808161ff92e801620341235d101b','illumination','float',1,NULL,NULL,'',NULL,'光照度的测量值,单位是： Lux','2018-03-08 09:37:25','2018-03-08 09:37:25'),('ff80808161ff92e801620341b717101f','ff80808161ff92e801620341235d101b','voltage','float',1,NULL,NULL,'',NULL,'电池当前电压值,电压值单位为100mv， 32即32*100mv = 3.2v','2018-03-08 09:37:41','2018-03-08 09:37:41'),('ff80808161ff92e801620341f68c1021','ff80808161ff92e801620341235d101b','electric','float',1,NULL,NULL,'',NULL,'电池电量百分比','2018-03-08 09:37:57','2018-03-08 09:37:57'),('ff80808161ff92e801620342360c1023','ff80808161ff92e801620341235d101b','undervoltage','int',1,NULL,NULL,'',NULL,'电压欠压报警信息,0-电压正常1-电池欠压','2018-03-08 09:38:14','2018-03-08 09:38:14'),('ff8080816204814b01620867b7870b4f','ff8080816204814b0162086712500b43','isAvailable','string',1,NULL,NULL,'',NULL,'true-无车，false-有车','2018-03-09 09:37:18','2018-03-09 09:37:18'),('ff8080816204814b0162089f5eae0c81','ff80808161ff92e801620346ec541048','waterState','int',1,NULL,NULL,'',NULL,'1:有水，0:无水','2018-03-09 10:38:05','2018-03-09 10:38:05'),('ff8080816204814b0162089fa9dd0c8a','ff80808161ff92e801620346ec541048','voltageState','int',1,NULL,NULL,'',NULL,'1-电池电压低 0-电池电压正常','2018-03-09 10:38:24','2018-03-09 10:38:24'),('ff8080816204814b016208c5d4cb0cec','8a8aeb3b6091d45f016091d61f2f0004','beep','int',1,NULL,NULL,'',NULL,'蜂鸣器设置，01-打开，00-关闭','2018-03-09 11:20:06','2018-03-09 11:20:06'),('ff8080816204814b016208c61a4f0cee','8a8aeb3b6091d45f016091d61f2f0004','bat','int',1,NULL,NULL,'',NULL,'电池电量，03-满格，02-中间，01-低电 00-没电','2018-03-09 11:20:23','2018-03-09 11:20:23'),('ff8080816204814b016208c659a80cf0','8a8aeb3b6091d45f016091d61f2f0004','csb','int',1,NULL,NULL,'',NULL,'01-有车，00-无车，02-变化中，03-未知','2018-03-09 11:20:40','2018-03-09 11:20:40'),('ff8080816204814b016208c8a6f50cf9','ff8080816204814b016208c813230cf3','isAvailable','string',1,NULL,NULL,'',NULL,'true-无车，false-有车','2018-03-09 11:23:10','2018-03-09 11:23:10'),('ff8080816204814b016208c904100d02','ff8080816204814b016208c813230cf3','voltage','float',1,NULL,NULL,'',NULL,'电压','2018-03-09 11:23:34','2018-03-09 11:23:34'),('ff8080816204814b016208c945060d04','ff8080816204814b016208c813230cf3','temperature','float',1,NULL,NULL,'',NULL,'温度','2018-03-09 11:23:51','2018-03-09 11:23:51'),('ff808081620e8a6101621d13fb8c09d9','ff808081620e8a6101621d0d64550603','watt','float',1,NULL,NULL,'',NULL,'功率','2018-03-13 09:57:52','2018-03-13 09:57:52'),('ff808081620e8a6101621d14360e09e4','ff808081620e8a6101621d0d64550603','voltage','float',1,NULL,NULL,'',NULL,'电压','2018-03-13 09:58:07','2018-03-13 09:58:07'),('ff808081620e8a6101621d147d8b0a20','ff808081620e8a6101621d0d64550603','electricity','float',1,NULL,NULL,'',NULL,'电流','2018-03-13 09:58:25','2018-03-13 09:58:25'),('ff808081620e8a6101621d14bf6e0a5c','ff808081620e8a6101621d0d64550603','temperature','float',1,NULL,NULL,'',NULL,'温度','2018-03-13 09:58:42','2018-03-13 09:58:42'),('ff808081620e8a6101621d15096f0a88','ff808081620e8a6101621d0d64550603','power','float',1,NULL,NULL,'',NULL,'电量（累计到当前的总电量）','2018-03-13 09:59:01','2018-03-13 09:59:01'),('ff808081620e8a6101621d15e1780b0c','ff808081620e8a6101621d0d64550603','switch','int',0,1,NULL,'',NULL,'1:开，0:关  表示开关状态','2018-03-13 09:59:56','2018-03-13 09:59:56'),('ff808081620e8a6101621d3469301dbc','ff808081620e8a6101621d3055311aef','irId','int',0,1,NULL,'',NULL,'1电源键，2菜单键，3上，4下，5左，6右，7ENTER(投影仪遥控)','2018-03-13 10:33:17','2018-03-13 10:33:17'),('ff808081620e8a6101621d41a497272d','ff808081602abd430160357a86780019','alarmState','int',1,NULL,NULL,'',NULL,'1-有烟雾 0-无烟雾',NULL,'2018-03-13 11:20:43'),('ff808081620e8a6101621d4735ac2b04','ff808081620e8a6101621d46b0302a88','PM2.5','float',1,NULL,NULL,'',NULL,'气体浓度，单位ug/m3','2018-03-13 10:53:49','2018-03-13 10:53:49'),('ff808081620e8a6101621d47919d2b52','ff808081620e8a6101621d46b0302a88','voltage','float',1,NULL,NULL,'',NULL,'电压值，单位V','2018-03-13 10:54:12','2018-03-13 10:54:12'),('ff808081620e8a6101621d5af46938a1','ff808081620e8a6101621d5a50d43863','openState','int',1,NULL,NULL,'',NULL,'0-井盖未打开，1-井盖己打开','2018-03-13 11:15:23','2018-03-13 11:15:23'),('ff808081620e8a6101621d5b3fdb38d2','ff808081620e8a6101621d5a50d43863','voltage','float',1,NULL,NULL,'',NULL,'电压','2018-03-13 11:15:42','2018-03-13 11:15:42'),('ff808081620e8a6101621d60192a3c4e','ff808081602abd430160357a86780019','voltageState','int',1,NULL,NULL,'',NULL,'1-电池电压低 0-电池电压正常','2018-03-13 11:21:00','2018-03-13 11:21:00'),('ff808081620e8a6101621e23c81441d6','ff808081620e8a6101621e221a66408b','watt','float',1,NULL,NULL,'功率',NULL,'','2018-03-13 14:54:44','2018-03-13 14:54:44'),('ff808081620e8a6101621e2401f741e1','ff808081620e8a6101621e221a66408b','voltage','float',1,NULL,NULL,'',NULL,'电压','2018-03-13 14:54:59','2018-03-13 14:54:59'),('ff808081620e8a6101621e2431d241f8','ff808081620e8a6101621e221a66408b','electricity','float',1,NULL,NULL,'',NULL,'电流','2018-03-13 14:55:11','2018-03-13 14:55:11'),('ff808081620e8a6101621e2462764214','ff808081620e8a6101621e221a66408b','temperature','float',1,NULL,NULL,'',NULL,'温度','2018-03-13 14:55:24','2018-03-13 14:55:24'),('ff808081620e8a6101621e24a9fc4239','ff808081620e8a6101621e221a66408b','power','float',1,NULL,NULL,'',NULL,'电量（累计到当前的总电量）','2018-03-13 14:55:42','2018-03-13 14:55:42'),('ff808081620e8a610162284defc31561','ff8080815fa3deb2015fb84496941187','voltage','float',1,NULL,NULL,'',NULL,'电池当前电压值,电压值单位为100mv， 32即32*100mv = 3.2v','2018-03-15 14:16:59','2018-03-15 14:16:59'),('ff808081623844f501623bdf3ee57d82','ff808081623844f501623bded0597d4c','temperature','float',1,NULL,NULL,'',NULL,'','2018-03-19 09:28:29','2018-03-19 09:28:29'),('ff808081623bdfa301623be0412d000b','ff808081623844f501623bded0597d4c','humidity','float',1,NULL,NULL,'',NULL,'','2018-03-19 09:29:35','2018-03-19 09:29:35'),('ff808081623bdfa301623bee4674087a','ff80808161ff92e80162032834220f45','voltage','float',1,NULL,NULL,'',NULL,'','2018-03-19 09:44:54','2018-03-19 09:44:54'),('ff808081623bdfa301623bf008c909a6','ff80808161ff92e801620346ec541048','voltage','float',1,NULL,NULL,'',NULL,'','2018-03-19 09:46:49','2018-03-19 09:46:49'),('ff808081623bdfa301623bf0443d09c6','ff80808161ff92e801620346ec541048','undervoltage','int',1,NULL,NULL,'',NULL,'','2018-03-19 09:47:05','2018-03-19 09:47:05'),('ff808081623c192e01623da960c20051','ff80808160b09fce0160b0b560ba0004','switch','int',0,1,NULL,'',NULL,'',NULL,'2018-03-27 11:18:53'),('ff8080816241781c01624179fba80006','ff808081620e8a6101621e25b04542dd','irId','int',0,1,NULL,'',NULL,'1电源键，2菜单键，3上，4下，5左，6右，7ENTER(投影仪遥控)','2018-03-20 11:35:36','2018-03-20 11:35:36'),('ff8080816241781c0162417b6b17000b','ff808081620e8a6101621e221a66408b','switch','int',0,1,NULL,'',NULL,'1:开，0:关 表示开关状态','2018-03-20 11:37:10','2018-03-20 11:37:10'),('ff8080816241781c0162417c450b001d','ff808081620e8a6101620ecc9af431a5','temperature','float',0,1,NULL,'',NULL,'温度设置','2018-03-20 11:38:06','2018-03-20 11:38:06'),('ff8080816241781c0162417c8c7c0021','ff808081620e8a6101620ecc9af431a5','model','int',0,1,NULL,'',NULL,'2-制冷，1-制热，0-送风','2018-03-20 11:38:24','2018-03-20 11:38:24'),('ff8080816241781c0162417cc8ea0025','ff808081620e8a6101620ecc9af431a5','switch','int',0,1,NULL,'',NULL,'否	1-开，0-关','2018-03-20 11:38:40','2018-03-20 11:38:40'),('ff8080816241781c0162417d4755002a','ff8080816204814b016208917ce20c40','switch','int',0,1,NULL,'',NULL,'1-开，0-关','2018-03-20 11:39:12','2018-03-20 11:39:12'),('ff8080816241781c0162417efe1b0031','8a8aeb3b6091d45f016091d61f2f0004','lockState','int',0,1,NULL,'',NULL,'否 00-升锁状态，01-降锁状态， 02-中间状态，03-上升遇阻后恢复，88-运动状态','2018-03-20 11:41:04','2018-03-20 11:41:04'),('ff8080816241781c0162417f80d90053','8a8aeb3b6078585a0160785f84d1001d','switch','int',0,1,NULL,'',NULL,'否	1-开，0-关','2018-03-20 11:41:38','2018-03-20 11:41:38'),('ff808081624839b201626113b42c06d9','ff808081602abd430160357a86780019','voltage','float',1,NULL,NULL,'',NULL,'','2018-03-26 14:51:44','2018-03-26 14:51:44'),('ff808081624839b20162611bc3260e13','ff808081602abd430160357a86780019','undervoltage','int',1,NULL,NULL,'',NULL,'','2018-03-26 15:00:32','2018-03-26 15:00:32'),('ff808081624839b20162615469bf3f20','8a8aeb3b6091d45f016091d61f2f0004','lockState','int',1,NULL,NULL,'',NULL,'0-升锁状态，1-降锁状态， 2-中间状态',NULL,'2018-03-26 16:02:48'),('ff808081624839b2016261e743fc3faa','8a8aeb3b6078585a0160785f84d1001d','switch','int',1,NULL,NULL,'',NULL,'','2018-03-26 18:42:49','2018-03-26 18:42:49'),('ff808081624839b201626579831165dc','ff808081620e8a6101621e18f2633a85','switch','int',0,1,NULL,'',NULL,'0-关 1-开','2018-03-27 11:21:25','2018-03-27 11:21:25'),('ff808081624839b201626579c0d265e1','ff808081620e8a6101621e18f2633a85','switch','int',1,NULL,NULL,'',NULL,'','2018-03-27 11:21:41','2018-03-27 11:21:41');
/* 初始化告警类型内容*/
INSERT INTO alarm_content (ID, ALARM_ID, ALARM_NAME, ALARM_CONTENT, ALARM_LEVEL, CREATE_TIME) VALUES ('1001', '1001', '电压告警', '电压过低报警', 0, '2018-02-26 16:54:01');
INSERT INTO alarm_content (ID, ALARM_ID, ALARM_NAME, ALARM_CONTENT, ALARM_LEVEL, CREATE_TIME) VALUES ('1002', '1002', '电量告警', '电量过低报警', 0, '2018-02-26 16:54:01');

/*创建索引*/
alter TABLE history_data add INDEX devId_updateTime(DEV_ID,UPDATE_TIME);
alter TABLE device add index productId(PRODUCT_ID);