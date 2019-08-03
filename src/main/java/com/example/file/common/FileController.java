package com.example.file.common;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.example.file.common.model.FileParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@RestController
@Slf4j
public class FileController {

    @Autowired
    AliyunOssService aliyunOssService;

    @Autowired
    FileUtilService fileUtilService;

    @Value("${app.filePath}")
    private String filePath;


    @PostMapping("/uploadFile")
    public Object getFileToken(@RequestBody FileParam fileParam){
        return MyRsp.success(JwtUtil.createFileToken(fileParam.getFileName()));
    }


    @PostMapping("/sendFile/{fileToken}")
    public Object sendImg(@PathVariable("fileToken") String fileToken, @RequestParam(name = "file_data", required = false) MultipartFile file){

//        String  newFileName = String.valueOf(new Date().getTime());
//
        log.info(file.getOriginalFilename());
        String oringinFileName = file.getOriginalFilename();

        File newFile = new File(filePath+oringinFileName);

        if(JwtUtil.getFileNamefromFileToken(fileToken).equals(oringinFileName)){

            if (!file.isEmpty()) {
                try {
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
                MyRsp.success(oringinFileName).msg("本地服务器上传成功");
            }
log.info("==========================test==========================");

            log.info(new File(filePath + oringinFileName).toString());
            log.info(oringinFileName);


            aliyunOssService.uploadFileToAliOss(new File(filePath + oringinFileName),oringinFileName);

            fileUtilService.delFile(new File(filePath + oringinFileName).toString());



        }

        return MyRsp.success(null);
    }


}
