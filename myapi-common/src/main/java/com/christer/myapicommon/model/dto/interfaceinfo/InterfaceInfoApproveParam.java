package com.christer.myapicommon.model.dto.interfaceinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-17 21:10
 * Description:
 * 接口审核，请求参数
 */
@Setter
@Getter
@ToString
public class InterfaceInfoApproveParam {

    /**
     * 接口申请id
     */
    @NotNull(message = "接口申请id不能为空")
    private Long id;
    /**
     * 流程实例id
     */
    @NotBlank(message = "流程实例id不能为空！")
    private String processInstanceId;
    /**
     *审核结果
     */
    private String auditResult;
    /**
     * 审核意见
     */
    private String auditOpinion;
    /**
     * 申请人id
     */
    private Long createUserId;
    /**
     * 审核用户id
     */
    private Long auditUserId;

}
