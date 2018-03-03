/*
Navicat MySQL Data Transfer

Source Server         : mysql_180
Source Server Version : 50617
Source Host           : 10.0.0.180:3306
Source Database       : hrp-sms

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-03-02 17:47:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_assistance
-- ----------------------------
DROP TABLE IF EXISTS `t_assistance`;
CREATE TABLE `t_assistance` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '在每一个辅助属性类别中是唯一的',
  `type_id` int(11) NOT NULL DEFAULT '0' COMMENT '资料类别id，关联t_assistance_type表id',
  `number` varchar(20) DEFAULT NULL COMMENT '辅助属性代码',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可用',
  `index` int(11) DEFAULT '0' COMMENT '显示顺序',
  PRIMARY KEY (`detail_id`,`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='辅助属性表\r\n存储系统中枚举变量值，如币别有：人民币，美元，欧元等类似的可以只用代码名称两个属性描述的资料';

-- ----------------------------
-- Records of t_assistance
-- ----------------------------
INSERT INTO `t_assistance` VALUES ('1', '10', '', '数字', '', '0');
INSERT INTO `t_assistance` VALUES ('1', '15', '', '新增时对于平台用户锁定', '', '0');
INSERT INTO `t_assistance` VALUES ('1', '20', '', '查看时对于平台用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('1', '25', '', '新增时对于平台用户必填', '', '0');
INSERT INTO `t_assistance` VALUES ('1', '30', '', '数字', '', '0');
INSERT INTO `t_assistance` VALUES ('1', '35', '', '引用基础资料', '', '0');
INSERT INTO `t_assistance` VALUES ('2', '10', '', '文本值', '', '0');
INSERT INTO `t_assistance` VALUES ('2', '15', '', '编辑时对于平台用户锁定', '', '0');
INSERT INTO `t_assistance` VALUES ('2', '20', '', '新增时对于平台用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('2', '25', '', '编辑时对于平台用户必填', '', '0');
INSERT INTO `t_assistance` VALUES ('2', '30', '', '数字带小数', '', '0');
INSERT INTO `t_assistance` VALUES ('2', '35', '', '引用辅助属性', '', '0');
INSERT INTO `t_assistance` VALUES ('3', '10', '', '日期时间', '', '0');
INSERT INTO `t_assistance` VALUES ('3', '30', '', '选择框', '', '0');
INSERT INTO `t_assistance` VALUES ('3', '35', '', '引用基础资料的附加属性', '', '0');
INSERT INTO `t_assistance` VALUES ('4', '10', '', '布尔', '', '0');
INSERT INTO `t_assistance` VALUES ('4', '15', '', '新增时对于供应商用户锁定', '', '0');
INSERT INTO `t_assistance` VALUES ('4', '20', '', '编辑时对于平台用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('4', '25', '', '新增时对于供应商用户必填', '', '0');
INSERT INTO `t_assistance` VALUES ('4', '35', '', '普通引用其他表-关联查询', '', '0');
INSERT INTO `t_assistance` VALUES ('5', '30', '', '下拉列表', '', '0');
INSERT INTO `t_assistance` VALUES ('5', '35', '', '普通引用其他表的其他字段-主要为了避免为4即引用他表数据时，需引用多个字段时关联表重复问题。依附于=4时存在', '', '0');
INSERT INTO `t_assistance` VALUES ('6', '30', '', 'F7选择框', '', '0');
INSERT INTO `t_assistance` VALUES ('7', '30', '', '级联选择器', '', '0');
INSERT INTO `t_assistance` VALUES ('8', '15', '', '编辑时对于供应商用户锁定', '', '0');
INSERT INTO `t_assistance` VALUES ('8', '20', '', '查看时对于供应商用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('8', '25', '', '编辑时对于供应商用户必填', '', '0');
INSERT INTO `t_assistance` VALUES ('8', '30', '', '手机号码', '', '0');
INSERT INTO `t_assistance` VALUES ('9', '30', '', '座机电话', '', '0');
INSERT INTO `t_assistance` VALUES ('10', '30', '', '普通文本', '', '0');
INSERT INTO `t_assistance` VALUES ('11', '30', '', '多行文本', '', '0');
INSERT INTO `t_assistance` VALUES ('12', '30', '', '日期时间', '', '0');
INSERT INTO `t_assistance` VALUES ('13', '30', '', '男：女', '', '0');
INSERT INTO `t_assistance` VALUES ('14', '30', '', '密码控件', '', '0');
INSERT INTO `t_assistance` VALUES ('15', '30', '', '是:否', '', '0');
INSERT INTO `t_assistance` VALUES ('16', '15', '', '新增时对于医院用户锁定', '', '0');
INSERT INTO `t_assistance` VALUES ('16', '20', '', '新增时对于供应商用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('16', '25', '', '新增时对于医院用户必填', '', '0');
INSERT INTO `t_assistance` VALUES ('32', '15', '', '编辑时对于医院用户锁定', '', '0');
INSERT INTO `t_assistance` VALUES ('32', '20', '', '编辑时对于供应商用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('32', '25', '', '编辑时对于医院用户必填', '', '0');
INSERT INTO `t_assistance` VALUES ('64', '20', '', '查看时对于医院用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('128', '20', '', '新增时对于医院用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('256', '20', '', '编辑时对于医院用户显示', '', '0');
INSERT INTO `t_assistance` VALUES ('512', '20', '', '是否在列表中显示(子表模板独有,子表数据显示在表头列表中)', '', '0');

-- ----------------------------
-- Table structure for t_assistance_type
-- ----------------------------
DROP TABLE IF EXISTS `t_assistance_type`;
CREATE TABLE `t_assistance_type` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '类别名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='辅助属性类别表\r\n存储系统中枚举类型资料（该类资料只有代码名称）';

-- ----------------------------
-- Records of t_assistance_type
-- ----------------------------
INSERT INTO `t_assistance_type` VALUES ('10', '字段类型', '基础资料元数据配置中(t_form_fields)的dataType值参考这里，一般可设置为该字段在数据库里面的储存类型对应的java数据类型，后台应根据此配置校验前端提交的数据合法性');
INSERT INTO `t_assistance_type` VALUES ('15', '字段锁定性', '基础资料元数据配置中(t_form_fields)的lock值参考这里，用于前段页面控制字段可用性');
INSERT INTO `t_assistance_type` VALUES ('20', '字段数据权限控制类型', '后端元数据配置中(t_form_fields)的disPlay值参考这里');
INSERT INTO `t_assistance_type` VALUES ('25', '字段必录性控制类型', '后端元数据配置中(t_form_fields)的mustInput值参考这里，前后端校验用');
INSERT INTO `t_assistance_type` VALUES ('30', '控件类型', '控件类型-用于描述元数据在前端页面的展示结构,后端元数据配置中(t_form_fields)的ctrlType值参考这里');
INSERT INTO `t_assistance_type` VALUES ('35', '标示字段的引用类型', '标示字段的引用类型，如引用基础资料、辅助资料，基础资料属性等,元数据配置中(t_form_fields)的lookupType值参考这里');

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
  `group` int(11) DEFAULT '0' COMMENT '按钮分组，前端渲染菜单使用\r\n\r\n将功能按钮的group值设置为一样，表名这些功能按钮创建一个按钮组\r\n如将 审核与反审核功能按钮 的group都设置为1，则前端会将这两个按钮创建到一组，下拉方式打开组内按钮。\r\n\r\n当业务单据上的菜单项（按钮组为1项）超过五项后，其他的按钮会统一放在一个按钮组\r\n',
  `icon` varchar(50) DEFAULT '' COMMENT '菜单图标',
  `desc` varchar(255) DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`class_id`,`access_mask`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='\r\n该表对业务类型对应功能进行描述（根据用户的角色类别及角色确定功能可用性），\r\n前端根据此信息生成功能菜单\r\n\r\n如用户有 查看，新增，修改，删除，禁用 五个功能\r\n\r\n对于 系统角色类型 查看，新增，修改，删除，禁用 功能都可用\r\n\r\n对于 医院角色类型 查看，新增，修改，删除，禁用 功能都可用\r\n\r\n对于 供应商角色类型 查看，新增，修改，删除，禁用 功能都可用\r\n\r\n\r\n对于 系统角色类型中的 业务系统管理角色 查看，新增，修改，删除 可用 禁用 功能不可用\r\n\r\n系统预留如下权限掩码，不可暂用,业务特有功能权限掩码从4096开始编码\r\n\r\n查看:1;\r\n新增:2;\r\n修改:4;\r\n 删除:8;\r\n审核:16;\r\n反审核:32;\r\n禁用:64;\r\n反禁用:128;\r\n同步:256;\r\n';

-- ----------------------------
-- Records of t_form_action
-- ----------------------------
INSERT INTO `t_form_action` VALUES ('1001', '查看', 'view', '5', '1', '1', '7', '1', 'icon-chakan', '用户查看权限');
INSERT INTO `t_form_action` VALUES ('1001', '新增', 'edit', '10', '2', '1', '7', '2', 'icon-icon-xinzeng', '用户新增权限');
INSERT INTO `t_form_action` VALUES ('1001', '修改', 'edit', '10', '4', '1', '7', '3', 'icon-xiugaiziliao', '用户修改权限');
INSERT INTO `t_form_action` VALUES ('1001', '删除', 'edit', '10', '8', '1', '7', '3', 'icon-delete', '用户删除权限');
INSERT INTO `t_form_action` VALUES ('1002', '查看', 'view', '5', '1', '1', '7', '1', 'icon-chakan', '供应商资料查看权限');
INSERT INTO `t_form_action` VALUES ('1002', '新增', 'edit', '10', '2', '1', '7', '2', 'icon-icon-xinzeng', '供应商资料新增权限');
INSERT INTO `t_form_action` VALUES ('1002', '修改', 'edit', '10', '4', '1', '7', '3', 'icon-xiugaiziliao', '供应商资料修改权限');
INSERT INTO `t_form_action` VALUES ('1002', '删除', 'edit', '10', '8', '1', '7', '3', 'icon-delete', '供应商资料删除权限');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务定义主表\r\n\r\n每一个业务单据都可以在此定义\r\n\r\n如采购订单，供应商资料，用户资料，物料基础资料等';

-- ----------------------------
-- Records of t_form_class
-- ----------------------------
INSERT INTO `t_form_class` VALUES ('1001', '用户', 't_user', 'id', '用户');
INSERT INTO `t_form_class` VALUES ('1011', '注册供应商', 't_supplier', 'id', '供应商注册用户时的供应商资料');
INSERT INTO `t_form_class` VALUES ('1012', '注册医院', 't_hospital', 'id', '医院注册用户时的医院基本资料');
INSERT INTO `t_form_class` VALUES ('2001', '采购订单', 't_order', 'id', '采购订单');

-- ----------------------------
-- Table structure for t_form_class_entry
-- ----------------------------
DROP TABLE IF EXISTS `t_form_class_entry`;
CREATE TABLE `t_form_class_entry` (
  `class_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '子表名称',
  `entry_index` int(11) NOT NULL COMMENT '子表序号(从1开始)',
  `table_name` varchar(50) NOT NULL DEFAULT '' COMMENT '物理表名',
  `primary_key` varchar(50) NOT NULL DEFAULT '' COMMENT '表主键',
  `foreign_key` varchar(50) NOT NULL DEFAULT '' COMMENT '关联主表字段',
  `join_type` varchar(50) NOT NULL DEFAULT 'INNER JOIN' COMMENT '与主表的连接关系(默认 INNER JOIN)',
  `desc` varchar(255) DEFAULT '',
  PRIMARY KEY (`class_id`,`entry_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务定义附表\r\n\r\n有些业务单据设计上可能一个表不能完全描述，如一张采购订单包括一份唯一的单据头数据，但可以有多份的单据分录数据，\r\n即采购订单与采购订单单据头是1对1关系，采购订单与采购订单分录是1对多关系\r\n\r\n此时对于采购订单的数据库存储上应该有两个表，一个表存储单据头信息，一个表存储单据头信息\r\n\r\n该表描述了一个业务单据的子表信息';

-- ----------------------------
-- Records of t_form_class_entry
-- ----------------------------
INSERT INTO `t_form_class_entry` VALUES ('1001', '用户角色', '1', 't_user_role', 'id', 'user_id', 'INNER JOIN', '用户角色(一个用户可以对应多个角色)');

-- ----------------------------
-- Table structure for t_form_fields
-- ----------------------------
DROP TABLE IF EXISTS `t_form_fields`;
CREATE TABLE `t_form_fields` (
  `class_id` int(11) NOT NULL COMMENT '业务类型id，对应t_form_class表class_id',
  `page` int(4) NOT NULL DEFAULT '0' COMMENT '标示模板字段所在的页面（0是表头，1是第一个子表，2是第二个子表，以此类推...）',
  `key` varchar(30) NOT NULL DEFAULT '' COMMENT '字段唯一标示(用于关联表显示字段名与本表字段名同名的情况，同一个class_id中key是唯一的)',
  `name` varchar(50) NOT NULL COMMENT '字段名(显示的字段名)',
  `sql_column_name` varchar(20) NOT NULL COMMENT '物理表中的字段名',
  `data_type` int(4) NOT NULL COMMENT '字段类型(数字，文本等)1:数字2:文本3:日期4:布尔,用于前端解析',
  `ctrl_type` int(4) NOT NULL COMMENT '控件类型，指示前端展示时的特殊类型，比如多选框，基础资料选择框等',
  `ctrl_index` int(11) NOT NULL DEFAULT '0' COMMENT '字段的后台查询顺序，index是用于前端界面展示时的tab顺序ctrl_index用于后端查询构建查询脚本时模板的遍历顺序，处理因为可能涉及关联其他表查询，连接表时的顺序不可随意改变时产生的错误',
  `index` int(11) DEFAULT '0' COMMENT '前端字段的显示顺序',
  `display` int(1) DEFAULT '1' COMMENT '字段显示性:用于根据角色类别控制字段显示性。\r\n采用三位二进制方式配置\r\n如1(001)表示该字段只有系统用户能可见；2(010)表示该字段只有医院用户可见；3(011)表示该字段系统用户与医院用户都可见\r\n参考t_SubSysEnum,typeID=3',
  `show_width` int(11) DEFAULT '80' COMMENT '字段在前端页面显示的宽度(单位px)',
  `look_up_type` int(11) DEFAULT '0' COMMENT '标示是否是引用类型\r\n1:引用基础资料',
  `look_up_class_id` int(11) DEFAULT '0' COMMENT '如果是引用类型，改字段指示引用类型的业务类型class_id',
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
INSERT INTO `t_form_fields` VALUES ('1001', '0', 'id', '主键', 'id', '1', '1', '0', '0', '0', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '0', '64', '63', '0', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '0', 'is_admin', '是否管理员', 'is_admin', '4', '15', '25', '25', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '1', '63', '0', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '0', 'mobile', '手机号码', 'mobile', '2', '10', '20', '20', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '50', '0', '1', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '0', 'name', '真实姓名', 'name', '2', '10', '15', '15', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '50', '0', '1', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '0', 'org_hospital', '医院', 'org', '4', '6', '35', '35', '455', '80', '1', '1003', 't_hospital', '', 'id', 'name', '', 'LEFT JOIN', 'type=3', null, null, null, '1', '64', '63', '0', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '0', 'org_supplier', '供应商公司', 'org', '4', '6', '30', '30', '63', '80', '1', '1002', 't_supplier', '', 'id', 'name', '', 'LEFT JOIN', 'type=3', null, null, null, '1', '64', '63', '0', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '0', 'password', '密码', 'password', '2', '10', '10', '10', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '50', '0', '1', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '1', 'role_id', '角色', 'role_id', '4', '6', '10', '10', '511', '80', '1', '1004', 't_role', '', 'id', 'name', '', 'INNER JOIN', '', null, null, null, '1', '64', '0', '0', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '1', 'user_id', '用户id', 'user_id', '1', '1', '5', '5', '0', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '64', '63', '0', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '0', 'user_name', '用户名', 'user_name', '2', '10', '5', '5', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '50', '0', '1', '0');
INSERT INTO `t_form_fields` VALUES ('1001', '1', 'user_role', '主键', 'id', '1', '1', '0', '0', '0', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '64', '63', '0', '0');
INSERT INTO `t_form_fields` VALUES ('1011', '0', 'address', '地址', 'address', '2', '10', '25', '25', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '255', '0', '1', '0');
INSERT INTO `t_form_fields` VALUES ('1011', '0', 'business_license', '营业执照号', 'business_license', '2', '10', '15', '15', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '20', '0', '1', '0');
INSERT INTO `t_form_fields` VALUES ('1011', '0', 'id', '主键', 'id', '1', '1', '0', '0', '0', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '0', '64', '63', '0', '0');
INSERT INTO `t_form_fields` VALUES ('1011', '0', 'name', '名称', 'name', '2', '10', '10', '10', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '50', '0', '1', '0');
INSERT INTO `t_form_fields` VALUES ('1011', '0', 'number', '代码', 'number', '2', '10', '5', '5', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '20', '0', '1', '0');
INSERT INTO `t_form_fields` VALUES ('1011', '0', 'tax_id', '税务登记号', 'tax_id', '2', '10', '20', '20', '511', '80', '0', '0', '', '', '', '', '', '', '', null, null, null, '1', '255', '0', '1', '0');

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
INSERT INTO `t_menu` VALUES ('81', '用户', '1', 'icon-yonghutouxiang', 'html/user/supplier/index.html?classId=1001', '8', '用户', '');
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
INSERT INTO `t_supplier` VALUES ('1', 'GZ-KF', '广东康发有限公司', '12345678908765', '广州市天河区', '12313121241');

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
  `is_admin` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为管理员(1:管理员2:非管理员)\r\n\r\n注册用户都是管理员',
  `org` bigint(64) NOT NULL COMMENT '关联组织\r\n对于供应商用户此字段记录t_supplier供应商资料id\r\n对于医院用户此字段记录t_hospital医院资料id\r\n对于系统用户改字段为0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'yadda', '111', 'bcbe3365e6ac95ea2c0343a2395834dd', '15212345678', '1', '', '0');
INSERT INTO `t_user` VALUES ('2', '康发公司', '222', 'bcbe3365e6ac95ea2c0343a2395834dd', '15212345678', '3', '', '1');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `user_id` bigint(64) NOT NULL COMMENT '用户id',
  `role_id` bigint(64) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表\r\n\r\n一个用户可以对应多个角色\r\n一个角色可以对应多个用户';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
