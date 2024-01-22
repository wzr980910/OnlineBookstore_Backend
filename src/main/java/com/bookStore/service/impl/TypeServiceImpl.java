package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Type;
import com.bookStore.pojo.respojo.TypeBook;
import com.bookStore.service.TypeService;
import com.bookStore.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邓桂材
 * @description 针对表【type(图书类型表)】的数据库操作Service实现
 * @createDate 2024-01-17 10:40:09
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
        implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Map<String, Object> queryAllType(Integer currentPage, Integer size) {
        Page<TypeBook> page = new Page<>(currentPage, size);
        IPage<TypeBook> typeBookIPage = typeMapper.selectPageType(page);
        //封装查询到的内容
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageInfo",typeBookIPage);
        return pageInfo;
    }

    @Override
    public List<Type> queryByParentId(Long parentId) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentId", parentId);
        List<Type> types = typeMapper.selectList(queryWrapper);
        return types;
    }

    @Override
    public Integer insertParentType(String parentType) {
        Type type = new Type();
        type.setType(parentType);
        //父类的parentId为 0;
        type.setParentId(Long.valueOf("0"));
        int insert = typeMapper.insert(type);
        return insert;
    }

    @Override
    public Integer insertType(Type type) {
        int insert = typeMapper.insert(type);
        return insert;
    }

    @Override
    public Integer deleteType(Long typeId) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentId", typeId);
        Long count = typeMapper.selectCount(queryWrapper);
        if (count == 0) {
            return typeMapper.deleteById(typeId);
        }
        //删除所有子类级别
        typeMapper.delete(queryWrapper);
        //删除父类级别
        return typeMapper.deleteById(typeId);
    }

    @Override
    public Integer updateType(Type type) {
        int rows = typeMapper.updateById(type);
        return rows;
    }
}




