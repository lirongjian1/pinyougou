package com.pinyougou.manager.controller;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor lrj
 * @date 2020/4/16 0016 18:19
 */
@RestController
@RequestMapping
public class PicController {
/**
 * # 配置文件服务器的访问地址
 * fastdfs_client.conf
 * fileServerUrl=http://192.168.12.131
 */
    @Value("${fileServerUrl}")
    private String fileServerUrl;

    @PostMapping("/upload")
    public Map<String,Object> upload(MultipartFile file){
        Map<String, Object> map = new HashMap<>();
        map.put("status",500);

        String path = this.getClass().getResource("/fastdfs_client.conf").getPath();
        try {
            ClientGlobal.init(path);
            StorageClient1 storageClient1 = new StorageClient1();
            String s = storageClient1.upload_file1(file.getBytes(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
            StringBuilder stringBuilder = new StringBuilder(fileServerUrl);
            stringBuilder.append("/"+s);
            map.put("url",stringBuilder.toString());
            map.put("status",200);
            return map;
        } catch (Exception e) {
           throw new RuntimeException(e);
        }

    }



}
