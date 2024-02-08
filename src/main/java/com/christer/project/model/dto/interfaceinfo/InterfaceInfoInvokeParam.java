package com.christer.project.model.dto.interfaceinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-08 17:54
 * Description:
 */
@Setter
@Getter
@ToString
public class InterfaceInfoInvokeParam {
    /**
     * 主键id
     */
    @NotNull(message = "接口id不能为空！")
    private Long id;
    /**
     * 用户请求参数
     */
    private String userRequestParams;
}
