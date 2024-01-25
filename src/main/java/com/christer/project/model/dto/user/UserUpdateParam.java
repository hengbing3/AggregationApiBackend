package com.christer.project.model.dto.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-25 21:49
 * Description:
 * 用户信息更新参数
 */
@Setter
@Getter
@ToString
public class UserUpdateParam implements Serializable {

    @NonNull
    private Long id;
    /**
     * 用户昵称
     */
    @NotBlank
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户简介
     */
    private String userProfile;
}
