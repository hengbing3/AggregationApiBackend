package com.christer.project.util;

import com.christer.project.exception.BusinessException;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.christer.project.common.ResultCode.PARAMS_VALIDATE_ERROR;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-25 21:08
 * Description:
 * bean 校验工具类
 */
public class ValidateUtil {

    private static final LocalValidatorFactoryBean validator;

    static {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
    }

    private ValidateUtil() {
        // 私有构造函数，禁止实例化
    }

    public static <T> void validateBeanList(List<T> objectList, Class<?>... groups) {
        Assert.notEmpty(objectList, "objects is not empty!");
        List<String> errors = Lists.newArrayList();
        for (T object : objectList) {
            Set<ConstraintViolation<T>> violations = validator.validate(object, groups);
            if (!CollectionUtils.isEmpty(violations)) {
                final String errorMessage = violations.stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                errors.add(errorMessage);
            }
        }
        if (!CollectionUtils.isEmpty(errors)) {
            throw new BusinessException(PARAMS_VALIDATE_ERROR.getCode(), errors.toString());
        }
    }

    public static <T> void validateBean(T object, Class<?>... groups) {
        Assert.notNull(object, "object is not null!");
        final Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        if (!CollectionUtils.isEmpty(validate)) {
            final String errorMessage = validate.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new BusinessException(PARAMS_VALIDATE_ERROR.getCode(), errorMessage);
        }
    }
}
