package com.bookStore.pojo.pay;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: WechatPay
 * Package: com.bookStore.pojo.pay
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -10:07
 * @Version: v1.0
 */
@Builder
@Data
public class Amount {
    private Integer total;
    private String currency;
}
