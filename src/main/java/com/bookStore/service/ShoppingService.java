package com.bookStore.service;

import com.bookStore.pojo.Shopping;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 邓桂材
* @description 针对表【shopping(购物车)】的数据库操作Service
* @createDate 2024-01-14 23:34:44
*/
public interface ShoppingService extends IService<Shopping> {
   /**
    * 通过用户id查询该用户的购物车信息
    * @param userId
    * @return
    */
   Map<String,Object> findAllByUserId(Integer currentPage,Integer size,Long userId);

   /**
    * 添加购物车信息
    * @param shopping
    * @return
    */
   Integer addShopping(Shopping shopping);

   /**
    * 批量删除购物车信息 可以删除多个，也可以删除一个
    */
   Integer deleteShopping(Long userId,List<String> bookIdsList);

   /**
    * 更新购物车
    */
   Integer updateShopping(Long userId,Shopping shopping);


}
