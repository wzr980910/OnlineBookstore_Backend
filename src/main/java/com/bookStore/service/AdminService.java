package com.bookStore.service;

import com.bookStore.entity.Admin;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/9:15
 * @Description:
 */
public interface AdminService {
    Admin login(String adminName,String password);
}
