package com.bookStore.pojo.pojoenum;

public enum Gender {
    /*性别男*/
    MALE(1),
    /*性别女*/
    FEMALE(2);


    private Integer code;

    Gender(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}