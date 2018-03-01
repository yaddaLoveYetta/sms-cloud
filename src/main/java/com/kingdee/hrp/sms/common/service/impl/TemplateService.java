package com.kingdee.hrp.sms.common.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.FormActionMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormClassEntryMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormClassMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormFieldsMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.ITemplateService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateService extends BaseService implements ITemplateService {
    /**
     * 查询基础资料/单据模板数据
     *
     * @param classId
     */
    @Override
    public Map<String, Object> getFormTemplate(Integer classId, Integer type) {

        Map<String, Object> retMap = new LinkedHashMap<String, Object>();

        // 主表模板
        Map<String, Object> formFields = new LinkedHashMap<String, Object>();
        // 子表模板
        Map<String, Object> formEntry = new LinkedHashMap<String, Object>();

        // 获取单据类别描述信息
        FormClassMapper classMapper = sqlSession.getMapper(FormClassMapper.class);

        // 主表单据类别描述
        FormClass formClass = classMapper.selectByPrimaryKey(classId);

        if (null == formClass) {
            throw new BusinessLogicRunTimeException("模板不存在或不唯一,请联系管理员!");
        }


        // 获取单据模板page=0表头

        FormFieldsMapper formFieldsMapper = sqlSession.getMapper(FormFieldsMapper.class);

        FormFieldsExample fieldsExample = new FormFieldsExample();
        FormFieldsExample.Criteria fieldsCriteria = fieldsExample.createCriteria();

        fieldsCriteria.andClassIdEqualTo(classId);
        fieldsCriteria.andPageEqualTo(0);

        if (type == 1) {
            // 后端构建查询脚本时调用
            fieldsExample.setOrderByClause("page, ctlIndex");

        } else {
            // 前端处理显示顺序时调用
            fieldsExample.setOrderByClause("page, [index]");
        }

        List<FormFields> headFields = formFieldsMapper.selectByExample(fieldsExample);

        // 子表单据类别描述
        FormClassEntryMapper classEntryMapper = sqlSession.getMapper(FormClassEntryMapper.class);
        FormClassEntryExample classEntryExample = new FormClassEntryExample();
        FormClassEntryExample.Criteria entryCriteria = classEntryExample.createCriteria();

        entryCriteria.andClassIdEqualTo(classId);
        List<FormClassEntry> formClassEntries = classEntryMapper.selectByExample(classEntryExample);

        // 打包表头字段模板
        Map<String, FormFields> formFields0 = new LinkedHashMap<String, FormFields>();
        for (FormFields item : headFields) {
            formFields0.put(item.getKey(), item);
        }
        // 表头模板
        formFields.put("0", formFields0);

        // 循环打包子表字段模板
        for (FormClassEntry entry : formClassEntries) {

            String entryIndex = entry.getEntryIndex().toString();

            formEntry.put(entryIndex, entry);

            // 查询子表字段模板(按子表page)
            FormFieldsExample formFieldsExample = new FormFieldsExample();
            FormFieldsExample.Criteria formFieldsExampleCriteria = formFieldsExample.createCriteria();

            formFieldsExampleCriteria.andClassIdEqualTo(classId);
            formFieldsExampleCriteria.andPageEqualTo(entry.getEntryIndex());

            List<FormFields> entryIndexFieldsByExample = formFieldsMapper.selectByExample(formFieldsExample);

            Map<String, FormFields> formFieldsEntryIndex = new LinkedHashMap<String, FormFields>();
            // 打包子表字段模板
            for (FormFields fields : entryIndexFieldsByExample) {
                formFieldsEntryIndex.put(fields.getKey(), fields);
            }

            // 第entryIndex个子表模板
            formFields.put(entryIndex, formFieldsEntryIndex);
        }

        retMap.put("formClass", formClass);
        retMap.put("formClassEntry", formEntry);
        retMap.put("formFields", formFields);

        return retMap;


    }

    /**
     * 获取基础资料/单据定义的功能操作列表
     *
     * @param classId 业务类型
     * @return 功能操作列表
     */
    @Override
    public List getFormAction(Integer classId) {

        FormActionMapper formActionMapper = sqlSession.getMapper(FormActionMapper.class);

        FormActionExample formActionExample = new FormActionExample();

        FormActionExample.Criteria criteria = formActionExample.createCriteria();

        criteria.andClassIdEqualTo(classId);
        // 功能按钮按照index排序
        formActionExample.setOrderByClause("`index`  ,`name` ");

        List<FormAction> formActions = formActionMapper.selectByExample(formActionExample);

        return formActions;
    }

    /**
     * 通过模板获取业务数据
     *
     * @param classId   业务类型
     * @param condition 过滤条件（json结构化数据）
     * @param orderBy   排序条件（json结构化数据）
     * @param pageSize  分页大小
     * @param pageNo    当前页码
     */
    @Override
    public Map<String, Object> getItems(Integer classId, String condition, String orderBy, Integer pageSize, Integer pageNo) {
        return null;
    }

    /**
     * 通过内码获取单个业务类型数据
     *
     * @param classId 业务类型
     * @param id      单据内码
     * @param orderBy 排序结构(json 结构化数据) 查询单据时，单据分录需要排序
     */
    @Override
    public Map<String, Object> getItemById(Integer classId, Long id, String orderBy) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param classId 业务类别
     * @param data    数据（严格按照单据模板匹配的数据）
     * @return 新增资料的id
     */
    @Override
    public Long addItem(Integer classId, String data) {
        return null;
    }

    /**
     * 修改业务数据
     *
     * @param classId 业务类型
     * @param id      内码
     * @param data    修改数据内容（严格按照单据模板匹配的数据）
     * @return 是否成功
     */
    @Override
    public Boolean editItem(Integer classId, Long id, String data) {
        return null;
    }

    /**
     * 删除基础资料
     *
     * @param classId 业务类型
     * @param ids     删除的内码集合
     * @return 是否成功
     */
    @Override
    public Boolean delItem(Integer classId, List<Long> ids) {
        return null;
    }

    /**
     * 审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */
    @Override
    public Boolean checkItem(Integer classId, List<Long> ids) {
        return null;
    }

    /**
     * 反审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */
    @Override
    public Boolean unCheckItem(Integer classId, List<Long> ids) {
        return null;
    }
}
