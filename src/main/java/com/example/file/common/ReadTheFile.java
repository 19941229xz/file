package com.example.file.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @program: file
 * @description: 获取txt文件内容
 * @author: BitCoc
 * @create: 2019-08-02 15:30
 */

public class ReadTheFile {
    /**
          * 读取txt文件的内容
          * @param file 想要读取的文件对象
          * @return 返回文件内容
          */

    public static String getfiletxt(File file) {

        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}