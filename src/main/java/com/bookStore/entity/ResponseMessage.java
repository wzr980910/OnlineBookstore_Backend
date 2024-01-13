package com.bookStore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/9:46
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private String statusCode;
    private String statusMessage;
    private Map<String,Object> content;

    public ResponseMessage(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
