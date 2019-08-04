package com.example.file.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import jdk.nashorn.internal.runtime.options.LoggingOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.nio.cs.FastCharsetProvider;
import sun.rmi.runtime.Log;

import java.io.File;
import java.net.URL;
import java.util.Date;

@Service
@Slf4j
public class AliyunOssServiceImpl implements AliyunOssService {

    @Autowired
    SystemConfigService systemConfigService;


    @Transactional
    @Override
    public String uploadFileToAliOss(File file,String originalFileName) {


        // get the aliyun oos config from db
        SystemConfig systemConfig = systemConfigService.getSystemConfigById(1);

        //文件上传至oss平台
        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String endpoint = systemConfig.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//        String accessKeyId = "LTAIMwV4Oxl7Oejc";
        String accessKeyId = systemConfig.getAccessKeyId();
//        String accessKeySecret = "ZwmXDHSWzkTXfcjb061LI6DtdsyJL4";
        String accessKeySecret = systemConfig.getAccessKeySecret();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String currentTime = System.currentTimeMillis()+"";

        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        PutObjectResult res = ossClient.putObject("vp-saas-common", currentTime+originalFileName, file);
//        ossClient.putObject("vp-saas-common", "img-zhong", new File("file:///"+"D:/img/"));

       if(res.getETag()!=null){
           // 关闭OSSClient。
           ossClient.shutdown();
           //Todo  后续可改进写活
           return "https://vp-saas-common.oss-cn-shenzhen.aliyuncs.com/"+currentTime+originalFileName;

       }else {
           // 关闭OSSClient。
           ossClient.shutdown();
           return "false";
       }

//        log.info(res.getVersionId());
////        log.info(res.get);
//
//        Date expiration = new Date(new Date().getTime() + 3600 * 1000);// 生成URL
//        URL url = ossClient.generatePresignedUrl(systemConfig.getBucketName(), accessKeyId, expiration);




    }
}
