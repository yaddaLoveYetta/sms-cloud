package com.kingdee.hrp.sms.common.controller;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author yadda(silenceisok@163.com)
 * @Title UploadController
 * @date 2018/4/11 11:51
 */
@Controller
@RequestMapping(value = "/file/")
public class UploadController {

    @ResponseBody
    @RequestMapping(value = "uploadPic")
    public Map<String, Object> uploadPic(HttpServletRequest request, HttpServletResponse response, @RequestParam("image") MultipartFile pic) {

        Map<String, Object> json = new HashMap<String, Object>();

        try {
            //输出文件后缀名称
            System.out.println(pic.getOriginalFilename());
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //图片名称
            String name = df.format(new Date());

            Random r = new Random();
            for (int i = 0; i < 3; i++) {
                name += r.nextInt(10);
            }
            //
            String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
            //保存图片 File位置 （全路径） /upload/fileName.jpg
            String url = request.getSession().getServletContext().getRealPath("/static/img/upload/hospital/");

            //相对路径
            String path = "/" + name + "." + ext;
            File file = new File(url);

            if (!file.exists()) {
                file.mkdirs();
            }

            pic.transferTo(new File(url + path));
            String contextPath = request.getSession().getServletContext().getContextPath();
            json.put("success", contextPath + "/static/img/upload/hospital/" + path);

        } catch (Exception e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }

        return json;
    }
}
