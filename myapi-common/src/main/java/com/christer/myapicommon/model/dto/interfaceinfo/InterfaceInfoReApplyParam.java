package com.christer.myapicommon.model.dto.interfaceinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-18 21:32
 * Description:
 */
@Setter
@Getter
@ToString
public class InterfaceInfoReApplyParam {

    /**
     * 主键
     */
    @NotNull(message = "接口id不能为空！")
    private Long id;

    /**
     * 名称
     */
    @NotBlank
    @Length(max = 20, message = "接口名称过长！")
    private String name;
    /**
     * 代码json串-代码块
     */
    @NotBlank(message = "代码快不能为空！")
    private String codeJson;

    /**
     * 描述
     */
    @NotBlank(message = "接口描述不能为空！")
    private String description;

    /**
     * 接口地址
     */
    @NotBlank(message = "接口地址不能为空！")
    private String url;

    /**
     * 请求参数
     */
    @NotBlank(message = "请求参数不能为空！")
    private String requestParams;

    /**
     * 请求头
     */
    @NotBlank(message = "请求头不能为空！")
    private String requestHeader;

    /**
     * 响应头
     */
    @NotBlank(message = "响应头不能为空！")
    private String responseHeader;

    /**
     * 请求类型
     */
    @NotBlank(message = "请求类型不能为空！")
    private String method;

    /**
     * 创建人id
     */
    private Long createUserId;
    /**
     * 更新人id
     */
    private Long updateUserId;
}
