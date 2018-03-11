package com.kingdee.hrp.sms.common.service.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingdee.hrp.sms.common.pojo.Condition;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用于生产服务中所需的所有插件，并统一提供所有插件的方法调用
 *
 * @author yadda
 * @date 2018-02-27 17:31:28 星期四
 */
@Service
public class PlugInFactory implements IPlugIn, InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 存放所有的插件序列
     */
    private List<IPlugIn> plugIns = new ArrayList<IPlugIn>();

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

        // 反射
        for (String name : names) {

            type = applicationContext.getType(name);

            if (IPlugIn.class.isAssignableFrom(type)) {
                plugIns.add(applicationContext.getBean(name, IPlugIn.class));
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public PlugInFactory() {
    }

    /**
     * 获取当前插件支持的业务类型集合
     *
     * @return 插件支持的业务类型classId集合
     */
    @Override
    public Set<Integer> getClassId() {
        return null;
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
    public PlugInRet beforeSave(int classId, Map<String, Object> formTemplate, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.beforeSave(classId, formTemplate, data);
                if (plugInRet.getCode() != 200) {
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
     * @return
     */
    @Override
    public PlugInRet afterSave(int classId, String id, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.afterSave(classId, id, data);
                if (plugInRet.getCode() != 200) {
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
     * @param id
     * @param formTemplate
     * @param data         业务数据  @return
     */
    @Override
    public PlugInRet beforeModify(int classId, String id, Map<String, Object> formTemplate, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.beforeModify(classId, id, formTemplate, data);
                if (plugInRet.getCode() != 200) {
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
     * @Title beforeEntryModify
     * @date 2017-07-12 09:05:42 星期三
     */
    @Override
    public PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, Map<String, Object> formTemplate, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.beforeEntryModify(classId, primaryId, entryId, formTemplate, data);
                if (plugInRet.getCode() != 200) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 基础资料修改后操作
     *
     * @param classId 业务类型
     * @param data    业务数据
     * @return
     */
    @Override
    public PlugInRet afterModify(int classId, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.afterModify(classId, data);
                if (plugInRet.getCode() != 200) {
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
     * @param formTemplate
     * @param data         删除的内码集合  @return
     */
    @Override
    public PlugInRet beforeDelete(int classId, Map<String, Object> formTemplate, JsonNode data) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.beforeDelete(classId, formTemplate, data);
                if (plugInRet.getCode() != 200) {
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
     * @Title beforeentryDelete
     * @date 2017-07-12 09:10:46 星期三
     */
    @Override
    public PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId, Map<String, Object> formTemplate) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.beforeEntryDelete(classId, primaryId, entryId, formTemplate);
                if (plugInRet.getCode() != 200) {
                    // 插件返回了阻止继续运行的情况--返回不继续执行后续插件
                    return plugInRet;
                }
            }
        }

        return plugInRet;
    }

    /**
     * 基础资料删除后操作
     *
     * @param classId 业务类型
     * @param delData 待删除的数据明细集合
     * @param items   删除的内码集合
     * @return
     */
    @Override
    public PlugInRet afterDelete(int classId, List<Map<String, Object>> delData, String items) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.afterDelete(classId, delData, items);
                if (plugInRet.getCode() != 200) {
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
     * @return
     */
    @Override
    public PlugInRet beforeQuery(int classId, Map<String, Object> formTemplate, List<Condition> conditons) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.beforeQuery(classId, formTemplate, conditons);
                if (plugInRet.getCode() != 200) {
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
     * @return
     */
    @Override
    public PlugInRet afterQuery(int classId, List<Map<String, Object>> list) {

        PlugInRet plugInRet = new PlugInRet();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {
                plugInRet = plugIn.afterQuery(classId, list);
                if (plugInRet.getCode() != 200) {
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
     * @param conditons    原始过滤条件
     * @return 插件过滤条件
     */
    @Override
    public List<Condition> getConditions(int classId, Map<String, Object> formTemplate, List<Condition> conditons) {

        List<Condition> pluginConditions = new ArrayList<Condition>();

        for (IPlugIn plugIn : plugIns) {
            if (plugIn.getClassId() != null && plugIn.getClassId().contains(classId)) {

                List<Condition> c = plugIn.getConditions(classId, formTemplate, conditons);
                if (c != null && c.size() > 0) {
                    pluginConditions.addAll(c);
                }
            }
        }

        return pluginConditions;
    }
}
