package com.kingdee.hrp.sms.basedata.controller;

import com.kingdee.hrp.sms.basedata.service.SupplierService;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.pojo.SupplierQualificationModel;
import com.kingdee.hrp.sms.util.FileOperate;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    @Value("#{propertiesConfig[filePath]}")
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
     * @param hospitalId 医院id
     * @param pageSize   分页大小
     * @param pageNo     当前页码
     */
    @RequestMapping(value = "getQualificationByHospital")
    @ResponseBody
    public SupplierQualificationModel getQualificationByHospital(Long hospitalId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNo) {

        // 供应商
        Long supplier = SessionUtil.checkSupplier();

        if (null == hospitalId) {
            throw new BusinessLogicRunTimeException("请指定医院!");
        }

        return supplierService.getQualificationByHospital(supplier, hospitalId, pageSize, pageNo);

    }

    /**
     * 获取供应商对所有合作医院提供的资质
     * 与getQualificationByHospital的区别是不区分医院，返回所有医院的。不返回类别(因为不同医院类别不同)
     *
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return SupplierQualificationModel
     */
    @RequestMapping(value = "getQualifications")
    @ResponseBody
    public SupplierQualificationModel getQualifications(@RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNo) {

        Long supplier = SessionUtil.checkSupplier();

        return supplierService.getQualification(supplier, pageSize, pageNo);
    }

    /**
     * 供应商提交一份证件给医院
     *
     * @param request               HttpServletRequest (必须上传一个pdf证件附件) 不需要，同步供应商自身维护的证件附件
     * @param type                  医院要求的证件类别id (t_hospital_supplier_qualification_type)
     * @param hospital              医院
     * @param supplierQualificationId 供应商维护的自己的证件信息id (t_supplier_qualification)
     */
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
}
