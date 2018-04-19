package com.kingdee.hrp.sms.basedata.controller;

import com.kingdee.hrp.sms.basedata.service.IItemService;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.util.FileOperate;
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
 * @Title ItemController
 * @date 2018/4/19 14:00
 */
@Controller
@RequestMapping(value = "/item/")
public class ItemController {

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
    private IItemService itemService;

    @ResponseBody
    @RequestMapping(value = "setImage", method = RequestMethod.POST)
    public Map<String, Object> setImage(HttpServletRequest request, HttpServletResponse response, Integer classId, Long id) {

        if (classId <= 0 || id <= 0) {
            throw new BusinessLogicRunTimeException("缺少参数classId或id,或保存基本资料后再设置图片");
        }

        Map<String, Object> ret = new HashMap<String, Object>(4);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //得到文件map对象
        Map<String, MultipartFile> files = multipartRequest.getFileMap();

        // 实例化一个jersey
        Client client = new Client();
        // 供应商、医院logo按classId来建目录保存-要求比不在文件服务器中先建立好目录，否则不能上传成功
        String logoPath = filePath + classId + "/";

        String fileName = "";
        String imageUrl = "";

        for (Map.Entry<String, MultipartFile> fileEntry : files.entrySet()) {

            MultipartFile file = fileEntry.getValue();
            fileName = file.getOriginalFilename();

            imageUrl = FileOperate.upload(client, file, fileHost, logoPath);

            if (!"".equals(imageUrl)) {
                //上传成功
                ret.put(fileName, imageUrl);
                // 只能上传一张logo
                break;
            } else {
                //上传失败
                ret.put(fileName, "error");
            }

        }

        if (imageUrl != null && !"".equals(imageUrl)) {
            itemService.setImage(classId, id, imageUrl);
        }

        return ret;
    }

}
