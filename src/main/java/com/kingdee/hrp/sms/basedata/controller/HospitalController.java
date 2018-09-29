package com.kingdee.hrp.sms.basedata.controller;

import com.kingdee.hrp.sms.basedata.service.HospitalService;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.util.FileOperate;
import com.sun.jersey.api.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping(value = "/hospital/")
public class HospitalController {

    private static Logger logger = LoggerFactory.getLogger(HospitalController.class);

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
    private HospitalService hospitalService;

    /**
     * 更换医院logo
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param classId  业务类型
     * @param id       医院id
     * @return Map<String, Object>
     */
    @ResponseBody
    @RequestMapping(value = "changeLogo", method = RequestMethod.POST)
    public Map<String, Object> changeLogo(HttpServletRequest request, HttpServletResponse response, Integer classId,
            Long id) {

        if (classId == null || id == null) {
            throw new BusinessLogicRunTimeException("缺少参数classId或id");
        }

        Map<String, Object> ret = new HashMap<>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //得到文件map对象
        Map<String, MultipartFile> files = multipartRequest.getFileMap();

        // 供应商、医院logo按classId来建目录保存-要求比不在文件服务器中先建立好目录，否则不能上传成功
        String logoPath = filePath + classId + "/";

        String fileName = "";
        String logoUrl = "";

        for (Map.Entry<String, MultipartFile> fileEntry : files.entrySet()) {

            MultipartFile file = fileEntry.getValue();
            fileName = file.getOriginalFilename();

            logoUrl = FileOperate.upload( file, fileHost, logoPath);

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
            hospitalService.changeLogo(classId, id, logoUrl);
        }

        return ret;
    }

    /**
     * 医院同意供应商成为合作供应商的申请
     *
     * @param id          申请记录id
     * @param hrpSupplier 医院制定关联的本地HRP供应商
     */
    @ResponseBody
    @RequestMapping(value = "agreeApply")
    public void agreeApply(Long id, Long hrpSupplier) {

        if (null == id) {
            throw new BusinessLogicRunTimeException("请选择记录进行操作!");
        }
        if (null == hrpSupplier) {
            throw new BusinessLogicRunTimeException("必须关联HRP供应商!");
        }

        hospitalService.processCooperationApply(id, hrpSupplier, Constants.CooperationApplyStatus.AGREE);
    }

    /**
     * 医院拒绝供应商成为合作供应商的申请
     *
     * @param id 申请记录id
     */
    @ResponseBody
    @RequestMapping(value = "disagreeApply")
    public void disagreeApply(Long id) {

        if (null == id) {
            throw new BusinessLogicRunTimeException("请选择记录进行操作!");
        }

        hospitalService.processCooperationApply(id, null, Constants.CooperationApplyStatus.DISAGREE);
    }

}
