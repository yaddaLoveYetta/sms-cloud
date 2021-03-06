package com.kingdee.hrp.sms.common.service.plugin.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;
import com.kingdee.hrp.sms.common.dao.generate.UserMapper;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.User;
import com.kingdee.hrp.sms.common.model.UserExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.service.plugin.AbstractPlugInAdapter;
import com.kingdee.hrp.sms.common.service.plugin.MustInputCheckResult;
import com.kingdee.hrp.sms.common.service.plugin.PlugInRet;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 基础资料通用插件，主要根据用户组织类别做数据隔离
 *
 * @author yadda
 */
@Component
public class BaseDataPlugin extends AbstractPlugInAdapter implements InitializingBean {

    /**
     * 插件支持的业务类别id集合，推荐的做法是一个插件只支持一个业务类别
     * 如只支持订单业务
     */
    private Set<Integer> classIdSet;

    /**
     * 插件名称
     *
     * @return 插件名称
     */
    @Override
    public String name() {
        return "基础资料插件";
    }

    /**
     * 插件说明</br>
     * 简单介绍下插件做了什么事情
     *
     * @return 插件说明
     */
    @Override
    public String description() {
        return "主要实现getConditions处理用户之间的数据隔离";
    }

    /**
     * 插件序号-同一个业务上绑定多插件时确定插件的执行顺序
     *
     * @return 插件序号，值越小越先执行
     */
    @Override
    public Integer index() {
        return 1;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        classIdSet = new HashSet<>();
        // 用户
        classIdSet.add(Constants.ClassType.USER.classId());
        // 角色
        classIdSet.add(Constants.ClassType.ROLE.classId());
        // 供应商类别
        classIdSet.add(Constants.ClassType.SUPPLIER_TYPE.classId());
        //医院供应商
        classIdSet.add(Constants.ClassType.HOSPITAL_SUPPLIER.classId());
        // 医院物料
        classIdSet.add(Constants.ClassType.ITEM.classId());
        // 单位
        classIdSet.add(Constants.ClassType.UNIT.classId());
        // 职员
        classIdSet.add(Constants.ClassType.EMP.classId());
    }

    /**
     * 获取当前插件支持的业务类型集合
     *
     * @return 插件支持的业务类型classId集合
     */
    @Override
    public Set<Integer> getSupports() {
        return classIdSet;
    }

    /**
     * 插件是否支持指定的业务单据
     *
     * @param classId 业务单据类型
     * @return true if support , false not support
     */
    @Override
    public boolean support(Integer classId) {
        return classIdSet.contains(classId);
    }

    /**
     * 禁用/反禁用后置事件（基础资料用）
     *
     * @param classId     业务类别
     * @param template    单据模板
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return PlugInRet
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlugInRet afterForbid(Integer classId, FormTemplate template, List<Long> ids, Integer operateType) {

        if (classId == Constants.ClassType.USER.classId() && operateType == 1) {
            // 禁用用户时，如果该用户是医院/供应商的管理员,则将归属该医院、供应商的所有用户一并禁用
            SqlSession sqlSession = Environ.getBean(SqlSession.class);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andIdIn(ids);
            List<User> users = userMapper.selectByExample(userExample);

            List<Long> adminUserOrgIds = new ArrayList<>();
            for (User user : users) {
                if (user.getIsAdmin() && user.getOrg() > 0) {
                    //user.getOrg() > 0 不是系统用户
                    adminUserOrgIds.add(user.getOrg());
                }
            }

            if (!adminUserOrgIds.isEmpty()) {
                User forbidUser = new User();
                forbidUser.setStatus(true);
                userExample.clear();
                criteria = userExample.createCriteria();
                criteria.andOrgIn(adminUserOrgIds);
                userMapper.updateByExampleSelective(forbidUser, userExample);
            }
        }

        return super.afterForbid(classId, template, ids, operateType);
    }

    /**
     * 基础资料新增前操作
     * <p>
     * 主要是补充一些不需要前端传输但又必须有的信息，如资料归属，类别，分录行号等字段。
     * 进行单据数据必录性，合法性校验等
     *
     * @param classId      业务类型
     * @param formTemplate 单据模板
     * @param data         前端提交的单据数据
     * @return PlugInRet
     */
    @Override
    @SuppressWarnings("unchecked")
    public PlugInRet beforeSave(int classId, FormTemplate formTemplate, JsonNode data) {

        // 数据处理，如补充一些不需要前端传输但又必须有的信息
        dataProcess(classId, formTemplate, data);

        // 通用必录性校验(按模板校验)
        MustInputCheckResult checkResult = mustInputCheck(formTemplate, data);

        if (!checkResult.isHeadCheckSuccess()) {
            // 单据头必须性校验不通过
            // 单据头必录校验有错误
            throw new BusinessLogicRunTimeException(Joiner.on(System.getProperty("line.separator")).join(checkResult.getHeadCheckError()));
        }

        if (!checkResult.isBodyCheckSuccess()) {
            // 单据头必须性校验不通过
            // 单据体必录校验有错误
            throw new BusinessLogicRunTimeException(Joiner.on(System.getProperty("line.separator")).join(checkResult.getBodyCheckError()));
        }

        return super.beforeSave(classId, formTemplate, data);

    }

