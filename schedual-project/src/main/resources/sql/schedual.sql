-- 创建任务调度数据库
create database IF NOT EXISTS schedual;

-- 切换到调度数据库
use schedual;

-- 删除周期任务表
drop table IF EXISTS period_task;

-- 周期任务表
CREATE TABLE period_task(
	id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '任务编号',
	project_name VARCHAR(100) NOT NULL COMMENT '项目名称',
	task_name VARCHAR(100) NOT NULL COMMENT '任务名称',
	group_name VARCHAR(100) NOT NULL COMMENT '组别名',
	url VARCHAR(255) NOT NULL COMMENT '任务调用地址',
	cron VARCHAR(20) NOT NULL COMMENT 'cron表达式',
	start_time DATETIME COMMENT '开始执行时间',
	task_status TINYINT(1) DEFAULT -1 COMMENT '任务状态',
	description varchar(200) COMMENT '描叙',
	src_ip int(10) 	COMMENT '原IP地址',
	dest_ip int(10) COMMENT '目的IP地址'
	create_time DATETIME NOT NULL COMMENT '任务创建时间',
	create_user VARCHAR(50) NOT NULL COMMENT '任务创建人',
	update_time DATETIME NOT NULL COMMENT '更新时间',
	update_user VARCHAR(50) NOT NULL COMMENT '更新人',
	UNIQUE (project_name,task_name,group_name)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '周期任务';

-- 删除任务表
drop table IF EXISTS task_report;

-- 任务报表
CREATE TABLE task_report (
	id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	task_id BIGINT(20) NOT NULL COMMENT '任务编号',
	ip INT(10)  COMMENT '任务执行服务IP地址',
	start_time DATETIME NOT NULL COMMENT '开始执行时间',
	end_time DATETIME NOT NULL COMMENT '执行结束时间'
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '任务报表';