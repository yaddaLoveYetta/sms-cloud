package com.kingdee.hrp.sms.common.service.plugin.impl;

import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.service.ITemplateService;
import com.kingdee.hrp.sms.common.service.plugin.PlugInAdpter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

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
     * 查询前插件查询条件
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditons    原始过滤条件
     * @return 插件过滤条件
     */
    @Override
    public List<Condition> getConditions(int classId, Map<String, Object> formTemplate, List<Condition> conditons) {

        List<Condition> ret = new ArrayList<Condition>();

        if (classId == 1001) {
            Condition condition = new Condition();
            condition.setLinkType(Condition.LinkTypeEnum.AND);
            condition.setFieldKey("name");
            condition.setLogicOperator(Condition.LogicOperatorEnum.EQUAL);
            condition.setValue("yadda");
            ret.add(condition);
        }
        return ret;
    }


}
