package com.example.file.common.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: file
 * @description: 文件实体类
 * @author: BitCoc
 * @create: 2019-08-02 16:33
 */

@Data
public class FileParam {

    @NotEmpty(message = "文件名不能为空")
    private String fileName;
}
