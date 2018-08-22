package com.kingdee.hrp.sms.basedata.controller;

import com.kingdee.hrp.sms.basedata.service.SupplierService;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.util.FileOperate;
import com.kingdee.hrp.sms.util.SessionUtil;
import com.sun.jersey.api.client.Client;
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

        Map<String, Object> ret = new HashMap<String, Object>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //得到文件map对象
        Map<String, MultipartFile> files = multipartRequest.getFileMap();

        // 实例化一个jersey
        Client client = new Client();
        // 供应商、医院logo按classId来建目录保存-要求比不在文件服务器中先建立好目录，否则不能上传成功
        String logoPath = filePath + classId + "/";

        String fileName = "";
        String logoUrl = "";

        for (Map.Entry<String, MultipartFile> fileEntry : files.entrySet()) {

            MultipartFile file = fileEntry.getValue();
            fileName = file.getOriginalFilename();

            logoUrl = FileOperate.upload(client, file, fileHost, logoPath);

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
        Long supplier = SessionUtil.getUserLinkSupplier();
        if (supplier <= 0) {
            throw new BusinessLogicRunTimeException("只有供应商能进行此操作!");
        }

        return supplierService.addCooperationApply(supplier, hospital);
    }
}
