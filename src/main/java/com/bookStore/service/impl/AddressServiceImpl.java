package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.exception.BizException;
import com.bookStore.pojo.Address;
import com.bookStore.pojo.pojoenum.DefaultAddress;
import com.bookStore.service.AddressService;
import com.bookStore.mapper.AddressMapper;
import com.bookStore.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邓桂材
 * @description 针对表【address(地址表)】的数据库操作Service实现
 * @createDate 2024-01-14 17:33:39
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
        implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public int addAddress(Long userId, Address address) {
        address.setUserId(userId);
        //查询数据库中是否存在该用户的默认地址
        LambdaQueryWrapper<Address> wrapperExist = new LambdaQueryWrapper<>();
        wrapperExist.eq(Address::getUserId, userId);
        wrapperExist.eq(Address::getDefaultAddress, DefaultAddress.DEFAULT_ADDRESS.getValue());

        Address addressDefault = addressMapper.selectOne(wrapperExist);
        //如果不存在
        if (addressDefault == null) {
            //将即将插入的地址设置成默认地址
            address.setDefaultAddress(DefaultAddress.DEFAULT_ADDRESS.getValue());
        } else {//如果数据库中有一条默认地址
            //如果用户想将这条地址设置为默认地址，将数据库中原来的默认地址改为非默认地址
            if (address.getDefaultAddress().equals(DefaultAddress.DEFAULT_ADDRESS.getValue())) {
                LambdaUpdateWrapper<Address> wrapperChangeDefault = new LambdaUpdateWrapper<>();
                wrapperChangeDefault.eq(Address::getId, addressDefault.getId());
                wrapperChangeDefault.set(Address::getDefaultAddress, DefaultAddress.NOT_DEFAULT_ADDRESS.getValue());
                int rows = addressMapper.update(wrapperChangeDefault);
                if (rows == 0) {
                    throw new BizException(ResultCode.DB_UPDATE_ERROR);
                }
            }
        }
        //插入地址
        return addressMapper.insert(address);
    }

    @Override
    public int updateAddress(Long userId, Address address) {
        address.setUserId(userId);
        if (address.getDefaultAddress().equals(DefaultAddress.DEFAULT_ADDRESS.getValue())) {
            //用户想将当前地址修改成默认地址
            LambdaUpdateWrapper<Address> wrapperOld = new LambdaUpdateWrapper<>();
            wrapperOld.eq(Address::getDefaultAddress, DefaultAddress.DEFAULT_ADDRESS.getValue());
            wrapperOld.eq(Address::getUserId, address.getUserId());
            wrapperOld.set(Address::getDefaultAddress, DefaultAddress.NOT_DEFAULT_ADDRESS.getValue());
            //更新之前先把以前的默认地址修改
            int rows = addressMapper.update(wrapperOld);
            if (rows == 0) {
                throw new BizException(ResultCode.DB_UPDATE_ERROR);
            }
        } else {
            //用户想将该地址设置为非默认地址
            //查询数据库中默认地址
            LambdaQueryWrapper<Address> wrapperSelectDefault = new LambdaQueryWrapper<>();
            wrapperSelectDefault.eq(Address::getDefaultAddress, DefaultAddress.DEFAULT_ADDRESS.getValue());
            wrapperSelectDefault.eq(Address::getUserId, userId);
            Address addressDefault = null;
            try {
                addressDefault = addressMapper.selectOne(wrapperSelectDefault);
            } catch (Exception e) {
                throw new BizException(ResultCode.DB_SELECT_ONE_ERROR);
            }
            //如果该地址是数据库中的默认地址，并且用户想将该地址改为非默认地址
            if (addressDefault.getId().equals(address.getId())) {
                //将数据库中最近一次更新的非默认地址设置成为默认地址
                setDefaultAddress(userId);
            }
        }
        //往数据库添加地址信息
        return addressMapper.updateById(address);
    }

    @Override
    public Map<String, Object> selectAddress(Long userId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, userId);
        //按照是否为默认地址降序排列
        wrapper.orderByDesc(Address::getDefaultAddress);
        List<Address> addresses = addressMapper.selectList(wrapper);
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("addressList", addresses);
        return addressMap;
    }

    @Override
    public int deleteAddress(Long userId, Long addressId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getId, addressId);
        wrapper.eq(Address::getUserId, userId);
        Address address = addressMapper.selectOne(wrapper);
        //未查询到地址信息
        if (address == null) {
            throw new BizException(ResultCode.DB_SELECT_ERROR);
        }
        //如果该地址是默认地址，将数据库中最近一次更新的非默认地址设置成为默认地址
        if (address.getDefaultAddress().equals(DefaultAddress.DEFAULT_ADDRESS.getValue())) {
            setDefaultAddress(userId);
        }
        //删除该地址
        return addressMapper.deleteById(address);
    }

    private void setDefaultAddress(Long userId) {
        //查询最后一次更新的地址，将其设置为默认地址
        LambdaQueryWrapper<Address> wrapperOld = new LambdaQueryWrapper<>();
        wrapperOld.eq(Address::getUserId, userId);
        wrapperOld.eq(Address::getDefaultAddress, DefaultAddress.NOT_DEFAULT_ADDRESS.getValue());
        wrapperOld.orderByDesc(Address::getUpdateTime);
        wrapperOld.last("Limit 1");
        Address addressLatestUpdate;
        try {
            addressLatestUpdate = addressMapper.selectOne(wrapperOld);
        } catch (Exception e) {
            throw new BizException(ResultCode.DB_SELECT_ONE_ERROR);
        }
        //如果存在最后一次更新的地址
        if (addressLatestUpdate != null) {
            LambdaUpdateWrapper<Address> wrapperUpdateDefault = new LambdaUpdateWrapper<>();
            wrapperUpdateDefault.eq(Address::getId, addressLatestUpdate.getId());
            wrapperUpdateDefault.set(Address::getDefaultAddress, DefaultAddress.DEFAULT_ADDRESS.getValue());
            int rows = addressMapper.update(wrapperUpdateDefault);
            if (rows == 0) {
                throw new BizException(ResultCode.DB_UPDATE_ERROR);
            }
        }
    }
}




