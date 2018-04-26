CREATE TABLE PROVINCE
(
   PROVINCE_ID          BIGINT NOT NULL AUTO_INCREMENT,
   PROVINCE_CODE        VARCHAR(20) COMMENT 'ʡ�ݱ���',
   PROVINCE_NAME        VARCHAR(20) COMMENT 'ʡ������',
   PRIMARY KEY (PROVINCE_ID)
)

CREATE TABLE IF NOT EXISTS thingsboard.user (
	id timeuuid,
	tenant_id timeuuid,
	customer_id timeuuid,
	email text,
	search_text text,
	authority text,
	first_name text,
	last_name text,
	additional_info text,
	PRIMARY KEY (id, tenant_id, customer_id, authority)
);

drop table thingsboard.province;
CREATE TABLE IF NOT EXISTS thingsboard.province(
	id timeuuid,
	province_code text,
	province_name text,
	search_text text,
	primary key(id)
);

CREATE MATERIALIZED VIEW IF NOT EXISTS thingsboard.province_by_id_and_name AS
	SELECT *
	from thingsboard.province
	WHERE id IS NOT NULL AND province_name IS NOT NULL
	PRIMARY KEY ( id, province_name)
	WITH CLUSTERING ORDER BY ( id ASC, province_name DESC);
	
CREATE MATERIALIZED VIEW IF NOT EXISTS thingsboard.province_by__name AS
	SELECT *
	from thingsboard.province
	WHERE id IS NOT NULL AND province_name IS NOT NULL
	PRIMARY KEY ( province_name,id)
	WITH CLUSTERING ORDER BY (province_name DESC);
	
CREATE MATERIALIZED VIEW IF NOT EXISTS thingsboard.province_by_name_search_text AS
	SELECT *
	from thingsboard.province
	WHERE id IS NOT NULL AND province_name IS NOT NULL
	PRIMARY KEY ( province_name,id,search_text)
	WITH CLUSTERING ORDER BY (province_name ASC,search_text DESC);	
	
SELECT * FROM province_by__name WHERE province_name='test' AND search_text='test' AND id<1be245a0-50a1-11e7-b184-9f556f5d9f84 LIMIT 2;

SELECT * FROM province_by__name WHERE province_name='test' AND id<1be245a0-50a1-11e7-b184-9f556f5d9f84 LIMIT 2;


drop table thingsboard.c_tenant;
CREATE TABLE IF NOT EXISTS thingsboard.c_tenant(
	id timeuuid,
	name text,
	region text,
	additional_info text,
	primary key(id)
);

CREATE MATERIALIZED VIEW IF NOT EXISTS thingsboard.ctenant_by_region AS
	SELECT *
	from thingsboard.c_tenant
	WHERE region IS NOT NULL AND id IS NOT NULL
	PRIMARY KEY ( region,id)
	WITH CLUSTERING ORDER BY (region ASC,id DESC);