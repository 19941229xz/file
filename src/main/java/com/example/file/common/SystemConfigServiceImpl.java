package com.example.file.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    SystemConfigDao systemConfigDao;

    @Override
    public SystemConfig getSystemConfigById(int id) {

        return systemConfigDao.getSystemConfigById(id);
    }
}
