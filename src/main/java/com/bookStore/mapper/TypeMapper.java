package com.bookStore.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bookStore.pojo.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookStore.pojo.respojo.TypeBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 邓桂材
* @description 针对表【type(图书类型表)】的数据库操作Mapper
* @createDate 2024-01-17 10:40:09
* @Entity com.bookStore.pojo.Type
*/
@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    List<TypeBook> selectAllType();

}




