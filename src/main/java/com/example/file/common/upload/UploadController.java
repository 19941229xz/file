package com.example.file.common.upload;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.example.file.common.DeleteFileUtil;
import com.example.file.common.failJwtUtil;
import com.example.file.common.MyResponse;
import com.example.file.common.MyRsp;
import com.example.file.common.model.FileParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

//@RestController
@RequestMapping("upload")
@Slf4j
public class UploadController {

    @Value("${app.imgPath}")
    private String imgPath;


    @PostMapping("/getFileToken")
    public Object getImgToken(@RequestBody @Valid FileParam fileParam) {

        String Token = failJwtUtil.createfileToken(fileParam.getFileName());

        return Token==null?MyRsp.error().msg("Token生成失败。。。。"):MyRsp.success(Token).msg("Token生成成功");

    }


    @PostMapping("/sendFile/{fileToken}")
    public Object sendImg(@PathVariable("fileToken") String fileToken,@RequestParam(name = "file_data", required = false) MultipartFile file){

      String  newFileName = String.valueOf(new Date().getTime());

        log.info(file.getOriginalFilename());

    if(failJwtUtil.getContentfromImgToken(fileToken).equals(file.getOriginalFilename())){

        String fileName = file.getOriginalFilename();


        File newFile = new File(imgPath+fileName);

        //文件上传至服务器
        if (!file.isEmpty()) {
            try {
                //图片命名
//                String newCompanyImagepath = imgPath + fileName;
//                File newFile = new File(newCompanyImagepath);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(newFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                log.info(newFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                return MyResponse.error().msg("本地服务器上传失败");
            }
            MyRsp.success(fileName).msg("本地服务器上传成功");
        }

        //文件上传至oss平台
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIMwV4Oxl7Oejc";
        String accessKeySecret = "ZwmXDHSWzkTXfcjb061LI6DtdsyJL4";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        PutObjectResult res= ossClient.putObject("vp-saas-common",newFileName , new File(imgPath+fileName));
//        ossClient.putObject("vp-saas-common", "img-zhong", new File("file:///"+"D:/img/"));

        // 关闭OSSClient。
        ossClient.shutdown();

        DeleteFileUtil.deleteFile(newFile.getAbsolutePath());
            return MyRsp.success(fileName).msg("文件上传成功");

        }
        return MyRsp.error().msg("文件地址获取失败");
    }
}
