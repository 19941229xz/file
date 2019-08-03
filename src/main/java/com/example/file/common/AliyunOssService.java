package com.example.file.common;

import java.io.File;

public interface AliyunOssService {


    /**
     * 上传本地文件到阿里云对象存储
     * @param file
     * @return 返回上传成功后 文件的访问路径 该路径是阿里云服务文件访问的绝对路径
     */
    String uploadFileToAliOss(File file,String originalFileName);




}
