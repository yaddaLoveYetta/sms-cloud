package com.kingdee.hrp.sms.common.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.common.service.ITemplateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 单据模板操作
 *
 * @author yadda
 */
@Controller
@RequestMapping(value = "/template/")
public class TemplateController {

    @Resource
    private ITemplateService templateService;

    /**
     * 查询单据模板数据
     */
    @RequestMapping(value = "getFormTemplate")
    @ResponseBody
    public Map<String, Object> getFormTemplate(Integer classId) {

        if (classId < 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        return templateService.getFormTemplate(classId, 0);

    }

    /**
     * 获取基础资料/单据定义的功能操作列表
     *
     * @param classId 业务类型
     * @return 功能操作列表
     */

    @RequestMapping(value = "getFormAction")
    @ResponseBody
    public List getFormAction(Integer classId, @RequestParam(defaultValue = "0") Integer type) {

        //0:查看(列表)1:(新增)2:(编辑)
        if (type < 0 || type > 2) {
            throw new BusinessLogicRunTimeException("type类型错误!");
        }
        return templateService.getFormAction(classId, type);
    }

    /**
     * 通过模板获取业务数据
     *
     * @param classId   业务类型
     * @param condition 过滤条件（json结构化数据）
     * @param sort      排序条件（json结构化数据）
     * @param pageSize  分页大小
     * @param pageNo    当前页码
     */
    @RequestMapping(value = "getItems")
    @ResponseBody
    public Map<String, Object> getItems(@RequestParam(defaultValue = "0") Integer classId, String condition, String sort, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNo) throws IOException {


        if (classId < 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        List<Condition> conditions = new ArrayList<Condition>();
        List<Sort> sorts = new ArrayList<Sort>();

        // 包装查询条件-方便操作
        if (StringUtils.isNotBlank(condition)) {
            conditions = stringToList(condition, Condition.class);
        }
        // 包装查询结果排序-方便操作
        if (StringUtils.isNotBlank(sort)) {
            sorts = stringToList(sort, Sort.class);
        }

        return templateService.getItems(classId, conditions, sorts, pageSize, pageNo);

    }

    /**
     * 通过内码获取单个业务类型数据
     *
     * @param classId 业务类型
     * @param id      单据内码
     * @param sort    排序结构(json 结构化数据) 查询单据时，单据分录需要排序
     */
    @ResponseBody
    @RequestMapping(value = "getItemById")
    public Map<String, Object> getItemById(Integer classId, Long id, String sort) throws IOException {

        if (classId < 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        List<Sort> sorts = new ArrayList<Sort>();

        // 包装查询结果排序-方便操作
        if (null != sort && !"".equals(sort)) {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Sort.class);
            sorts = objectMapper.readValue(sort, javaType);
        }

        return templateService.getItemById(classId, id, sorts);

    }

    /**
     * 新增数据
     *
     * @param classId 业务类别
     * @param data    数据（严格按照单据模板匹配的数据）
     * @return 新增资料的id
     */
    @ResponseBody
    @RequestMapping(value = "addItem")
    public Long addItem(Integer classId, String data) throws IOException {

        if (classId < 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        if ("".equals(data.trim())) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交data");
        }

        return templateService.addItem(classId, data);

    }


    /**
     * 修改业务数据
     *
     * @param classId 业务类型
     * @param id      内码
     * @param data    修改数据内容（严格按照单据模板匹配的数据）
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "editItem")
    public Boolean editItem(Integer classId, Long id, String data) throws IOException {

        if (classId <= 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        if (id <= 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交id");
        }

        if ("".equals(data.trim())) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交data");
        }

        return templateService.editItem(classId, id, data);
    }

    /**
     * 删除基础资料
     *
     * @param classId 业务类型
     * @param items   删除的内码集合
     * @return 是否成功
     */

    @ResponseBody
    @RequestMapping(value = "delItem")
    public Boolean delItem(Integer classId, String items) {

        if (classId <= 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId!");
        }

        if (!StringUtils.isNotBlank(items)) {
            throw new BusinessLogicRunTimeException("参数错误：必须指定删除的项!");
        }

        List<Long> ids = stringToList(items, Long.class);

        return templateService.delItem(classId, ids);
    }


    @ResponseBody
    @RequestMapping(value = "forbid")
    public Boolean forbid(Integer classId, String items, Integer operateType) {

        if (classId <= 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId!");
        }

        if (!StringUtils.isNotBlank(items)) {
            throw new BusinessLogicRunTimeException("参数错误：必须指定操作的项!");
        }

        List<Long> ids = stringToList(items, Long.class);

        return templateService.forbid(classId, ids, operateType);

    }

    /**
     * 审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */

    @ResponseBody
    @RequestMapping(value = "check")
    public Boolean check(Integer classId, List<Long> ids) {
        return null;
    }

    /**
     * 反审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "unCheck")
    public Boolean unCheck(Integer classId, List<Long> ids) {
        return null;
    }

    /**
     * 将String转成List
     *
     * @param str 待转化字符串，必须符合list格式
     * @param t   List泛型类型
     * @param <T> List<T>
     * @return List<T>
     * @throws IOException
     */
    private <T> List<T> stringToList(String str, Class<T> t) {

        List<T> target = new ArrayList<T>();

        if (!StringUtils.isNotBlank(str)) {
            return new ArrayList<T>();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, t);
            target = objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }

        return target;
    }
}
