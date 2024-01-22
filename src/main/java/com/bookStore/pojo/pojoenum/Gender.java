package com.bookStore.pojo.pojoenum;

public enum Gender {
    /*性别未知*/
    UNK_NOWN(0),
    /*性别男*/
    MALE(1),
    /*性别女*/
    FEMALE(2);

    private Integer value;

    Gender(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}