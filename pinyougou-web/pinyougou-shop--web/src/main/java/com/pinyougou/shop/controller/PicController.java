package com.pinyougou.shop.controller;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor lrj
 * @date 2020/4/11 0011 16:26
 */
@RestController
@RequestMapping
public class PicController {
    /** 注入文件服务器访问地址 */
    @Value("${fileServerUrl}")
    private String fileServerUrl;

    @PostMapping("upload")
    public Map<String,Object> uploadFile(@RequestParam("file") MultipartFile multipartFile) {

        Map<String,Object> map = new HashMap<>();
        map.put("status",500);

        try {
            //获取文件绝对路径
            String path = this.getClass().getResource("/fastdfs_client.conf").getPath();
            //初始化客户端全局对象
            ClientGlobal.init(path);
            StorageClient1 storageClient1 = new StorageClient1();
            String s = storageClient1.upload_file1(multipartFile.getBytes(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
            StringBuilder stringBuilder = new StringBuilder(fileServerUrl);
            String url = stringBuilder.append("/" + s).toString();
            map.put("url",url);
            map.put("status",200);
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
