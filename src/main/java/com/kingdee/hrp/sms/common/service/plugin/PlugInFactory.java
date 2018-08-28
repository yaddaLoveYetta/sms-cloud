package com.kingdee.hrp.sms.common.service.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.pojo.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 用于生产服务中所需的所有插件，并统一提供所有插件的方法调用
 *
 * @author yadda
 * @date 2018-02-27 17:31:28 星期四
 */
@Service(value = "plugInFactory")
public class PlugInFactory implements PlugIn, InitializingBean, ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(PlugInFactory.class);

    /**
     * spring context
     */
    private ApplicationContext applicationContext;

    /**
     * 存放所有的插件序列
     */
    private static List<PlugIn> plugIns = new ArrayList<PlugIn>();

    /**
     * 1. 实例化()构造函数;
     * 2. 设置属性值(set);
     * 3. 如果实现了BeanNameAware接口,调用setBeanName设置Bean的ID或者Name;
     * 4. 如果实现BeanFactoryAware接口,调用setBeanFactory 设置BeanFactory;
     * 5. 如果实现ApplicationContextAware,调用setApplicationContext设置ApplicationContext
     * 6. 调用BeanPostProcessor的预先初始化方法;
     * 7. 调用InitializingBean的afterPropertiesSet()方法;
     * 8. 调用定制init-method方法；
     * 9. 调用BeanPostProcessor的后初始化方法;
     */
    @Override
    public void afterPropertiesSet() {

        // spring ioc容器中所有bean
        String[] names = applicationContext.getBeanDefinitionNames();

        Class<?> type;

        synchronized (PlugInFactory.class) {

            for (String name : names) {

                type = applicationContext.getType(name);

                if (PlugInFactory.class.isAssignableFrom(type)) {
                    // 为了规范PlugInFactory调用插件的逻辑，PlugInFactory也实现了IPlugIn接口
                    // 但PlugInFactory不是业务插件,不将其放入插件列表
                    continue;
                }

                if (PlugIn.class.isAssignableFrom(type)) {

                    PlugIn plugIn = applicationContext.getBean(name, PlugIn.class);

                    if (!plugIns.contains(plugIn)) {
                        logger.info("add a new plugin:" + plugIn.name());
                        plugIns.add(plugIn);
                    }
                }
            }

            // 插件执行顺序排序
            plugIns.sort(Comparator.comparing(PlugIn::index));

            logger.info(String.format("init %s plugin total", plugIns.size()));

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public PlugInFactory() {
    }

    /**
     * 插件名称
     *
     * @return 插件名称
     */
    @Override
    public String name() {
        return null;
    }

    /**
     * 插件说明</br>
     * 简单介绍下插件做了什么事情
     *
     * @return 插件说明
     */
    @Override
    public String description() {
        return null;
    }

    /**
     * 插件序号-同一个业务上绑定多插件时确定插件的执行顺序
     *
     * @return 插件序号，值越小越先执行
     */
    @Override
    public Integer index() {
        return 0;
    }

    /**
     * 获取当前插件支持的业务类型集合
     *
     * @return 插件支持的业务类型classId集合
     */
    @Override
    public Set<Integer> getSupports() {
        return new HashSet<>(1);
    }

    /**
     * 插件是否支持指定的业务单据
     *
     * @param classId 业务单据类型
     * @return true if support , false not support
     */
    @Override
    public boolean support(Integer classId) {
        return false;
    }

    /**
     * 基础资料新增前操作
     *
     * @param classId      业务类型
     * @param formTemplate 单据模板
     * @param data         业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeSave(int classId, FormTemplate formTemplate, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.beforeSave(classId, formTemplate, data);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 基础资料新增后操作
     *
     * @param classId 业务类型
     * @param id      新增的资料内码
     * @param data    业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterSave(int classId, Long id, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.afterSave(classId, id, data);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 基础资料修改前操作
     *
     * @param classId      业务类型
     * @param id           主键
     * @param formTemplate 模板数据
     * @param data         业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeModify(int classId, Long id, FormTemplate formTemplate, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.beforeModify(classId, id, formTemplate, data);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 单据/基础资料修改分录数据前事件
     *
     * @param classId      事务类型
     * @param primaryId    主表内码
     * @param entryId      子表内码
     * @param formTemplate 模板数据
     * @param data         修改数据内容
     * @return PlugInRet
     * @date 2017-07-12 09:05:42 星期三
     */
    @Override
    public PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, FormTemplate formTemplate,
            JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.beforeEntryModify(classId, primaryId, entryId, formTemplate, data);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 单据修改后操作
     *
     * @param classId      业务类型
     * @param id           单据内码
     * @param formTemplate 模板
     * @param data         业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterModify(int classId, Long id, FormTemplate formTemplate, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.afterModify(classId, id, formTemplate, data);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 基础资料删除前操作
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeDelete(int classId, FormTemplate formTemplate, List<Long> ids) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.beforeDelete(classId, formTemplate, ids);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 单据/基础资料分录删除前事件
     *
     * @param classId      事务类型
     * @param primaryId    主表内码
     * @param entryId      子表内码
     * @param formTemplate 模板数据
     * @return PlugInRet
     * @date 2017-07-12 09:10:46 星期三
     */
    @Override
    public PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId,
            FormTemplate formTemplate) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.beforeEntryDelete(classId, primaryId, entryId, formTemplate);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 删除后操作
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterDelete(int classId, FormTemplate formTemplate, List<Long> ids) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.afterDelete(classId, formTemplate, ids);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 基础资料查询前操作
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditons    原始过滤条件
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeQuery(int classId, FormTemplate formTemplate, List<Condition> conditons) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.beforeQuery(classId, formTemplate, conditons);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 基础资料查询后操作
     *
     * @param classId 业务类型
     * @param list    查询结果集
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterQuery(int classId, List<Map<String, Object>> list) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.afterQuery(classId, list);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
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

        List<Condition> pluginConditions = new ArrayList<Condition>();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {

                List<Condition> c = plugIn.getConditions(classId, formTemplate, conditions);
                if (!CollectionUtils.isEmpty(c)) {
                    pluginConditions.addAll(c);
                }
            }
        }

        return pluginConditions;
    }

    /**
     * 禁用/反禁用前置事件（基础资料用）
     *
     * @param classId     业务类别
     * @param template    单据模板
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeForbid(Integer classId, FormTemplate template, List<Long> ids, Integer operateType) {

        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.beforeForbid(classId, template, ids, operateType);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;

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
    public PlugInRet afterForbid(Integer classId, FormTemplate template, List<Long> ids, Integer operateType) {
        PlugInRet plugInRet = new PlugInRet();

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {
                plugInRet = plugIn.afterForbid(classId, template, ids, operateType);
                if (plugInRet.getCode() != StatusCode.SUCCESS) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 审核/反审核时获取审核状态的字段模板key
     * <p>
     * 审核时系统设置该字段值为1，反审核时设置字段值为0
     *
     * @param classId 业务类型
     * @return 审核状态字段key
     */
    @Override
    public String checkFieldKey(Integer classId) {

        String key = null;

        for (PlugIn plugIn : plugIns) {
            if (plugIn.getSupports() != null && plugIn.support(classId)) {

                String temp = plugIn.checkFieldKey(classId);

                if (null != temp) {
                    key = temp;
                }
            }
        }

        return key;

    }
}
