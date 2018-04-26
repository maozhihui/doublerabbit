CREATE DATABASE IF NOT EXISTS mqtt;
USE mqtt;

CREATE TABLE category (
`id` INT AUTO_INCREMENT,
`parent_id` INT,
`code` VARCHAR(32),
`name` VARCHAR(32),
`level_flag` TINYINT,
PRIMARY KEY(id)
)

CREATE TABLE device (
id INT AUTO_INCREMENT,
dev_eui VARCHAR(32) NOT NULL,
dev_name VARCHAR(32),
dev_type TINYINT,
location VARCHAR(32),
dev_name_en VARCHAR(32),
location_en VARCHAR(32),
PRIMARY KEY(id)
);

CREATE TABLE device_data(
id INT AUTO_INCREMENT,
dev_id INT,
attr_name VARCHAR(32) NOT NULL,
attr_value VARCHAR(32),
update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id)
);

CREATE TABLE latest_data(
id INT AUTO_INCREMENT,
dev_id INT,
attr_name VARCHAR(32) NOT NULL,
attr_value VARCHAR(32),
alarm_level VARCHAR(32) DEFAULT "normal",
alarm_cause VARCHAR(32),
update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
alarm_level_en VARCHAR(32) DEFAULT "normal",
alarm_cause_en VARCHAR(32),
PRIMARY KEY(id)
);

ALTER TABLE device_data ADD CONSTRAINT device_id FOREIGN KEY (`dev_id`) REFERENCES device(`id`);
ALTER TABLE latest_data ADD CONSTRAINT lateest_device_id FOREIGN KEY (`dev_id`) REFERENCES device(`id`);

insert  into `device`(`id`,`devEUI`,`devName`,`devType`) values (1,'ffffff10000000b8','温湿度_E5_01',4),(3,'ffffff1000000131','温湿度_E4_01',4),(5,'ffffff1000000132','温湿度_A1_01',4),(7,'ffffff1000000133','温湿度_A2_01',4),(8,'ffffff100000013b','温湿度_A5_01',4),(9,'ffffff100000013c','温湿度_A2_02',4),(10,'ffffff100000013d','温湿度_A3_01',4),(11,'ffffff100000009e','温湿度_A3_02',4),(12,'ffffff100000009f','温湿度_A4_01',4),(13,'ffffff1000000094','温湿度_A5_02',4),(14,'ffffff1000000095','温湿度_A4_02',4),(15,'ffffff100000009b','温湿度_A1_02',4),(16,'ffffff10000000a0','温湿度_A5_03',4),(17,'ffffff000002ba3a','温湿度_A5_04',4),(18,'ffffff10000001a9','地磁_E5_01',12),(19,'ffffff10000001a2','地磁_E5_02',12),(20,'ffffff000002bba9','烟感_E3_01',19),(21,'ffffff000002bb97','烟感_E2_01',19),(22,'ffffff000002bbb5','烟感_E4_01',19),(23,'ffffff000002bbb1','烟感_A1_01',19),(24,'ffffff000002bbae','烟感_A2_01',19),(25,'ffffff000002bba7','烟感_A4_01',19),(26,'ffffff000002bb9a','烟感_A4_02',19),(27,'ffffff000002bb9b','烟感_A3_01',19);