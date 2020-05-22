

package com.github.east196.core.util;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.github.east196.ezsb.core.BootException;

import java.util.Set;

/**
 * hibernate-validator校验工具类
 * <p>
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author east196
 */
public class ValidatorUtil {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws BootException 校验不通过，则报 BootException 异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws BootException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new BootException(msg.toString());
        }
    }
}
