package com.bookStore.service;

import com.bookStore.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

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
    Map<String,Object> queryAllType();

    /**
     * 通过父类id查询图书类别
     * @return
     */
    List<Type> queryByParentId(Long parentId);

    /**
     * 添加父类类别
     */
    Integer insertParentType(String parentType);

    /**
     * 添加子类类别
     * @param type
     * @return
     */
    Integer insertType(Type type);

    /**
     * 通过id删除类别
     */
    Integer deleteType(Long typeId);

    /**
     * 更新图书类别
     */
    Integer updateType(Type type);


}
