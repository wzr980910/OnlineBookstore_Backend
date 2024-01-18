package com.bookStore.pojo.pojoenum;


/**
 * @author wmh
 * @date 2024/01/17 18:34
 */
public enum OrderStatus {
    /**
     * 待付款
     */
    WAIT_PAYMENT(0),
    /**
     * 待发货
     */
    WAIT_SEND(1),
    /**
     * 待收货
     */
    WAIT_RECEIVE(2);

    private Integer code;

    OrderStatus(Integer code){
        this.code = code;
    }
    public Integer getCode(){
        return this.code;
    }

}
