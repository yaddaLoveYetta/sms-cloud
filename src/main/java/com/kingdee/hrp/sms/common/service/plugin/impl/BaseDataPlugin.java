package com.kingdee.hrp.sms.common.service.plugin.impl;

import com.kingdee.hrp.sms.common.dao.generate.UserMapper;
import com.kingdee.hrp.sms.common.model.User;
import com.kingdee.hrp.sms.common.model.UserExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.service.ITemplateService;
import com.kingdee.hrp.sms.common.service.plugin.PlugInAdpter;
import com.kingdee.hrp.sms.common.service.plugin.PlugInRet;
import com.kingdee.hrp.sms.util.Environ;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yadda
 */
@Service
public class BaseDataPlugin extends PlugInAdpter implements InitializingBean {

    @Resource
    private ITemplateService templateService;

    private Set<Integer> classId;


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
        classId = new HashSet<>();
        // 用户
        classId.add(1001);
        classId.add(1002);
    }

    /**
     * 获取当前插件支持的业务类型集合
     *
     * @return 插件支持的业务类型classId集合
     */
    @Override
    public Set<Integer> getClassId() {
        return classId;
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
    public PlugInRet afterForbid(Integer classId, Map<String, Object> template, List<Long> ids, Integer operateType) {

        if (classId == 1001) {
            // 禁用用户时，如果该用户是医院/供应商的管理员,则将归属该医院、供应商的所有用户一并禁用
            SqlSession sqlSession = Environ.getBean(SqlSession.class);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andIdIn(ids);
            List<User> users = userMapper.selectByExample(userExample);

            List<Long> adminUserIds = new ArrayList<>();
            for (User user : users) {
                if (user.getIsAdmin() && user.getOrg() > 0) {
                    //user.getOrg() > 0 不是系统用户
                    adminUserIds.add(user.getId());
                }
            }

            if (!adminUserIds.isEmpty()) {
                User forbidUser = new User();
                forbidUser.setStatus(true);
                userExample.clear();
                criteria.andIdIn(adminUserIds);
                userMapper.updateByExampleSelective(forbidUser, userExample);
            }
        }

        return super.afterForbid(classId, template, ids, operateType);
    }

    /**
     * 查询前插件查询条件
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditons    原始过滤条件
     * @return 插件过滤条件
     */
    @Override
    public List<Condition> getConditions(int classId, Map<String, Object> formTemplate, List<Condition> conditons) {

/*        // test
        List<Condition> ret = new ArrayList<Condition>();

        if (classId == 1001) {
            Condition condition = new Condition();
            condition.setLinkType(Condition.LinkTypeEnum.AND);
            condition.setFieldKey("name");
            condition.setLogicOperator(Condition.LogicOperatorEnum.EQUAL);
            condition.setValue("yadda");
            ret.add(condition);
        }
        return ret;*/

        return null;
    }


}
