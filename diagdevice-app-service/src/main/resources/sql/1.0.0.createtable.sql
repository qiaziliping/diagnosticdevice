-- 后台管理系统相关表
CREATE TABLE `sys_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父ID',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `resource_name` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `resource_type` int(1) DEFAULT NULL COMMENT '资源类型 1预留 2：一级菜单 3：二级菜单 4：三级菜单 5：权限',
  `resource_url` varchar(100) DEFAULT NULL COMMENT '资源URL',
  `resource_code` varchar(100) DEFAULT NULL COMMENT '资源编码 format as function:permission',
  `module` varchar(200) DEFAULT NULL COMMENT '页面路由',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标路径',
  `remark` varchar(200) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统资源';

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_code` varchar(100) DEFAULT NULL COMMENT 'role code <=> role identifier -- used by shiro tool',
  `role_type` varchar(20) DEFAULT NULL COMMENT '角色类型:admin管理员自己建立',
  `is_valid` int(1) DEFAULT NULL COMMENT '是否激活 0: no 1:yes',
  `remark` varchar(200) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'SUPER_ADMINISTRATOR', null, '1', '超级管理员', '0', null, '2018-12-04 20:54:25', null, null);


CREATE TABLE `sys_role_authority` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(10) NOT NULL COMMENT '角色ID',
  `resource_id` bigint(10) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`),
  KEY `ra_fk_role_id` (`role_id`),
  KEY `ra_fk_resource_id` (`resource_id`),
  CONSTRAINT `ra_fk_resource_id` FOREIGN KEY (`resource_id`) REFERENCES `sys_authority` (`id`),
  CONSTRAINT `ra_fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色资源';

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` int(1) DEFAULT NULL COMMENT '性别：0女，1男',
  `is_active` int(1) DEFAULT NULL COMMENT '是否激活：0否，1是',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `image_path_url` varchar(300) DEFAULT NULL COMMENT '头像地址',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` int(1) DEFAULT NULL COMMENT '状态 0正常 1删除',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4  COMMENT='后台用户表';
INSERT INTO `sys_user` VALUES ('1', 'sysadmin', '$2a$10$xLYZfhgfkWcWdJ2ppwh6p.2Zlg7ltj3Yvv2RkodZHvGKwdzh2bCYa', '系统管理员', '1', '1', '15361666666', '597721790@qq.com', '1', '2018-11-20 17:44:13', null, '超级管理员', '0', null, '2018-11-20 17:44:20', null, '2019-01-26 16:58:00');



CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `ur_fk_user_id` (`user_id`),
  KEY `ur_fk_role_id` (`role_id`),
  CONSTRAINT `ur_fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `ur_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4  COMMENT='用户角色关联表';
INSERT INTO `sys_user_role` VALUES ('2', '1', '1');


---##### czl相关表 #####---
CREATE TABLE `czl_operate_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `record_type` varchar(50) DEFAULT NULL COMMENT '记录类型',
  `record_id` varchar(50) DEFAULT NULL COMMENT '记录对应的值',
  `json_data` varchar(5000) DEFAULT NULL COMMENT 'json原始数据',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='业务表修改记录，通过表坐标对应记录';

CREATE TABLE `czl_record_type` (
  `id` varchar(50) DEFAULT NULL COMMENT '记录id',
  `record_type` varchar(50) DEFAULT NULL COMMENT '记录类型',
  `table_name` varchar(50) DEFAULT NULL COMMENT '资产所在表',
  `field_name` varchar(50) DEFAULT NULL COMMENT '资产所在字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='存证链记录类型，对应相关的表';
INSERT INTO `czl_record_type` VALUES ('1', '设备变更记录', 'czl_operate_log', 'id\r\n');
INSERT INTO `czl_record_type` VALUES ('2', '发票凭证', 'device', 'voucher');
INSERT INTO `czl_record_type` VALUES ('3', '用户钱包', 'user', 'id');
INSERT INTO `czl_record_type` VALUES ('4', '诊断设备', 'device', 'serial_no');
INSERT INTO `czl_record_type` VALUES ('5', '用户订单记录', 'user_order', 'order_id');
INSERT INTO `czl_record_type` VALUES ('6', '用户消费记录', 'user_consumer_record', 'order_id');

-- 创建订单详情表
CREATE TABLE `user_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单详情ID',
  `order_id` bigint(20) NOT NULL COMMENT 'user_order订单表主键',
  `diag_soft_price_id` bigint(20) DEFAULT NULL COMMENT '诊断软件价格表ID',
  `soft_name` varchar(50) DEFAULT NULL COMMENT '软件名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

