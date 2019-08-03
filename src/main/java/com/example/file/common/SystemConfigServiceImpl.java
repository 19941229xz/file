package com.example.file.common;

import org.springframework.beans.factory.annotation.Autowired;

public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    SystemConfigDao systemConfigDao;

    @Override
    public SystemConfig getSystemConfigById(int id) {

        return systemConfigDao.getSystemConfigById(id);
    }
}
