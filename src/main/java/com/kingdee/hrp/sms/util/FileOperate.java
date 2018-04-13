package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件操作
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/4/12 11:26
 */
public class FileOperate {

    private final static Logger logger = LoggerFactory.getLogger(FileOperate.class);

    /**
     * 上传文件
     *
     * @param serverPath 服务器地址:(http://127.0.0.1:8081/)
     * @param path       文件路径（不包含服务器地址：file/）
     * @return 文件url
     */
    public static String upload(Client client, MultipartFile file, String serverPath, String path) {

        // 文件名用雪花算法来保证不重复
        Long fileName = SnowFlake.getId(0, 0);
        // 文件名
        //String fileName = FilenameUtils.getName(file.getOriginalFilename());
        // 文件的扩展名
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        //相对路径
        String relativePath = path + fileName;
        // 创建文件存放位置
        String directory = serverPath + path.substring(0, path.lastIndexOf("/"));

        // 文件存放服务器的URL（真实路径）
        String realPath = serverPath + relativePath + "." + extension;

        // 设置请求路径
        WebResource resource = client.resource(realPath);

        try {
            //已经存在文件先删除,试图删除一次
            resource.delete();
        } catch (Exception e) {
            logger.debug("resource.delete() error", e);
        }
        // 发送开始post get put（基于put提交）
        try {
            resource.put(String.class, file.getBytes());
            return realPath;
        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e);
        }
    }

    /**
     * 删除文件
     *
     * @param filePath（文件完整地址：http://172.16.5.102:8090/upload/1234.jpg）
     * @return
     */
    public static String delete(String filePath) {
        try {
            Client client = new Client();
            WebResource resource = client.resource(filePath);
            resource.delete();
            return "y";
        } catch (Exception e) {
            e.printStackTrace();
            return "n";
        }
    }
}
