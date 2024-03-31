package com.christer.myapicommon.model.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-31 21:20
 * Description:
 */
@Setter
@Getter
@ToString
public class UserAddParam {

    private Integer id;
    /**
     * 账号
     */
    @Length(max = 16, message = "账号最大支持16位！")
    @NotBlank(message = "账户不能为空！")
    private String userAccount;
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
    @NotBlank(message = "请确认密码！")
    private String checkPassword;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空！")
    private String userName;
    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空！")
    private String userAvatar;
    /**
     * 用户简介
     */
    private String userProfile;
    /**
     * 用户角色
     */
    @NotBlank(message = "用户角色不能为空！")
    private String userRole;
    /**
     * 部门
     */
    @NotNull(message = "部门不能为空！")
    private Integer departmentId;
}
