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
    //升序
    ASC(1),
    //降序
    DESC(2),
    //不排序
    NO(0);
    private Integer value;

    OrderMethod(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
