package com.bookStore.pojo.respojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wmh
 * @date 2024/01/19 17:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeBook {
    private String type;
    private String parentType;
}
