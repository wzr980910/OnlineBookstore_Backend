package com.bookStore.service.impl;

import com.bookStore.entity.Admin;
import com.bookStore.mapper.AdminMapper;
import com.bookStore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/9:17
 * @Description:
 */
@Service
public class AdminServiceImpl implements AdminService {
    private AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin login(String adminName,String password){
        return adminMapper.selectAdmin(adminName,password);
    };

}
