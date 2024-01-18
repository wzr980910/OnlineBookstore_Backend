package com.bookStore.pojo.strategy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName: ComprehensiveOrder
 * Package: com.bookStore.pojo.strategy
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/17 -20:48
 * @Version: v1.0
 */

@Data
@Component
public class ComprehensiveOrder {
    @Value("${comprehensiveOrder.saleVolumeDiscount}")
    private Double saleVolumeDiscount;
    @Value("${comprehensiveOrder.priceDiscount}")
    private Double priceDiscount;
}
