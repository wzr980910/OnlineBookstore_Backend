package com.bookStore.pojo.pojoenum;/**
 * ClassName: DefaultAddress
 * Package: com.bookStore.pojo.addressenum
 * Description:
 * @Author: 邓桂材
 * @Create: 2024/1/16 -14:32
 * @Version: v1.0
 *
*/public enum DefaultAddress {
    DEFAULT_ADDRESS(1),
    NOT_DEFAULT_ADDRESS(0);

    private Integer value;

    public Integer getValue() {
        return value;
    }

    DefaultAddress(Integer value) {
        this.value = value;
    }
}
