/*
 * Copyright (c) 2008-2016 yadda(silenceisok@163.com), All Rights Reserved.
*/
/**
 * Copyright (c) 2008-2016 yadda(silenceisok@163.com), All Rights Reserved.
*/
/**
 * Copyright (c) 2008-2016 yadda(silenceisok@163.com), All Rights Reserved.
*/
/**
 * @(#)util Copyright (c) 2008-2016 yadda(silenceisok@163.com), All Rights Reserved.
 */
/*
* @(#)util
*
* Copyright ***版权信息***.
*/
package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.filter.SmsPropertyPlaceholderConfigurer;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
     * @param path       文件全路径（即方法不考虑fileRootDir配置）
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
     * 上传文件到文件服务器
     *
     * @param file       byte[]
     * @param serverPath 服务器地址:(http://127.0.0.1:8081/)
     * @param path       文件全路径（即方法不考虑fileRootDir配置）
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
     * 上传文件到文件服务器
     *
     * @param file      byte[]
     * @param path      文件路径（不包含根地址fileRootDir配置）
     * @param extension 文件扩展名
     * @return 文件url
     */
    public static String upload(byte[] file, String path, String extension) {

        String fileHost = SmsPropertyPlaceholderConfigurer.getContextProperty("fileHost");
        String fileRootDir = SmsPropertyPlaceholderConfigurer.getContextProperty("fileRootDir");
        // 文件存放路径等于根路径加上业务设置的路径
        path = fileRootDir + path;

        return upload(file, fileHost, path, extension);
    }

    /**
     * 上传文件到文件服务器
     *
     * @param file      File
     * @param path      文件路径（不包含根地址fileRootDir配置）
     * @param extension 文件扩展名
     * @return 文件url
     */
    public static String upload(File file, String path, String extension) {

        String fileHost = SmsPropertyPlaceholderConfigurer.getContextProperty("fileHost");
        String fileRootDir = SmsPropertyPlaceholderConfigurer.getContextProperty("fileRootDir");
        // 文件存放路径等于根路径加上业务设置的路径
        path = fileRootDir + path;

        try {
            return upload(FileUtils.readFileToByteArray(file), fileHost, path, extension);
        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e);
        }
    }

    /**
     * 上传文件到文件服务器
     *
     * @param file File
     * @param path 文件路径（不包含根地址fileRootDir配置）
     * @return 文件url
     */
    public static String upload(File file, String path) {

        String extension = FilenameUtils.getExtension(file.getName());

        return upload(file, path, extension);

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
    public static byte[] downloadWebFile(String fileUrl) {

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

    private static void readFileByLines() throws Exception {
        //项目的绝对路径，也就是想修改的文件路径
        String filePath = "D:\\work\\projects\\sms-cloud\\src\\main\\java\\com\\kingdee\\hrp\\sms\\util";
        File f = new File(filePath);
        String content = "/**\n" +
                " * @(#)" + f.getName() + "\n" +
                " *\n" +
                " * Copyright (c) 2008-2016 yadda(silenceisok@163.com), All Rights Reserved.\n" +
                "*/\n";

        fileTree(f, content);
    }

    /**
     * 取出所有的文件及文件夹
     *
     * @param f 文件夹对象
     * @throws Exception
     */
    private static void fileTree(File f, String content) throws Exception {
        File[] t = f.listFiles();
        for (int i = 0; i < t.length; i++) {
            if (t[i].isDirectory()) {
                fileTree(t[i], content);
            } else {
                insert(t[i], content);
            }
        }
    }

/*    public static void main(String[] args) {
        try {
            readFileByLines();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 开始插入内容
     *
     * @param f 文件对象
     * @throws IOException
     */
    private static void insert(File f, String content) throws IOException {
        File temp = File.createTempFile("temp", null);
        temp.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        FileOutputStream tempOut = new FileOutputStream(temp);
        FileInputStream tempInput = new FileInputStream(temp);
        raf.seek(0);
        byte[] buf = new byte[64];
        int hasRead = 0;
        while ((hasRead = raf.read(buf)) > 0) {
            tempOut.write(buf, 0, hasRead);
        }
        raf.seek(0);

        raf.write(content.getBytes());
        while ((hasRead = tempInput.read(buf)) > 0) {
            raf.write(buf, 0, hasRead);
        }
        raf.close();
        tempOut.close();
        tempInput.close();
    }

    public static void main(String[] args) throws Exception {
        //java文件所在目录
        String dir = "D:\\work\\projects\\sms-cloud\\src\\main\\java\\com\\kingdee";
        File file = new File(dir);
        addCopyright4Directory(file);
    }

    private static void addCopyright4Directory(File file) throws Exception {
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        for (File f : files) {
            if (f.isFile()) {
                addCopyright4File(f);
                System.out.println("文件===" + f.getName());
            } else {
                System.out.println("目录==" + f.getName());
                addCopyright4Directory(f);
            }
        }
    }

    private static void addCopyright4File(File file) throws Exception {
        String fileName = file.getName();
        boolean isJava = fileName.endsWith(".java");
        if (!isJava) {
            logger.info("This file is not java source file,filaName=" + fileName);
            return;
        }

        if (isJava) {
            // 版权字符串

            String copyright = "/*\n" +
                    " * Copyright (c) 2008-2016 yadda(silenceisok@163.com), All Rights Reserved.\n" +
                    "*/\n";
            //尝试使用了RandomAccessFile.writeUTF，问题是开头字符是“NUL”，没能解决。
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String content = "";
            //读取一行，一定要加上“换行符”,Windows下可以直接用“\n”
            String lineSeperator = "\n";
            //lineSeperator = System.getProperty("line.separator")
            while ((line = br.readLine()) != null) {
                content += line + lineSeperator;
            }
            br.close();
            //把拼接后的字符串写回去
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(copyright);
            fileWriter.write(content);
            fileWriter.close();
        }

    }

}
