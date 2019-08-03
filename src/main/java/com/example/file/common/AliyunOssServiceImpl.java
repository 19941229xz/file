package com.example.file.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
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

        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        PutObjectResult res = ossClient.putObject("vp-saas-common", System.currentTimeMillis()+originalFileName, file);
//        ossClient.putObject("vp-saas-common", "img-zhong", new File("file:///"+"D:/img/"));

        //Todo 对PutObjectResult进行校验  看是否上传成功  并且返回文件访问路径

        // 关闭OSSClient。
        ossClient.shutdown();

        return null;
    }
}
