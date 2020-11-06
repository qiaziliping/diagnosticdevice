-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.11 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 diagdevice_qkl 的数据库结构
CREATE DATABASE IF NOT EXISTS `diagdevice_qkl` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `diagdevice_qkl`;


-- 导出  表 diagdevice_qkl.znhy_account 结构
CREATE TABLE IF NOT EXISTS `znhy_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名字',
  `bank_card` varchar(30) DEFAULT NULL COMMENT '银行卡',
  `we_chat` varchar(50) DEFAULT NULL COMMENT '微信账号',
  `alipay` varchar(50) DEFAULT NULL COMMENT '支付宝账号',
  `telephone` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `create_date` date DEFAULT NULL COMMENT '创建日期',
  `account_id` varchar(50) DEFAULT NULL COMMENT '钱包地址id',
  `creator` varchar(40) DEFAULT NULL COMMENT '创建者',
  `update_date` date DEFAULT NULL COMMENT '更新时间',
  `updator` varchar(40) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54323 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='智能合约账户表';

-- 正在导出表  diagdevice_qkl.znhy_account 的数据：~3 rows (大约)
DELETE FROM `znhy_account`;
/*!40000 ALTER TABLE `znhy_account` DISABLE KEYS */;
INSERT INTO `znhy_account` (`id`, `name`, `bank_card`, `we_chat`, `alipay`, `telephone`, `create_date`, `account_id`, `creator`, `update_date`, `updator`) VALUES
	(12345, '李平', NULL, NULL, '15360166220', '15360166220', '2018-09-15', '0x59da5eefdfb3a653ef95e6f7fddecc55eb26b4a4', NULL, '2018-09-21', NULL),
	(54321, '欧祥瑞', NULL, NULL, '18617086698', '18617086698', '2018-09-15', '0xd78062de5ca25f7fe1b6a48454529118c8bf1a74', NULL, '2018-09-21', NULL),
	(54322, '张三', NULL, NULL, '13987654323', '13987654321', '2018-09-18', '0xb972fb41fb342c9316b6d7355b0df4164f8857a3', NULL, '2018-09-21', 'yaoyao');
/*!40000 ALTER TABLE `znhy_account` ENABLE KEYS */;


-- 导出  表 diagdevice_qkl.znhy_allocation 结构
CREATE TABLE IF NOT EXISTS `znhy_allocation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `job_group_id` int(11) DEFAULT NULL COMMENT '分配组id',
  `creator_id` varchar(50) DEFAULT NULL COMMENT '创建分配表的账户ID',
  `name` varchar(50) DEFAULT NULL COMMENT '数字资产名字',
  `assets_type` varchar(40) DEFAULT NULL COMMENT '数字资产类型',
  `create_date` date DEFAULT NULL COMMENT '后台创建时间',
  `allocation_id` varchar(70) DEFAULT NULL COMMENT '分配表链上ID',
  `creator` varchar(40) DEFAULT NULL COMMENT '记录后台创建者',
  `updator` varchar(40) DEFAULT NULL COMMENT '后台记录更新者',
  `update_date` date DEFAULT NULL COMMENT '后台更新时间',
  PRIMARY KEY (`id`),
  KEY `FK_allocation_allocation_group` (`job_group_id`),
  CONSTRAINT `FK_allocation_allocation_group` FOREIGN KEY (`job_group_id`) REFERENCES `znhy_allocation_group` (`job_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='智能合约分配表';

-- 正在导出表  diagdevice_qkl.znhy_allocation 的数据：~2 rows (大约)
DELETE FROM `znhy_allocation`;
/*!40000 ALTER TABLE `znhy_allocation` DISABLE KEYS */;
INSERT INTO `znhy_allocation` (`id`, `job_group_id`, `creator_id`, `name`, `assets_type`, `create_date`, `allocation_id`, `creator`, `updator`, `update_date`) VALUES
	(1, 1, '0xd78062de5ca25f7fe1b6a48454529118c8bf1a74', '9852641256325', '0', '2018-09-15', '4c8959e5f06635b2a99751a14b6402632eecbcf69877680abfc8fe126fcdf902', NULL, NULL, '2018-09-21'),
	(3, 2, '0xd78062de5ca25f7fe1b6a48454529118c8bf1a74', '978547654321', '0', '2018-09-18', '0503ed88bfce6f4261dad1af13f62cfc7bf587dc586beda22280cca67532402b', '54322', 'system', '2018-09-21');
/*!40000 ALTER TABLE `znhy_allocation` ENABLE KEYS */;


-- 导出  表 diagdevice_qkl.znhy_allocation_group 结构
CREATE TABLE IF NOT EXISTS `znhy_allocation_group` (
  `job_group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分配组id',
  `group_name` varchar(50) DEFAULT NULL COMMENT '分配组名称',
  `creator` varchar(40) DEFAULT NULL COMMENT '创建者姓名',
  `create_date` date DEFAULT NULL COMMENT '创建时间',
  `updator` varchar(40) DEFAULT NULL COMMENT '更新者姓名',
  `update_date` date DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`job_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='智能合约分配组表';

-- 正在导出表  diagdevice_qkl.znhy_allocation_group 的数据：~0 rows (大约)
DELETE FROM `znhy_allocation_group`;
/*!40000 ALTER TABLE `znhy_allocation_group` DISABLE KEYS */;
INSERT INTO `znhy_allocation_group` (`job_group_id`, `group_name`, `creator`, `create_date`, `updator`, `update_date`) VALUES
	(1, '设备分成组', 'ouxiangrui', '2018-09-15', NULL, NULL),
	(2, '智能分账组', 'qingqing.li', '2018-09-18', 'yaoyao.wei', '2018-09-18');
/*!40000 ALTER TABLE `znhy_allocation_group` ENABLE KEYS */;


-- 导出  表 diagdevice_qkl.znhy_allocation_rule 结构
CREATE TABLE IF NOT EXISTS `znhy_allocation_rule` (
  `job_group_id` int(11) DEFAULT NULL COMMENT '分配组id',
  `job` varchar(50) DEFAULT NULL COMMENT '角色',
  `radios` double DEFAULT NULL COMMENT '分配比例',
  `account_id` varchar(50) DEFAULT NULL COMMENT '角色钱包地址id',
  KEY `FK_znhy_allocation_rule_znhy_allocation_group` (`job_group_id`),
  CONSTRAINT `FK_znhy_allocation_rule_znhy_allocation_group` FOREIGN KEY (`job_group_id`) REFERENCES `znhy_allocation_group` (`job_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分配组内具体规则表';

-- 正在导出表  diagdevice_qkl.znhy_allocation_rule 的数据：~4 rows (大约)
DELETE FROM `znhy_allocation_rule`;
/*!40000 ALTER TABLE `znhy_allocation_rule` DISABLE KEYS */;
INSERT INTO `znhy_allocation_rule` (`job_group_id`, `job`, `radios`, `account_id`) VALUES
	(1, '平台', 0.2, '0xd78062de5ca25f7fe1b6a48454529118c8bf1a74'),
	(1, '所有者', 0.8, '0x59da5eefdfb3a653ef95e6f7fddecc55eb26b4a4'),
	(2, 'platform', 0.4, '0xd78062de5ca25f7fe1b6a48454529118c8bf1a74'),
	(2, 'ower', 0.6, '0xb972fb41fb342c9316b6d7355b0df4164f8857a3');
/*!40000 ALTER TABLE `znhy_allocation_rule` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
