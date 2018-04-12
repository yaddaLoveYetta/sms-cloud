package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件操作
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/4/12 11:26
 */
public class FileOperate {

    /**
     * 上传文件
     *
     * @param serverPath 服务器地址:(http://127.0.0.1:8081/)
     * @param path       文件路径（不包含服务器地址：file/）
     * @return 文件url
     */
    public static String upload(Client client, MultipartFile file, String serverPath, String path) {

        // 文件名
        String fileName = FilenameUtils.getName(file.getOriginalFilename());
        // 文件的扩展名
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        //相对路径
        String relativePath = path + fileName;

        // 创建文件存放位置
        String directory = serverPath + path.substring(0, path.lastIndexOf("/"));
        File fileDirectory = new File(directory);
        if (!fileDirectory.exists()) {
            boolean mkdirs = fileDirectory.mkdirs();
            System.out.println(mkdirs);
        }

        // 文件存放服务器的URL（真实路径）
        String realPath = serverPath + relativePath;

        // 设置请求路径
        WebResource resource = client.resource(realPath);

        // 发送开始post get put（基于put提交）
        try {
/*            File mf = resource.get(File.class);
            if (mf.isFile()) {
                //已经存在文件先删除
                resource.delete();
            }*/
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
