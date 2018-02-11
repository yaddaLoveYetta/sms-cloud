/*
Navicat MySQL Data Transfer

Source Server         : mysql-180
Source Server Version : 50617
Source Host           : 10.0.0.180:3306
Source Database       : hrp-sms

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-02-11 18:11:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_hospital
-- ----------------------------
DROP TABLE IF EXISTS `t_hospital`;
CREATE TABLE `t_hospital` (
  `id` bigint(64) NOT NULL COMMENT '内码',
  `number` varchar(20) NOT NULL COMMENT '代码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `business_license` varchar(20) NOT NULL COMMENT '营业执照号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_hospital
-- ----------------------------

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单名字',
  `index` int(11) NOT NULL DEFAULT '0' COMMENT '菜单显示顺序',
  `icon` varchar(50) DEFAULT '' COMMENT '菜单图标',
  `url` varchar(500) DEFAULT NULL COMMENT '菜单链接地址',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级菜单id',
  `desc` varchar(100) DEFAULT '' COMMENT '菜单描述',
  `visible` bit(1) DEFAULT b'1' COMMENT '是否显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '订单管理', '1', 'icon-dingdan', 'html/base/index1.html?classId=1001', '0', '订单管理', '');
INSERT INTO `t_menu` VALUES ('2', '证件管理', '1', 'icon-msnui-intelligence icon-color-red', 'html/base/index1.html?classId=1001', '0', '证件管理', '');
INSERT INTO `t_menu` VALUES ('3', '发货管理', '1', 'icon-fahuo icon-color-light-blue', 'html/base/index1.html?classId=1001', '0', '发货管理', '');
INSERT INTO `t_menu` VALUES ('4', '统计查询', '1', 'icon-tongji2 icon-color-green', 'html/base/index1.html?classId=1001', '0', '统计查询', '');
INSERT INTO `t_menu` VALUES ('5', '基础资料', '1', 'icon-jibenziliao1 icon-color-blue', 'html/base/index1.html?classId=1001', '0', '基础资料', '');
INSERT INTO `t_menu` VALUES ('6', '供应商管理', '1', 'icon-supplier icon-color-purple', 'html/base/index1.html?classId=1001', '0', '供应商管理', '');
INSERT INTO `t_menu` VALUES ('7', '客户管理', '1', 'icon-yiyuan1 icon-color-red', 'html/base/index1.html?classId=1001', '0', '供应商的医院客户管理', '');
INSERT INTO `t_menu` VALUES ('10', '系统设置', '1', 'icon-set icon-color-blue', 'html/base/index1.html?classId=1001', '0', '系统设置', '');
INSERT INTO `t_menu` VALUES ('11', '采购订单', '1', 'icon-caigoudingdan', 'html/base/index1.html?classId=1001', '1', '采购订单', '');
INSERT INTO `t_menu` VALUES ('12', '订单追踪', '1', 'icon-icon-p_mrpjinduzhuizong', 'html/base/index1.html?classId=1001', '1', '订单追踪', '');
INSERT INTO `t_menu` VALUES ('21', '供应商资质维护', '1', 'icon-msnui-intelligence', 'html/base/index1.html?classId=1001', '2', '供应商资质管理', '');
INSERT INTO `t_menu` VALUES ('22', '物料证件维护', '1', 'icon-msnui-intelligence', 'html/base/index1.html?classId=1001', '2', '物料证件维护', '');
INSERT INTO `t_menu` VALUES ('23', '供应商物料查询', '1', 'icon-chaxun', 'html/base/index1.html?classId=1001', '2', '供应商物料查询', '');
INSERT INTO `t_menu` VALUES ('24', '物料证件查询', '1', 'icon-chaxun', 'html/base/index1.html?classId=1001', '2', '物料证件查询', '');
INSERT INTO `t_menu` VALUES ('25', '供应商证件类别', '1', 'icon-leibie', 'html/base/index1.html?classId=1001', '2', '供应商证件类别', '');
INSERT INTO `t_menu` VALUES ('26', '物料证件类别', '1', 'icon-leibie', 'html/base/index1.html?classId=1001', '2', '物料证件类别', '');
INSERT INTO `t_menu` VALUES ('31', '发货单', '1', 'icon-daifahuo', 'html/base/index1.html?classId=1001', '3', '发货单', '');
INSERT INTO `t_menu` VALUES ('41', '订单统计', '1', 'icon-tongji', 'html/base/index1.html?classId=1001', '4', '订单统计', '');
INSERT INTO `t_menu` VALUES ('51', '用户', '1', 'icon-yonghutouxiang', 'html/base/index1.html?classId=1001', '5', '用户', '');
INSERT INTO `t_menu` VALUES ('52', '用户类别', '1', 'icon-leimupinleifenleileibie2 icon-color-green', 'html/base/index1.html?classId=1001', '5', '用户类别', '');
INSERT INTO `t_menu` VALUES ('53', '角色', '1', 'icon-jiaoseguanli', 'html/base/index1.html?classId=1001', '5', '角色', '');
INSERT INTO `t_menu` VALUES ('54', '角色类别', '1', 'icon-leimupinleifenleileibie2 icon-color-green', 'html/base/index1.html?classId=1001', '5', '角色类别', '');
INSERT INTO `t_menu` VALUES ('55', '角色权限', '1', 'icon-setting-permissions', 'html/base/index1.html?classId=1001', '5', '角色权限', '');
INSERT INTO `t_menu` VALUES ('61', '我的供应商', '1', 'icon-icon-supplier', 'html/base/index1.html?classId=1001', '6', '医院维护的供应商资料', '');
INSERT INTO `t_menu` VALUES ('62', '供应商对照', '1', 'icon-icon-p_gongyingshangfenpei', 'html/base/index1.html?classId=1001', '6', '医院供应商与供应商用户的对应关系', '');
INSERT INTO `t_menu` VALUES ('71', '我的客户', '1', 'icon-web-icon-', 'html/base/index1.html?classId=1001', '7', '已我有关系的医院', '');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL COMMENT '内码',
  `number` varchar(20) NOT NULL DEFAULT '' COMMENT '代码',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '角色类别(关联t_role_type表id)',
  `user_define` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否用户自定义(0:系统内置 1:用户自定义)\r\n系统内置角色不可修改删除',
  `org` bigint(64) NOT NULL DEFAULT '0' COMMENT '角色所属组织(属于哪一个医院或者供应商，用户自定义的角色一定要有所属组织\r\n默认0:系统管理员，\r\n属于供应商类别时，关联t_supplier表id，即归属于某一个供应商，\r\n属于医院类别时，关联t_hospital表id，即归属于某一家医院',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'system_admin', '平台内置角色', '1', '\0', '0');
INSERT INTO `t_role` VALUES ('2', 'hospital_admin', '医院内置角色', '2', '\0', '0');
INSERT INTO `t_role` VALUES ('3', 'supplier_admin', '供应商内置角色', '3', '\0', '0');

-- ----------------------------
-- Table structure for t_role_type
-- ----------------------------
DROP TABLE IF EXISTS `t_role_type`;
CREATE TABLE `t_role_type` (
  `id` int(11) NOT NULL COMMENT '内码',
  `number` varchar(20) NOT NULL COMMENT '代码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色类别表(该表数据不可修改)\r\n\r\n系统用户的角色分三个类别，目前一个用户只支持对应一个角色\r\n\r\n1：系统管理员用的角色\r\n2：医院角色\r\n3：供应商角色';

-- ----------------------------
-- Records of t_role_type
-- ----------------------------
INSERT INTO `t_role_type` VALUES ('1', 'System', '系统管理员');
INSERT INTO `t_role_type` VALUES ('2', 'Hospital', '医院');
INSERT INTO `t_role_type` VALUES ('3', 'Supplier', '供应商');

-- ----------------------------
-- Table structure for t_supplier
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `id` bigint(64) NOT NULL COMMENT '内码',
  `number` varchar(20) NOT NULL COMMENT '代码',
  `name` varchar(50) NOT NULL COMMENT '公司名称',
  `business_license` varchar(20) NOT NULL COMMENT '营业执照号码',
  `address` varchar(255) NOT NULL COMMENT '公司地址',
  `tax_id` varchar(255) NOT NULL COMMENT '税务登记号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(64) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '真实姓名',
  `user_name` varchar(20) NOT NULL COMMENT '登录账号',
  `password` varchar(50) NOT NULL COMMENT '登陆密码',
  `mobile` varchar(11) NOT NULL COMMENT '手机号码',
  `role` bigint(64) NOT NULL COMMENT '角色(关联t_role表id)',
  `is_admin` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('11', 'yadda', '111', 'bcbe3365e6ac95ea2c0343a2395834dd', '15212345678', '1', '');
