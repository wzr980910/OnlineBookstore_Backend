package com.bookStore.pojo.pojoenum;

/**
 * ClassName: Order
 * Package: com.bookStore.pojo.pojoenum
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/17 -21:46
 * @Version: v1.0
 */
public enum OrderMethod {
    ASC,
    DESC,
    NO;
    private Integer code;

    @Override
    public String toString() {
        return ""+code;
    }
}
