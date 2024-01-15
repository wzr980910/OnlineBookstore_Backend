package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.mapper.BookMapper;
import com.bookStore.mapper.PublishingHouseMapper;
import com.bookStore.pojo.Book;
import com.bookStore.pojo.Shopping;
import com.bookStore.pojo.respojo.CartBook;
import com.bookStore.service.ShoppingService;
import com.bookStore.mapper.ShoppingMapper;
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

    private ShoppingMapper shoppingMapper;
    private BookMapper bookMapper;
    private PublishingHouseMapper publishingHouseMapper;

    @Autowired
    public void setShoppingMapper(ShoppingMapper shoppingMapper) {
        this.shoppingMapper = shoppingMapper;
    }

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Autowired
    public void setPublishingHouseMapper(PublishingHouseMapper publishingHouseMapper) {
        this.publishingHouseMapper = publishingHouseMapper;
    }

    @Override
    public Map<String, Object> findAllByAccountName(String accountName) {
        QueryWrapper<Shopping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", accountName);
        //查询出该用户的所有购物车信息
        List<Shopping> shoppingList = shoppingMapper.selectList(queryWrapper);
        List<CartBook> cartBooks = new ArrayList<>();
        for (Shopping shop :
                shoppingList) {
            CartBook cartBook = new CartBook();
            Book book = bookMapper.selectById(shop.getBookId());
            cartBook.setBookName(book.getBookName());
            cartBook.setImg(book.getPicture());
            cartBook.setPublishName(publishingHouseMapper.selectById(book.getPublishId()).getPublishName());
            cartBook.setNumber(shop.getNumber());
            cartBook.setPrice(shop.getPrice());
            cartBooks.add(cartBook);
        }
        //查询购物车物品的数量
        Long num = shoppingMapper.selectCount(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("cartBooks", cartBooks);
        map.put("num", num);
        return map;
    }

    @Override
    public Integer addShopping(Shopping shopping) {
        return shoppingMapper.insert(shopping);
    }



}




