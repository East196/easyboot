package com.github.east196.ezsb.dds;

import java.lang.annotation.*;

/**
 * 多数据源注解
 *
 * @author east196
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    String value() default "";
}
