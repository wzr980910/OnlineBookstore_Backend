package com.bookStore.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bookStore.pojo.OrdersShow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookStore.pojo.pojoenum.OrderStatus;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 邓桂材
 * @description 针对表【orders_show(订单详情表)】的数据库操作Mapper
 * @createDate 2024-01-14 16:56:54
 * @Entity com.bookStore.pojo.OrdersShow
 */
@Mapper
public interface OrdersShowMapper extends BaseMapper<OrdersShow> {
    IPage<OrdersShow> selectOrderPage(IPage<?> page, Long userId, Long orderId, String bookName);

    IPage<OrdersShow> selectOrderPageByStatus(IPage<?> page, Long userId, Integer orderStatus);

}




