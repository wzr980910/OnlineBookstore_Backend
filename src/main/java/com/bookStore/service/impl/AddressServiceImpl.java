package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Address;
import com.bookStore.service.AddressService;
import com.bookStore.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author 邓桂材
* @description 针对表【address(地址表)】的数据库操作Service实现
* @createDate 2024-01-14 17:33:39
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

}




