package com.bookStore.service;

import com.bookStore.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 邓桂材
* @description 针对表【type(图书类型表)】的数据库操作Service
* @createDate 2024-01-17 10:40:09
*/
public interface TypeService extends IService<Type> {
    /**
     * 查询所有图书类别
     * @return
     */
    List<Type> queryAllType();

    /**
     * 通过父类id查询图书类别
     * @return
     */
    List<Type> queryByParentId(Long parentId);

}
