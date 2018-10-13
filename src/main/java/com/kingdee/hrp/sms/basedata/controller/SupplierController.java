package com.kingdee.hrp.sms.basedata.controller;

import com.kingdee.hrp.sms.basedata.service.SupplierService;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.SupplierQualificationAttachment;
import com.kingdee.hrp.sms.common.pojo.Qualification;
import com.kingdee.hrp.sms.common.pojo.SupplierQualificationModel;
import com.kingdee.hrp.sms.util.FileOperate;
import com.kingdee.hrp.sms.util.JsonUtil;
import com.kingdee.hrp.sms.util.RequestUtil;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yadda(silenceisok@163.com)
 * @date 2018/4/13 11:11
 */
@Controller
@RequestMapping(value = "/supplier/")
public class SupplierController {

    /**
     * 后台图片保存地址
     */
    @Value("#{propertiesConfig[fileRootDir]}")
    private String filePath;

    /**
     * 项目host路径
     */
    @Value("#{propertiesConfig[fileHost]}")
    private String fileHost;

    @Resource
    private SupplierService supplierService;

    @ResponseBody
    @RequestMapping(value = "changeLogo", method = RequestMethod.POST)
    public Map<String, Object> changeLogo(HttpServletRequest request, HttpServletResponse response, Integer classId,
                                          Long id) {

        if (classId == null || id == null) {
            throw new BusinessLogicRunTimeException("缺少参数classId或id");
        }

        Map<String, Object> ret = new HashMap<String, Object>(4);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //得到文件map对象
        Map<String, MultipartFile> files = multipartRequest.getFileMap();

        // 供应商、医院logo按classId来建目录保存-要求必须在文件服务器中先建立好目录，否则不能上传成功
        String logoPath = filePath + classId + "/";

        String fileName = "";
        String logoUrl = "";

        for (Map.Entry<String, MultipartFile> fileEntry : files.entrySet()) {

            MultipartFile file = fileEntry.getValue();
            fileName = file.getOriginalFilename();

            logoUrl = FileOperate.upload(file, fileHost, logoPath);
            //FileOperate.upload(file.getBytes(), logoPath, FilenameUtils.getExtension(fileName);
            if (!"".equals(logoUrl)) {
                //上传成功
                ret.put(fileName, logoUrl);
                // 只能上传一张logo
                break;
            } else {
                //上传失败
                ret.put(fileName, "error");
            }

        }

        if (logoUrl != null && !"".equals(logoUrl)) {
            supplierService.changeLogo(classId, id, logoUrl);
        }

        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "addCooperationApply")
    public Boolean addCooperationApply(Long hospital) {

        // 供应商
        Long supplier = SessionUtil.checkSupplier();

        return supplierService.addCooperationApply(supplier, hospital);
    }

    /**
     * 获取供应商对指定医院提供的资质
     * <p>
     * 返回医院要求的所有资质类型及当前供应商已经提供的资质类型，资质明细
     *
     * @param hospital 医院id
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     */
    @RequestMapping(value = "getHospitalSupplierQualificationsByHospital")
    @ResponseBody
    public SupplierQualificationModel getHospitalSupplierQualificationsByHospital(Long hospital,
                                                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                  @RequestParam(defaultValue = "1") Integer pageNo) {

        // 供应商
        Long supplier = SessionUtil.checkSupplier();

        if (null == hospital) {
            throw new BusinessLogicRunTimeException("请指定医院!");
        }

        return supplierService.getHospitalSupplierQualificationsByHospital(supplier, hospital, pageSize, pageNo);

    }

    /**
     * 获取供应商对所有合作医院提供的资质
     * 与getQualificationByHospital的区别是不区分医院，返回所有医院的。不返回类别(因为不同医院类别不同)
     *
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return SupplierQualificationModel
     */
    @RequestMapping(value = "getHospitalSupplierQualifications")
    @ResponseBody
    public SupplierQualificationModel getHospitalSupplierQualifications(
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNo) {

        Long supplier = SessionUtil.checkSupplier();

        return supplierService.getHospitalSupplierQualifications(supplier, pageSize, pageNo);
    }

