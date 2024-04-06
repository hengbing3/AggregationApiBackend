package com.christer.project.model.dto.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-06 17:30
 * Description:
 */
@Setter
@Getter
@ToString
public class MyUserUpdateParam implements Serializable {

    @NotNull
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
