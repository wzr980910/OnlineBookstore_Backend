package com.bookStore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/20:25
 * @Description:送货地址实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address {

    private String address;                     //快递地址

    private String userNumber;                  //用户id

}
