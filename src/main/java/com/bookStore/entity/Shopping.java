package com.bookStore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/20:41
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shopping {

    private Integer ID;             //购物车id

    private Integer bookID;         //图书id

    private Integer userNumber;     //用户id

}
