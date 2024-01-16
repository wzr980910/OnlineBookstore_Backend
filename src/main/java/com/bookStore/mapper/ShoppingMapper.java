package com.bookStore.mapper;

import com.bookStore.pojo.Shopping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 邓桂材
* @description 针对表【shopping(购物车)】的数据库操作Mapper
* @createDate 2024-01-14 23:34:44
* @Entity com.bookStore.pojo.Shopping
*/
@Mapper
public interface ShoppingMapper extends BaseMapper<Shopping> {

}