    /**
     * 查询前插件查询条件
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditions   原始过滤条件
     * @return 插件过滤条件
     */
    @Override
    public List<Condition> getConditions(int classId, FormTemplate formTemplate, List<Condition> conditions) {

        List<Condition> ret = new ArrayList<Condition>();

        Constants.UserRoleType userRoleType = getCurrentUserRoleType();
        Long linkOrg = getCurrentUserLinkOrg();

        if (userRoleType == Constants.UserRoleType.SYSTEM) {
            // 系统角色类别放开所有数据查看权限
            return ret;
        }

        if (userRoleType == Constants.UserRoleType.HOSPITAL) {
            // 当前是医院角色的用户在操作

            if (classId == Constants.ClassType.USER.classId() || classId == Constants.ClassType.ROLE.classId()) {
                // 用户、角色
                Condition condition = new Condition();
                condition.setLinkType(Condition.LinkType.AND);
                condition.setFieldKey("org_hospital");
                condition.setLogicOperator(Condition.LogicOperator.EQUAL);
                condition.setValue(linkOrg);
                condition.setNeedConvert(false);
                ret.add(condition);
            }

            if (classId == Constants.ClassType.SUPPLIER_TYPE.classId() || classId == Constants.ClassType.HOSPITAL_SUPPLIER.classId() ||
                    classId == Constants.ClassType.ITEM.classId()) {
                // 供应商类别、医院供应商
                Condition condition = new Condition();
                condition.setLinkType(Condition.LinkType.AND);
                condition.setFieldKey("org");
                condition.setLogicOperator(Condition.LogicOperator.EQUAL);
                condition.setValue(linkOrg);
                condition.setNeedConvert(false);
                ret.add(condition);
            }

        }

        if (userRoleType == Constants.UserRoleType.SUPPLIER) {
            // 当前是供应商角色的用户在操作

            if (classId == Constants.ClassType.USER.classId() || classId == Constants.ClassType.ROLE.classId()) {
                // 用户、角色
                Condition condition = new Condition();
                condition.setLinkType(Condition.LinkType.AND);
                condition.setFieldKey("org_supplier");
                condition.setLogicOperator(Condition.LogicOperator.EQUAL);
                condition.setValue(linkOrg);
                condition.setNeedConvert(false);
                ret.add(condition);
            }

        }

        return ret;

    }

    /**
     * 单据新增保存前数据处理
     *
     * @param classId      业务类型
     * @param formTemplate 单据模板
     * @param data         前端提交的单据数据
     */
    private void dataProcess(int classId, FormTemplate formTemplate, JsonNode data) {

        Constants.UserRoleType userRoleType = SessionUtil.getUserRoleType();

        if (classId == Constants.ClassType.EMP.classId() || classId == Constants.ClassType.UNIT.classId()) {
            // 单位，职员新增时，设置归属医院
            if (SessionUtil.getUserLinkHospital() == -1) {
                throw new BusinessLogicRunTimeException("当前登录用户非医院用户，不能进行此操作!");
            }

            ((ObjectNode) data).put("org", SessionUtil.getUserLinkHospital());
        }

        if (userRoleType != Constants.UserRoleType.SYSTEM && classId == Constants.ClassType.ROLE.classId()) {
            // 非系统管理员操作角色新增功能时，
            // 新增的角色类别与当前登录用户类别相同(即医院只能新增医院角色，供应商只能新增供应商角色)
            ((ObjectNode) data).put("type", SessionUtil.getUserRoleTypeNumber());

            if (userRoleType == Constants.UserRoleType.HOSPITAL) {
                // 医院新增角色--绑定新增的角色所属医院
                ((ObjectNode) data).put("org_hospital", SessionUtil.getUserLinkHospital());
            }

            if (userRoleType == Constants.UserRoleType.SUPPLIER) {
                // 供应商新增角色--绑定新增的角色所属供应商
                ((ObjectNode) data).put("org_supplier", SessionUtil.getUserLinkSupplier());
            }

        }
    }

}
