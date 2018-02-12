/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : hrp-sms

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-02-12 21:54:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_form_action
-- ----------------------------
DROP TABLE IF EXISTS `t_form_action`;
CREATE TABLE `t_form_action` (
  `class_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单id(参考t_menu表id) 明细菜单，即二级菜单',
  `name` varchar(20) NOT NULL COMMENT '功能名称',
  `text` varchar(20) NOT NULL COMMENT '操作方法名(前端使用)',
  `index` int(11) DEFAULT '0' COMMENT '功能显示顺序',
  `access_mask` int(11) NOT NULL COMMENT '权限掩码(每一个权限掩码代表了一个功能)',
  `access_use` int(11) NOT NULL DEFAULT '0' COMMENT '关联权限掩码总和。\r\n如有删除权限则一定具备查看及修改权限，\r\n所以删除权限的access_use应配置成查看及修改权限的\r\naccess_mask之和',
  `owner_type` int(11) DEFAULT '7' COMMENT '权限所有角色类别，\r\n可指定该权限只能由哪些类别的角色可用\r\n采用三位二进制描述\r\n001（1）:系统类别角色可用\r\n010（2）:医院类别角色可用\r\n100（4）:供应商类别角色可用\r\n还可以有其他组合设置如\r\n011（3）: 系统类别角色与医院类别角色可用\r\n111（7）:三类角色类别都可用\r\n\r\n默认三类角色都可用',
  `icon` varchar(50) DEFAULT '' COMMENT '菜单图标',
  `desc` varchar(255) DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`class_id`,`access_mask`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='\r\n该表对业务类型对应功能进行描述（根据用户的角色类别及角色确定功能可用性），\r\n前端根据此信息生成功能菜单\r\n\r\n如用户有 查看，新增，修改，删除，禁用 五个功能\r\n\r\n对于 系统角色类型 查看，新增，修改，删除，禁用 功能都可用\r\n\r\n对于 医院角色类型 查看，新增，修改，删除，禁用 功能都可用\r\n\r\n对于 供应商角色类型 查看，新增，修改，删除，禁用 功能都可用\r\n\r\n\r\n对于 系统角色类型中的 业务系统管理角色 查看，新增，修改，删除 可用 禁用 功能不可用\r\n\r\n系统预留如下权限掩码，不可暂用,业务特有功能权限掩码从4096开始编码\r\n\r\n查看:1;\r\n新增:2;\r\n修改:4;\r\n 删除:8;\r\n审核:16;\r\n反审核:32;\r\n禁用:64;\r\n反禁用:128;\r\n同步:256;\r\n';

-- ----------------------------
-- Records of t_form_action
-- ----------------------------
INSERT INTO `t_form_action` VALUES ('1001', '查看', 'view', '5', '1', '1', '7', '', '供应商资料查看权限');
INSERT INTO `t_form_action` VALUES ('1001', '修改', 'edit', '10', '4', '1', '7', '', '供应商资料修改权限');

-- ----------------------------
-- Table structure for t_form_class
-- ----------------------------
DROP TABLE IF EXISTS `t_form_class`;
CREATE TABLE `t_form_class` (
  `class_id` int(11) NOT NULL COMMENT '业务类型(每一个表单都有一个唯一的业务类型id)',
  `name` varchar(50) NOT NULL COMMENT '业务类型名称(如采购订单,用户,权限等)',
  `table_name` varchar(255) DEFAULT NULL COMMENT '数据存储物理表(主表,如用户资料存储表为t_user)',
  `primary_key` varchar(255) DEFAULT NULL COMMENT '数据存储物理表的主键(只能设置一个)',
  `desc` varchar(255) DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_form_class
-- ----------------------------
INSERT INTO `t_form_class` VALUES ('1001', '注册供应商', 't_supplier', 'id', '供应商注册用户时的供应商资料');
INSERT INTO `t_form_class` VALUES ('1002', '注册医院', 't_hospital', 'id', '医院注册用户时的医院基本资料');

-- ----------------------------
-- Table structure for t_form_fields
-- ----------------------------
DROP TABLE IF EXISTS `t_form_fields`;
CREATE TABLE `t_form_fields` (
  `class_id` int(11) NOT NULL COMMENT '业务类型id，对应t_form_class表class_id',
  `key` varchar(30) NOT NULL DEFAULT '' COMMENT '字段唯一标示(用于关联表显示字段名与本表字段名同名的情况，同一个class_id中key是唯一的)',
  `name` varchar(50) NOT NULL COMMENT '字段名(显示的字段名)',
  `page` int(4) NOT NULL DEFAULT '0' COMMENT '标示模板字段所在的页面（0是表头，1是第一个子表，2是第二个子表，以此类推...）',
  `sql_column_name` varchar(20) NOT NULL COMMENT '物理表中的字段名',
  `data_type` int(4) NOT NULL COMMENT '字段类型(数字，文本等)1:数字2:文本3:日期4:布尔,用于前端解析',
  `ctrl_type` int(4) NOT NULL COMMENT '控件类型，指示前端展示时的特殊类型，比如多选框，基础资料选择框等',
  `ctrl_index` int(11) NOT NULL DEFAULT '0' COMMENT '字段的后台查询顺序，index是用于前端界面展示时的tab顺序ctrl_index用于后端查询构建查询脚本时模板的遍历顺序，处理因为可能涉及关联其他表查询，连接表时的顺序不可随意改变时产生的错误',
  `index` int(11) DEFAULT '0' COMMENT '前端字段的显示顺序',
  `display` int(1) DEFAULT '1' COMMENT '字段显示性:用于根据角色类别控制字段显示性。\r\n采用三位二进制方式配置\r\n如1(001)表示该字段只有系统用户能可见；2(010)表示该字段只有医院用户可见；3(011)表示该字段系统用户与医院用户都可见\r\n参考t_SubSysEnum,typeID=3',
  `show_width` int(11) DEFAULT '80' COMMENT '字段在前端页面显示的宽度(单位px)',
  `lookup_type` int(11) DEFAULT '0' COMMENT '标示是否是引用类型',
  `lookup_class_id` int(11) DEFAULT '0' COMMENT '如果是引用类型，改字段指示引用类型的业务类型class_id',
  `src_table` varchar(20) DEFAULT '' COMMENT '引用的表名(表示此字段是要关联表查询)',
  `src_table_alis` varchar(30) DEFAULT '' COMMENT '关联表别名(用于关联多次同一张表时)',
  `src_field` varchar(30) DEFAULT '' COMMENT '关联表的字段名,当src_table有值时，此字段必须配置',
  `display_field` varchar(30) DEFAULT '' COMMENT '关联表显示的字段名,当src_table有值时，此字段为必填,即显示src_table表的display_field字段',
  `display_ext` varchar(30) DEFAULT '' COMMENT '关联表显示的附加字段,如关联基础资料时一般需要显示基础资料的名称与代码display_field配置成显示name,display_ext可配置成显示number',
  `join_type` varchar(20) DEFAULT '' COMMENT '表链接类型(默认 INNER JOIN)',
  `filter` varchar(500) DEFAULT '' COMMENT '可附加过滤条件',
  `default_value` varchar(255) DEFAULT NULL COMMENT '默认值(控件初始化时候的默认值)',
  `max_value` double(10,2) DEFAULT NULL COMMENT '最大值(数值类型字段使用)',
  `min_value` double(10,2) DEFAULT NULL COMMENT '最小值(数值类型字段使用)',
  `must_input` int(4) DEFAULT '0' COMMENT '是否必录： 采用二进制方式配置, 参考t_SubSysEnum,typeID=4',
  `length` int(11) DEFAULT NULL COMMENT '字段长度',
  `lock` int(4) DEFAULT '0' COMMENT '字段锁定性:表示字段页面展现的控制可编辑形式。\r\n采用二进制方式配置\r\n如3表示新增修改时都锁定\r\n参考t_SubSysEnum,typeID=2',
  `is_condition` int(4) DEFAULT '0' COMMENT '是否可作为过滤条件，0：否，1：是',
  `is_count` int(4) DEFAULT '0' COMMENT '是否统计项，0：否，1：是',
  PRIMARY KEY (`class_id`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_form_fields
-- ----------------------------

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
INSERT INTO `t_menu` VALUES ('8', '用户管理', '1', 'icon-yiyuan1 icon-color-red', 'html/base/index1.html?classId=1001', '0', '系统用户管理', '');
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
INSERT INTO `t_menu` VALUES ('53', '角色', '1', 'icon-jiaoseguanli', 'html/base/index1.html?classId=1001', '5', '角色', '');
INSERT INTO `t_menu` VALUES ('54', '角色类别', '1', 'icon-leimupinleifenleileibie2 icon-color-green', 'html/base/index1.html?classId=1001', '5', '角色类别', '');
INSERT INTO `t_menu` VALUES ('55', '角色权限', '1', 'icon-setting-permissions', 'html/base/index1.html?classId=1001', '5', '角色权限', '');
INSERT INTO `t_menu` VALUES ('61', '我的供应商', '1', 'icon-icon-supplier', 'html/base/index1.html?classId=1001', '6', '医院维护的供应商资料', '');
INSERT INTO `t_menu` VALUES ('62', '供应商对照', '1', 'icon-icon-p_gongyingshangfenpei', 'html/base/index1.html?classId=1001', '6', '医院供应商与供应商用户的对应关系', '');
INSERT INTO `t_menu` VALUES ('71', '我的客户', '1', 'icon-web-icon-', 'html/base/index1.html?classId=1001', '7', '已我有关系的医院', '');
INSERT INTO `t_menu` VALUES ('81', '用户', '1', 'icon-yonghutouxiang', 'html/base/index1.html?classId=1001', '8', '用户', '');
INSERT INTO `t_menu` VALUES ('82', '系统用户资料', '1', 'icon-yonghutouxiang', 'html/base/index1.html?classId=1001', '8', '系统用户资料', '');
INSERT INTO `t_menu` VALUES ('83', '医院用户资料', '1', 'icon-yonghutouxiang', 'html/base/index1.html?classId=1001', '8', '医院用户资料', '');
INSERT INTO `t_menu` VALUES ('84', '供应商用户资料', '1', 'icon-yonghutouxiang', 'html/base/index1.html?classId=1001', '8', '供应商用户资料', '');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(64) NOT NULL COMMENT '内码',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色类别表(该表数据有系统内置且不可修改)\r\n\r\n系统用户的角色分三个类别，目前一个用户只支持对应一个角色\r\n\r\n1：系统管理员用的角色\r\n2：医院角色\r\n3：供应商角色';

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
  `org` bigint(64) NOT NULL COMMENT '关联组织\r\n对于供应商用户此字段记录t_supplier供应商资料id\r\n对于医院用户此字段记录t_hospital医院资料id\r\n对于系统用户改字段为0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('11', 'yadda', '111', 'bcbe3365e6ac95ea2c0343a2395834dd', '15212345678', '1', '', '0');
