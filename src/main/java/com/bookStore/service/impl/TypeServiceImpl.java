package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Type;
import com.bookStore.service.TypeService;
import com.bookStore.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Type> queryAllType() {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        List<Type> types = typeMapper.selectList(queryWrapper);
        return types;
    }

    @Override
    public List<Type> queryByParentId(Long parentId) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentId",parentId);
        List<Type> types = typeMapper.selectList(queryWrapper);
        return types;
    }
}




