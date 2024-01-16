package com.bookStore.mapper;

import com.bookStore.pojo.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 邓桂材
* @description 针对表【type(图书类型表)】的数据库操作Mapper
* @createDate 2024-01-14 16:56:54
* @Entity com.bookStore.pojo.Type
*/
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

}




