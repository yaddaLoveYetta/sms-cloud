package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author le.xiao
 */
public final class RequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    /**
     * 获取上传的文件
     *
     * @param request HttpServletRequest
     * @return List<File>
     */
    public static List<File> getFiles(HttpServletRequest request) {

        List<File> files = new ArrayList<>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 附件
        Map<String, MultipartFile> multipartFileMap = multipartRequest.getFileMap();

        if (!multipartFileMap.isEmpty()) {

            // MultipartFile to File
            for (Map.Entry<String, MultipartFile> fileEntry : multipartFileMap.entrySet()) {

                MultipartFile multipartFile = fileEntry.getValue();

                File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                        multipartFile.getName());

                files.add(tmpFile);

            }
        }

        return files;

    }

    /**
     * 获取上传的文件
     *
     * @param request HttpServletRequest
     * @return List<MultipartFile>
     */
    public static List<MultipartFile> getMultipartFiles(HttpServletRequest request) {

        List<MultipartFile> files = new ArrayList<>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 附件
        Map<String, MultipartFile> multipartFileMap = multipartRequest.getFileMap();

        if (!multipartFileMap.isEmpty()) {

            for (Map.Entry<String, MultipartFile> fileEntry : multipartFileMap.entrySet()) {

                files.add(fileEntry.getValue());
            }
        }

        return files;

    }
}
