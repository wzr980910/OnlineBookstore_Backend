package com.bookStore.pojo.pojoenum;

/**
 * @author wmh
 * @date 2024/01/22 11:11
 */
public enum DisplaySlides {
    /**
     * 不展示  默认不展示
     */
    NOT_DISPLAY(0),
    /**
     * 展示
     */
    DISPLAY(1);
    private Integer code;

    DisplaySlides(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
