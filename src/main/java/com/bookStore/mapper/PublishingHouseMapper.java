package com.bookStore.mapper;

import com.bookStore.pojo.PublishingHouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 邓桂材
* @description 针对表【publishing_house(出版社信息)】的数据库操作Mapper
* @createDate 2024-01-14 16:56:54
* @Entity com.bookStore.pojo.PublishingHouse
*/
@Mapper
public interface PublishingHouseMapper extends BaseMapper<PublishingHouse> {

}




