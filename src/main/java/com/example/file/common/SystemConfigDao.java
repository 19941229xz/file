package com.example.file.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SystemConfigDao {

    @Select("select * from systemConfig where id=#{id}")
    SystemConfig getSystemConfigById(int id);
}
