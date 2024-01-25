package com.bookStore.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: MethodLog
 * Package: com.bookStore.log
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/25 -15:31
 * @Version: v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodLog {
}
