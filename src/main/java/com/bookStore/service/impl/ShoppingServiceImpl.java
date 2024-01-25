package com.bookStore.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.mapper.BookMapper;
import com.bookStore.mapper.PublishingHouseMapper;
import com.bookStore.mapper.StockMapper;
import com.bookStore.pojo.Shopping;
import com.bookStore.pojo.respojo.CartBook;
import com.bookStore.service.ShoppingService;
import com.bookStore.mapper.ShoppingMapper;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邓桂材
 * @description 针对表【shopping(购物车)】的数据库操作Service实现
 * @createDate 2024-01-14 23:34:44
 */
@Service
public class ShoppingServiceImpl extends ServiceImpl<ShoppingMapper, Shopping>
        implements ShoppingService {
    @Autowired
    private ShoppingMapper shoppingMapper;


    @Override
    public Map<String, Object> findAllByUserId(Integer currentPage, Integer size, Long userId) {
        Page<CartBook> page = new Page<>(currentPage, size);
        IPage<CartBook> cartBookIPage = shoppingMapper.selectPageShopping(page, userId);
        //查询出该用户的所有购物车信息
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageInfo", cartBookIPage);
        return pageInfo;
    }

    @Override
    public Integer addShopping(Shopping shopping) {
        QueryWrapper<Shopping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bookId", shopping.getBookId()).eq("userId", shopping.getUserId());
        Long count = shoppingMapper.selectCount(queryWrapper);
        if (count > 0) {
            //查找已存在的购物车信息
            Shopping newShopping = shoppingMapper.selectOne(queryWrapper);
            Integer number = newShopping.getNumber();
            //更改已存在得图书数量
            shopping.setNumber(shopping.getNumber() + number);
            return shoppingMapper.updateShopping(shopping.getNumber(), shopping.getBookId());
        }
        //添加不存在的购物车信息
        return shoppingMapper.insert(shopping);
    }

    @Override
    public Integer deleteShopping(Long userId, List<String> bookIdsList) {
        int rows = shoppingMapper.deleteShopping(userId, bookIdsList);
        return rows;
    }

    @Override
    public Integer updateShopping(Long userId,Shopping shopping) {
        QueryWrapper<Shopping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",shopping.getId()).eq("userId",userId);
        int update = shoppingMapper.update(shopping,queryWrapper);
        return update;
    }


}




