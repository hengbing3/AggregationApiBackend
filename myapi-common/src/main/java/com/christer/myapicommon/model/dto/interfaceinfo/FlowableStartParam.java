package com.christer.myapicommon.model.dto.interfaceinfo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-16 14:00
 * Description:
 * 工作流启动时需要的参数
 */
@Setter
@Getter
public class FlowableStartParam {

    /**
     * 流程实例的key
     */
    @NotBlank(message = "流程实例的key不能为空")
    private String processInstanceKey;
    /**
     * 申请用户id
     */
    @NotBlank(message = "申请用户id不能为空")
    private String assigneeUser;

}
