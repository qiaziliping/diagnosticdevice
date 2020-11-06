/*
 * 
权限表数据，基础数据，resource_code的值不能随意修改

Navicat MySQL Data Transfer

Source Server         : locahost
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : bc_diagdevice

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-01-26 16:35:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_authority`
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COMMENT='系统资源';

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES ('1', '0', '0', '系统管理', '2', null, 'SYS_MGR', null, null, '系统管理', '0', null, '2019-01-05 10:06:34', null, '2019-01-05 10:06:37');
INSERT INTO `sys_authority` VALUES ('2', '1', '1', '权限管理', '3', null, 'SYS_MGR_AUTH', null, null, '权限管理', '0', null, '2019-01-05 10:10:05', null, '2019-01-05 10:10:07');
INSERT INTO `sys_authority` VALUES ('3', '2', '3', '权限管理新增', '5', '/sys/authority/save/**', 'SYS_MGR_AUTH_ADD', null, null, '权限管理新增', '0', null, '2019-01-05 10:12:13', null, '2019-01-05 10:12:16');
INSERT INTO `sys_authority` VALUES ('4', '2', '4', '权限管理修改', '5', '/sys/authority/update/**', 'SYS_MGR_AUTH_UPDATE', null, null, '权限管理修改', '0', null, '2019-01-05 10:13:04', null, '2019-01-07 11:43:34');
INSERT INTO `sys_authority` VALUES ('5', '2', '0', '权限管理删除', '5', '/sys/authority/delete/**', 'SYS_MGR_AUTH_DEL', null, null, null, '0', null, '2019-01-05 16:30:25', null, null);
INSERT INTO `sys_authority` VALUES ('8', '0', '0', '分账管理', '2', null, 'ZNHY_MGR', null, null, null, '0', null, '2019-01-10 10:13:38', null, null);
INSERT INTO `sys_authority` VALUES ('10', '8', '0', '分账账户管理', '3', null, 'ZNHY_MGR_ACCOUNT', null, null, null, '0', null, '2019-01-10 10:31:22', null, '2019-01-10 10:35:06');
INSERT INTO `sys_authority` VALUES ('11', '10', '0', '分账账户管理查询', '5', '/znhy/getZnhyAccountList', 'ZNHY_MGR_ACCOUNT_QRY', null, null, null, '0', null, '2019-01-10 10:32:37', null, null);
INSERT INTO `sys_authority` VALUES ('12', '10', '0', '分账账户管理新增', '5', '/znhy/saveZnhyAccount/**', 'ZNHY_MGR_ACCOUNT_ADD', null, null, null, '0', null, '2019-01-10 10:33:39', null, null);
INSERT INTO `sys_authority` VALUES ('13', '10', '0', '分账账户管理修改', '5', '/znhy/updateZnhyAccount/**', 'ZNHY_MGR_ACCOUNT_UPDATE', null, null, null, '0', null, '2019-01-10 10:34:09', null, null);
INSERT INTO `sys_authority` VALUES ('14', '8', '0', '分账规则管理', '3', null, 'ZNHY_MGR_RULE', null, null, null, '0', null, '2019-01-10 10:34:46', null, '2019-01-10 10:35:44');
INSERT INTO `sys_authority` VALUES ('15', '14', '0', '分账规则管理新增', '5', '/znhy/saveZnhyAllocationRule/**', 'ZNHY_MGR_RULE_ADD', null, null, null, '0', null, '2019-01-10 10:36:58', null, null);
INSERT INTO `sys_authority` VALUES ('16', '14', '0', '分账规则管理修改', '5', '/znhy/updateAllocationRule/**', 'ZNHY_MGR_RULE_UPDATE', null, null, null, '0', null, '2019-01-10 10:37:31', null, null);
INSERT INTO `sys_authority` VALUES ('17', '14', '0', '分账规则管理查询', '5', '/znhy/getAllocationGroupList', 'ZNHY_MGR_RULE_QRY', null, null, null, '0', null, '2019-01-10 10:37:59', null, null);
INSERT INTO `sys_authority` VALUES ('18', '8', '0', '资产分账管理', '3', null, 'ZNHY_MGR_ALLOCATION', null, null, null, '0', null, '2019-01-10 10:38:49', null, null);
INSERT INTO `sys_authority` VALUES ('19', '18', '0', '资产分账管理查询', '5', '/znhy/getZnhyAllocationPage', 'ZNHY_MGR_ALLOCATION_QRY', null, null, null, '0', null, '2019-01-10 10:39:28', null, null);
INSERT INTO `sys_authority` VALUES ('20', '18', '0', '资产分账管理新增', '5', '/znhy/saveZnhyAllocation/**', 'ZNHY_MGR_ALLOCATION_ADD', null, null, null, '0', null, '2019-01-10 10:40:01', null, null);
INSERT INTO `sys_authority` VALUES ('21', '18', '0', '资产分账管理修改', '5', '/znhy/updateZnhyAllocation/**', 'ZNHY_MGR_ALLOCATION_UPDATE', null, null, null, '0', null, '2019-01-10 10:40:24', null, null);
INSERT INTO `sys_authority` VALUES ('22', '0', '0', 'APP用户管理', '2', null, 'APP_USER_MGR', null, null, null, '0', null, '2019-01-11 11:02:21', null, null);
INSERT INTO `sys_authority` VALUES ('23', '22', '0', 'APP用户管理查询', '5', '/loginUser/getUserList', 'APP_USER_MGR_QRY', null, null, null, '0', null, '2019-01-11 11:03:14', null, null);
INSERT INTO `sys_authority` VALUES ('26', '0', '0', '设备管理', '2', null, 'DEVICE_MGR', null, null, null, '0', null, '2019-01-15 17:33:23', null, null);
INSERT INTO `sys_authority` VALUES ('27', '26', '0', '设备管理添加', '5', '/device/save/**', 'DEVICE_MGR_ADD', null, null, null, '0', null, '2019-01-15 17:34:02', null, '2019-01-24 17:13:23');
INSERT INTO `sys_authority` VALUES ('28', '26', '0', '设备管理修改', '5', '/device/update/**', 'DEVICE_MGR_UPDATE', null, null, null, '0', null, '2019-01-15 17:34:56', null, '2019-01-24 17:13:42');
INSERT INTO `sys_authority` VALUES ('29', '26', '0', '设备管理查询', '5', '/device/getList', 'DEVICE_MGR_QRY', null, null, null, '0', null, '2019-01-15 17:35:35', null, '2019-01-24 17:13:54');
INSERT INTO `sys_authority` VALUES ('30', '0', '0', '消费记录', '2', null, 'CONSUME_MGR', null, null, null, '0', null, '2019-01-15 20:25:36', null, null);
INSERT INTO `sys_authority` VALUES ('31', '30', '0', '消费记录查询', '5', '/loginUser/getConsumerRecordList', 'CONSUME_MGR_QRY', null, null, null, '0', null, '2019-01-15 20:26:16', null, null);
INSERT INTO `sys_authority` VALUES ('32', '0', '0', '价格管理', '2', null, 'SOFT_PRICE_MGR', null, null, null, '0', null, '2019-01-15 20:27:29', null, null);
INSERT INTO `sys_authority` VALUES ('33', '32', '0', '软件价格查询', '5', '/diagSoft/getDiagSoftPriceList', 'SOFT_PRICE_MGR_QRY', null, null, null, '0', null, '2019-01-15 20:28:08', null, null);
INSERT INTO `sys_authority` VALUES ('34', '32', '0', '软件价格新增', '5', '/diagSoft/saveDiagSoftPrice/**', 'SOFT_PRICE_MGR_ADD', null, null, null, '0', null, '2019-01-15 20:28:38', null, null);
INSERT INTO `sys_authority` VALUES ('35', '32', '0', '软件价格修改', '5', '/diagSoft/updateDiagSoftPrice/**', 'SOFT_PRICE_MGR_UPDATE', null, null, null, '0', null, '2019-01-15 20:29:02', null, null);
INSERT INTO `sys_authority` VALUES ('36', '32', '0', '软件价格删除', '5', '/diagSoft/deleteDiagSoftPrice/**', 'SOFT_PRICE_MGR_DEL', null, null, null, '0', null, '2019-01-15 20:29:37', null, null);
INSERT INTO `sys_authority` VALUES ('37', '1', '0', '用户管理', '3', null, 'SYS_MGR_USER', null, null, null, '0', null, '2019-01-15 20:31:43', null, null);
INSERT INTO `sys_authority` VALUES ('38', '1', '0', '角色管理', '3', null, 'SYS_MGR_ROLE', null, null, null, '0', null, '2019-01-15 20:32:20', null, null);
INSERT INTO `sys_authority` VALUES ('39', '37', '0', '用户管理查询', '5', '/sys/user/getSysUserPage', 'SYS_MGR_USER_QRY', null, null, null, '0', null, '2019-01-15 20:33:00', null, null);
INSERT INTO `sys_authority` VALUES ('40', '37', '0', '用户管理新增', '5', '/sys/user/save/**', 'SYS_MGR_USER_ADD', null, null, null, '0', null, '2019-01-15 20:33:31', null, null);
INSERT INTO `sys_authority` VALUES ('41', '37', '0', '用户管理修改', '5', '/sys/user/update/**', 'SYS_MGR_USER_UPDATE', null, null, null, '0', null, '2019-01-15 20:34:00', null, null);
INSERT INTO `sys_authority` VALUES ('42', '37', '0', '用户管理删除', '5', '/sys/user/del/**', 'SYS_MGR_USER_DEL', null, null, null, '0', null, '2019-01-15 20:34:23', null, null);
INSERT INTO `sys_authority` VALUES ('43', '38', '0', '角色管理查询', '5', '/sys/role/getSysRolePage', 'SYS_MGR_ROLE_QRY', null, null, null, '0', null, '2019-01-15 20:35:10', null, null);
INSERT INTO `sys_authority` VALUES ('44', '38', '0', '角色管理新增', '5', '/sys/role/save/**', 'SYS_MGR_ROLE_ADD', null, null, null, '0', null, '2019-01-15 20:35:35', null, null);
INSERT INTO `sys_authority` VALUES ('45', '38', '0', '角色管理修改', '5', '/sys/role/update/**', 'SYS_MGR_ROLE_UPDATE', null, null, null, '0', null, '2019-01-15 20:35:59', null, null);
INSERT INTO `sys_authority` VALUES ('46', '38', '0', '角色管理删除', '5', '/sys/role/delete/**', 'SYS_MGR_ROLE_DEL', null, null, null, '0', null, '2019-01-15 20:36:21', null, null);
INSERT INTO `sys_authority` VALUES ('47', '38', '0', '角色管理授权', '5', '/sys/role/saveRoleAuthority/**', 'SYS_MGR_ROLE_AUTH', null, null, null, '0', null, '2019-01-15 20:37:45', null, null);
INSERT INTO `sys_authority` VALUES ('48', '38', '0', '角色管理权限查询', '5', '/sys/role/getRoleAuthorityByRoleId/**', 'SYS_MGR_ROLE_AUTH_QRY', null, null, null, '0', null, '2019-01-15 20:38:36', null, null);
INSERT INTO `sys_authority` VALUES ('49', '2', '0', '权限管理查询', '5', '/sys/authority/getSysAuthorityPage', 'SYS_MGR_AUTH_QRY', null, null, null, '0', null, '2019-01-25 15:09:20', null, null);
