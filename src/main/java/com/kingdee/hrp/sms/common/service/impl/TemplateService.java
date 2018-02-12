package com.kingdee.hrp.sms.common.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.FormActionMapper;
import com.kingdee.hrp.sms.common.model.FormAction;
import com.kingdee.hrp.sms.common.model.FormActionExample;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.ITemplateService;
import org.springframework.stereotype.Service;

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
    public Map<String, Object> getFormTemplate(Integer classId) {
        return null;
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
