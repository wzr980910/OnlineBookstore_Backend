package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Admin;
import com.bookStore.service.AdminService;
import com.bookStore.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author 邓桂材
* @description 针对表【admin(管理员表)】的数据库操作Service实现
* @createDate 2024-01-14 12:03:10
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




