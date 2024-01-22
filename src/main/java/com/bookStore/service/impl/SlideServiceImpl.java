package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Slide;
import com.bookStore.service.SlideService;
import com.bookStore.mapper.SlideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邓桂材
 * @description 针对表【slide】的数据库操作Service实现
 * @createDate 2024-01-22 11:08:14
 */
@Service
public class SlideServiceImpl extends ServiceImpl<SlideMapper, Slide>
        implements SlideService {
    @Autowired
    private SlideMapper slideMapper;

    @Override
    public Integer addSlide(Slide slide) {
        int insert = slideMapper.insert(slide);
        return insert;
    }

    @Override
    public Integer updateSlide(Slide slide) {
        int update = slideMapper.updateById(slide);
        return update;
    }

    @Override
    public Map<String,Object> querySlidePage(Integer currentPage, Integer size) {
        Page<Slide> page = new Page<>(currentPage,size);
        IPage<Slide> slideIPage = slideMapper.queryAllPage(page);
        Map<String,Object> pageInfo = new HashMap<>();
        pageInfo.put("pageInfo",slideIPage);
        return pageInfo;
    }

    @Override
    public Integer deleteSlide(List<String> ids) {
        int rows = slideMapper.deleteBatchSlideIds(ids);
        return rows;
    }
}




