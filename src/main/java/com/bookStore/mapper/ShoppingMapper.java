package com.bookStore.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookStore.pojo.Shopping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookStore.pojo.respojo.CartBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 邓桂材
* @description 针对表【shopping(购物车)】的数据库操作Mapper
* @createDate 2024-01-14 23:34:44
* @Entity com.bookStore.pojo.Shopping
*/
@Mapper
public interface ShoppingMapper extends BaseMapper<Shopping> {

    IPage<CartBook> selectPageShopping(IPage<?> page,Long userId);

    int updateShopping(Integer number,Long bookId);

    int deleteShopping(@Param("userId") Long userId, @Param("bookIdsList") List<String> bookIdsList);

}




