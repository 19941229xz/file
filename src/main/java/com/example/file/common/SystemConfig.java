package com.example.file.common;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件实体类
 */
@Component
@ConfigurationProperties(prefix = "app")
@Data
public class SystemConfig {


    private String info;

    private String author;

    private String email;

    private String swaggerTitle;



    private String swaggerContactName;

    private String swaggerContactWebUrl;

    private String swaggerContactEmail;
    private String swaggerVersion;

    private String swaggerDescription;
    private String swaggerTermsOfServiceUrl;


    /**
     * 阿里云对象存储相关参数
     */
    private int id;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


}
