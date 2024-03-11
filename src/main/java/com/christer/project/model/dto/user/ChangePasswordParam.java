package com.christer.project.model.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-11 10:47
 * Description:
 */
@Setter
@Getter
@ToString
public class ChangePasswordParam {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空！")
    private Long id;
    /**
     * 密码
     */
    @Length(max = 16, message = "密码最大支持16位！")
    @NotBlank(message = "密码不能为空！")
    private String userPassword;
    /**
     * 确认密码
     */
    @Length(max = 16, message = "密码最大支持16位！")
    @NotBlank(message = "密码不能为空！")
    private String confirmPassword;
}
