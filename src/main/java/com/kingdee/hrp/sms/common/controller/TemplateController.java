package com.kingdee.hrp.sms.common.controller;

import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.FormAction;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.common.service.TemplateService;
import com.kingdee.hrp.sms.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
    private TemplateService templateService;

    private static Logger logger = LoggerFactory.getLogger(TemplateController.class);

    /**
     * 查询单据模板数据
     */
    @RequestMapping(value = "getFormTemplate")
    @ResponseBody
    public FormTemplate getFormTemplate(Integer classId) {

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
    public List<FormAction> getFormAction(Integer classId, @RequestParam(defaultValue = "0") Integer type) {

        //0:查看(列表)1:(新增)2:(编辑)
        Constants.BillOperateType operateType = Constants.BillOperateType.getBillOperateType(type);

        if (operateType == Constants.BillOperateType.NOT_SUPPORT) {
            throw new BusinessLogicRunTimeException("type类型错误!");
        }
        return templateService.getFormAction(classId, operateType);
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
    public Map<String, Object> getItems(@RequestParam(defaultValue = "0") Integer classId, String condition,
            String sort, @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNo) throws IOException {

        if (classId <= 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        List<Condition> conditions = new ArrayList<>();
        List<Sort> sorts = new ArrayList<>();

        // 包装查询条件-方便操作
        if (StringUtils.isNotBlank(condition)) {
            conditions = JsonUtil.json2Collection(condition, List.class, Condition.class);
        }
        // 包装查询结果排序-方便操作
        if (StringUtils.isNotBlank(sort)) {
            sorts = JsonUtil.json2Collection(condition, List.class, Sort.class);
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

        if (classId <= 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        List<Sort> sorts = new ArrayList<Sort>();
        // 包装查询结果排序-方便操作
        if (StringUtils.isNotBlank(sort)) {
            sorts = JsonUtil.json2Collection(sort, List.class, Sort.class);
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

        List<Long> ids = JsonUtil.json2Collection(items, List.class, Long.class);

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

        List<Long> ids = JsonUtil.json2Collection(items, List.class, Long.class);

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
     * 按条件导出数据
     *
     * @param response  HttpServletResponse
     * @param classId   业务类型
     * @param condition 查询条件
     * @param sort      排序条件
     */
    @RequestMapping(value = "export")
    public void export(HttpServletResponse response, Integer classId, String condition, String sort) {

        if (classId <= 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        List<Condition> conditions = new ArrayList<>();
        List<Sort> sorts = new ArrayList<>();

        // 包装查询条件-方便操作
        if (StringUtils.isNotBlank(condition)) {
            conditions = JsonUtil.json2Collection(condition, List.class, Condition.class);
        }
        // 包装查询结果排序-方便操作
        if (StringUtils.isNotBlank(sort)) {
            sorts = JsonUtil.json2Collection(condition, List.class, Sort.class);
        }

        FormTemplate formTemplate = templateService.getFormTemplate(classId, 1);
        // 导出的excel文件名
        String fileName = formTemplate.getFormClass().getName() + ".xls";
        //创建HSSFWorkbook
        HSSFWorkbook wb = templateService.export(classId, conditions, sorts);

        //响应到客户端
        returnResult(response, fileName, wb);

    }

    /**
     * 选择id导出数据
     *
     * @param response HttpServletResponse
     * @param classId  业务类型
     * @param ids      id集合
     */
    @RequestMapping(value = "exportById")
    public void exportById(HttpServletResponse response, Integer classId, String ids) {

        if (classId <= 0) {
            throw new BusinessLogicRunTimeException("参数错误：必须提交classId");
        }

        List<Long> idList = new ArrayList<>();

        if (StringUtils.isBlank(ids)) {
            throw new BusinessLogicRunTimeException("参数错误：请勾选导出记录");
        }

        idList = JsonUtil.json2Collection(ids, List.class, Long.class);

        FormTemplate formTemplate = templateService.getFormTemplate(classId, 1);
        // 导出的excel文件名
        String fileName = formTemplate.getFormClass().getName() + ".xls";
        //创建HSSFWorkbook
        HSSFWorkbook wb = templateService.export(classId, idList);

        //响应到客户端
        returnResult(response, fileName, wb);

    }

    private void returnResult(HttpServletResponse response, String fileName, HSSFWorkbook wb) {

        OutputStream os = null;
        try {
            this.setResponseHeader(response, fileName);
            os = response.getOutputStream();
            wb.write(os);

        } catch (Exception e) {
            logger.error("exportByCondition错误:{}", e.getMessage());
        } finally {
            if (os == null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    logger.error("exportByCondition关闭流错误:{}", e.getMessage());
                }

            }
        }

    }

    /**
     * 发送响应流方法
     *
     * @param response HttpServletResponse
     * @param fileName String
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
            // 指定返回的是一个不能被客户端读取的流，必须被下载
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            // 添加头信息，指定文件大小，让浏览器能够显示下载进度
            //response.addHeader("Content-Length", file.Length.ToString());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
