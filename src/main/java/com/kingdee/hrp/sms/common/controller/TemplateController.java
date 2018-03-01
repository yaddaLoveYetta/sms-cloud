package com.kingdee.hrp.sms.common.controller;

import com.kingdee.hrp.sms.common.domain.StatusCode;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.service.ITemplateService;
import com.sun.tools.corba.se.idl.constExpr.BooleanAnd;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    public Map<String, Object> getFormTemplate(Integer classId) {

        if (classId < 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        return null;

    }

    /**
     * 获取基础资料/单据定义的功能操作列表
     *
     * @param classId 业务类型
     * @return 功能操作列表
     */

    @RequestMapping(value = "getFormAction")
    @ResponseBody
    public List getFormAction(Integer classId) {
        return templateService.getFormAction(classId);
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


    @RequestMapping(value = "getItems")
    public Map<String, Object> getItems(Integer classId, String condition, String orderBy, Integer pageSize, Integer pageNo) {


        if (classId < 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        return null;

    }

    /**
     * 通过内码获取单个业务类型数据
     *
     * @param classId 业务类型
     * @param id      单据内码
     * @param orderBy 排序结构(json 结构化数据) 查询单据时，单据分录需要排序
     */


    @RequestMapping(value = "getItemById")
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


    @RequestMapping(value = "addItem")
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


    @RequestMapping(value = "editItem")
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


    @RequestMapping(value = "delItem")
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


    @RequestMapping(value = "checkItem")
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
    @RequestMapping(value = "unCheckItem")
    public Boolean unCheckItem(Integer classId, List<Long> ids) {
        return null;
    }


}
