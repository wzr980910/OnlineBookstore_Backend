package com.bookStore.pojo.pay.wxpay;

import lombok.Data;

@Data
public class ResourceDto {

    private String algorithm;
    private String ciphertext;
    private String associated_data;
    private String original_type;
    private String nonce;

}