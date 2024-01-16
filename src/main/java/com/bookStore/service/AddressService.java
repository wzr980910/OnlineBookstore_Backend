package com.bookStore.service;

import com.bookStore.pojo.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author 邓桂材
* @description 针对表【address(地址表)】的数据库操作Service
* @createDate 2024-01-14 17:33:39
*/
public interface AddressService extends IService<Address> {

    int addAddress(Long userId, Address address);

    int updateAddress(Long userId, Address address);
    int deleteAddress(Long userId,Long addressId);

    Map<String, Object> selectAddress(Long userId);
}
