package com.bookStore.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author wmh
 * @date 2024/01/18 16:06
 */
@Component
public class OrderNumberGeneratorUtil {
    private static final String DATE_FORMAT = "yyMMddHHmmss";
    private static final String ORDER_NUMBER_CHARS = "0123456789";
    private static final int ORDER_NUMBER_LENGTH = 3;

    public static Long generateOrderNumber() {
        StringBuilder orderNumber = new StringBuilder();

        // 添加日期部分
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        orderNumber.append(dateFormat.format(new Date()));

        // 添加随机字符部分
        Random random = new Random();
        for (int i = 0; i < ORDER_NUMBER_LENGTH; i++) {
            int randomIndex = random.nextInt(ORDER_NUMBER_CHARS.length());
            orderNumber.append(ORDER_NUMBER_CHARS.charAt(randomIndex));
        }

        return Long.valueOf(orderNumber.toString());
    }

}