--#######智能合约相关表---------------------
DROP TABLE IF EXISTS `znhy_account`;
CREATE TABLE `znhy_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名字',
  `bank_card` varchar(30) DEFAULT NULL COMMENT '银行卡',
  `we_chat` varchar(50) DEFAULT NULL COMMENT '微信账号',
  `alipay` varchar(50) DEFAULT NULL COMMENT '支付宝账号',
  `paypal` varchar(50) DEFAULT NULL COMMENT 'paypal账号',
  `telephone` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `account_id` varchar(50) DEFAULT NULL COMMENT '钱包地址id',
  `creator` varchar(40) DEFAULT NULL COMMENT '创建者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `updator` varchar(40) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4  COMMENT='智能合约账户表';

DROP TABLE IF EXISTS `znhy_allocation`;
CREATE TABLE `znhy_allocation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `job_group_id` int(11) DEFAULT NULL COMMENT '分配组id',
  `creator_id` varchar(50) DEFAULT NULL COMMENT '创建分配表的账户ID',
  `name` varchar(50) DEFAULT NULL COMMENT '数字资产名字',
  `assets_type` varchar(40) DEFAULT NULL COMMENT '数字资产类型',
  `create_date` datetime DEFAULT NULL COMMENT '后台创建时间',
  `allocation_id` varchar(70) DEFAULT NULL COMMENT '分配表链上ID',
  `creator` varchar(40) DEFAULT NULL COMMENT '记录后台创建者',
  `updator` varchar(40) DEFAULT NULL COMMENT '后台记录更新者',
  `update_date` datetime DEFAULT NULL COMMENT '后台更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_znhy_allo_name` (`name`),
  KEY `FK_allocation_allocation_group` (`job_group_id`),
  CONSTRAINT `FK_allocation_allocation_group` FOREIGN KEY (`job_group_id`) REFERENCES `znhy_allocation_group` (`job_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4  COMMENT='智能合约分配表';

DROP TABLE IF EXISTS `znhy_allocation_group`;
CREATE TABLE `znhy_allocation_group` (
  `job_group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分配组id',
  `group_name` varchar(50) DEFAULT NULL COMMENT '分配组名称',
  `creator` varchar(40) DEFAULT NULL COMMENT '创建者姓名',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` varchar(40) DEFAULT NULL COMMENT '更新者姓名',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`job_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4  COMMENT='智能合约分配组表';

DROP TABLE IF EXISTS `znhy_allocation_rule`;
CREATE TABLE `znhy_allocation_rule` (
  `job_group_id` int(11) DEFAULT NULL COMMENT '分配组id',
  `job` varchar(50) DEFAULT NULL COMMENT '角色',
  `radios` double DEFAULT NULL COMMENT '分配比例',
  `account_id` varchar(50) DEFAULT NULL COMMENT '角色钱包地址id',
  KEY `FK_znhy_allocation_rule_znhy_allocation_group` (`job_group_id`),
  CONSTRAINT `FK_znhy_allocation_rule_znhy_allocation_group` FOREIGN KEY (`job_group_id`) REFERENCES `znhy_allocation_group` (`job_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='分配组内具体规则表';

DROP TABLE IF EXISTS `znhy_order_allocate_fail`;
CREATE TABLE `znhy_order_allocate_fail` (
  `order_id` int(11) DEFAULT NULL COMMENT '订单id',
  `serial_no` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '序列号',
  `code` int(11) DEFAULT NULL COMMENT '错误code',
  `message` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '错误消息',
  `success` tinyint(1) DEFAULT NULL COMMENT '是否已经成功',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='智能合约订单分配失败表';




