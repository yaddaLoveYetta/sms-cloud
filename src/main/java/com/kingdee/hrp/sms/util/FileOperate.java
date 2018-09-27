package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件操作
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/4/12 11:26
 */
public final class FileOperate {

    private final static Logger logger = LoggerFactory.getLogger(FileOperate.class);

    /**
     * 上传文件
     *
     * @param serverPath 服务器地址:(http://127.0.0.1:8081/)
     * @param path       文件路径（不包含服务器地址：eg: file/1012/eee）
     * @return 文件url
     */
    public static String upload(MultipartFile file, String serverPath, String path) {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        try {
            return upload(file.getBytes(), serverPath, path, extension);
        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }
    }

    /**
     * 上传文件
     *
     * @param file       byte[]
     * @param serverPath 服务器地址:(http://127.0.0.1:8081/)
     * @param path       文件路径（不包含服务器地址：file/）
     * @param extension  文件扩展名
     * @return 文件url
     */
    public static String upload(byte[] file, String serverPath, String path, String extension) {

        // 文件名用雪花算法来保证不重复
        Long fileName = SnowFlake.getId(0, 0);
        //相对路径
        String relativePath = path + fileName;
        // 创建文件存放位置
        String directory = serverPath + path.substring(0, path.lastIndexOf("/"));

        // 文件存放服务器的URL（真实路径）
        String realPath = serverPath + relativePath + "." + extension;

        // 实例化一个jersey
        //Client实例很消耗系统资源，需要重用
        //创建web资源，创建请求，接受响应都是线程安全的
        //所以Client实例和WebResource实例可以在多个线程间安全的共享
        Client client = new Client();

        // 设置请求路径
        WebResource resource = client.resource(realPath);

        try {
            //已经存在文件先删除,试图删除一次
            resource.delete();
        } catch (Exception e) {
            logger.debug("resource.delete() error", e);
        }
        // 发送开始post get put（基于put提交）

        resource.put(String.class, file);
        return realPath;

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
            return "n";
        }
    }

    /**
     * 下载网络上的文件
     *
     * @param fileUrl 文件url链接
     * @return byte[]
     */
    public static byte[] downloadPicture(String fileUrl) {

        DataInputStream dataInputStream = null;
        ByteArrayOutputStream output = null;

        try {

            URL url = new URL(fileUrl);
            dataInputStream = new DataInputStream(url.openStream());

            output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            return output.toByteArray();

        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            throw new BusinessLogicRunTimeException(e.getMessage());

        } finally {
            try {
                if (null != dataInputStream) {
                    dataInputStream.close();
                }
                if (null != output) {
                    output.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }

        }
    }
}
