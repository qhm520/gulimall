package com.qian.gulimall.third.party.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * FdfsController is
 *
 * @author QIAN
 * Date 2020/08/06
 * Time 16:36
 */
@Slf4j
@RestController
public class FdfsController {

    private String folder = "com/qian/gulimall/third/party/controller/FdfsController/";

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, size, fileName.substring(fileName.lastIndexOf(".") + 1), null);
        // TODO 保存到数据库

        return getResAccessUrl(storePath);
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileUrl = "group1/M00/00/00/qf5zCl8rxVeAAvygAAAADEHIqB4224.txt";  // TODO 从参数中获取ID查询获取
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = fastFileStorageClient.downloadFile(group, path, downloadByteArray);

        String filename = "qian2020080617.txt";
        try (OutputStream outputStream = new FileOutputStream(new File(filename));) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            outputStream.write(bytes);
            outputStream.flush();
        }
    }

    /**
     * 测试文件删除
     */
    @RequestMapping("/delete")
    public void deleteFile() {
        fastFileStorageClient.deleteFile("group1", "M00/00/00/wKiAjVlpQVyARpQwAADGA0F72jo566.jpg");
    }


    // 封装文件完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }
}