    /**
     * 供应商提交一份证件给医院
     *
     * @param request                 HttpServletRequest (必须上传了至少一个pdf证件附件) 不需要，同步供应商自身维护的证件附件
     * @param type                    医院要求的证件类别id (t_hospital_supplier_qualification_type)
     * @param hospital                医院
     * @param supplierQualificationId 供应商维护的自己的证件信息id (t_supplier_qualification)
     */
    @RequestMapping(value = "transferQualification")
    @ResponseBody
    public void transferQualification(HttpServletRequest request, Long type, Long hospital,
                                      Long supplierQualificationId) {

        Long supplier = SessionUtil.checkSupplier();

        if (null == type || type <= 0) {
            throw new BusinessLogicRunTimeException("请选择资质类型");
        }
        if (null == hospital || hospital <= 0) {
            throw new BusinessLogicRunTimeException("请选择目标医院");
        }
        if (null == supplierQualificationId || supplierQualificationId <= 0) {
            throw new BusinessLogicRunTimeException("请选择要提交的证件");
        }

        supplierService.transferQualification(type, hospital, supplierQualificationId);

    }

    /**
     * 供应商新增一个证件信息
     *
     * @param request       HttpServletRequest (必须上传了至少一个pdf证件附件)
     * @param qualification 证件信息
     */
    @RequestMapping(value = "addQualification")
    @ResponseBody
    public void addQualification(HttpServletRequest request, Qualification qualification) throws IOException {

        Long supplier = SessionUtil.checkSupplier();

        // 附件
        List<File> files = RequestUtil.getFiles(request);

        if (CollectionUtils.isEmpty(files)) {
            throw new BusinessLogicRunTimeException("请至少提交一个附件");
        }

        supplierService.addQualification(supplier, qualification, files);

    }

    /**
     * 新增证件附件
     *
     * @param request   HttpServletRequest (必须上传至少一个pdf证件附件)
     * @param id        证件信息
     * @param overwrite 替换、追加附件(1:覆盖0:追加，默认0)
     */
    @RequestMapping(value = "addQualificationAttachment")
    @ResponseBody
    public void addQualificationAttachment(HttpServletRequest request, Long id,
                                           @RequestParam(defaultValue = "0") Integer overwrite) throws IOException {

        Long supplier = SessionUtil.checkSupplier();

        // 附件
        List<File> files = RequestUtil.getFiles(request);

        if (CollectionUtils.isEmpty(files)) {
            throw new BusinessLogicRunTimeException("请至少提交一个附件");
        }

        supplierService.addQualificationAttachment(supplier, id, files, overwrite);

    }

    /**
     * 删除证件附件(只是删除附件绑定资料，非物理清楚附件文件)
     *
     * @param qualificationId 证件信息
     * @param attachmentIds   附件记录id,多个用逗号分隔
     */
    @RequestMapping(value = "delQualificationAttachment")
    @ResponseBody
    public void delQualificationAttachment(Long qualificationId,
                                           String attachmentIds) {

        Long supplier = SessionUtil.checkSupplier();

        if (!StringUtils.isNotBlank(attachmentIds)) {
            throw new BusinessLogicRunTimeException("参数错误：必须指定删除的项!");
        }

        List<Long> ids = JsonUtil.json2Collection(attachmentIds, List.class, Long.class);

        supplierService.delQualificationAttachment(supplier, qualificationId, ids);

    }

    /**
     * 供应商资质获取附件列表
     *
     * @param qualificationId 证件
     * @return 附件列表
     */
    public List<SupplierQualificationAttachment> getQualificationAttachment(Long qualificationId) {

        Long supplier = SessionUtil.checkSupplier();

        if (null == qualificationId) {
            throw new BusinessLogicRunTimeException("参数错误：必须指定证件查看附件!");
        }

        return supplierService.getQualificationAttachment(supplier, qualificationId);

    }
}
