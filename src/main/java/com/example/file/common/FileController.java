package com.example.file.common;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.example.file.common.model.FileParam;
import lombok.extern.slf4j.Slf4j;
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

    @Value("${app.filePath}")
    private String filePath;


    @RequestMapping("/uploadFile")
    public Object getFileToken(@RequestBody FileParam fileParam){
        return MyRsp.success(JwtUtil.createFileToken(fileParam.getFileName()));
    }


    @PostMapping("/sendFile/{fileToken}")
    public Object sendImg(@PathVariable("fileToken") String fileToken, @RequestParam(name = "file_data", required = false) MultipartFile file){

//        String  newFileName = String.valueOf(new Date().getTime());
//
//        log.info(file.getOriginalFilename());
        String oringinFileName = file.getOriginalFilename();
//Todo  参考uploadController


        return MyRsp.success(null);
    }


}
