package com.christer.myapicommon.model.dto.interfaceinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-17 22:00
 * Description:
 * 完成任务，请求参数
 */
@Setter
@Getter
@ToString
public class FlowableCompleteTaskParam {
    /**
     * 流程实例id
     */
    @NotBlank(message = "流程实例id不能为空")
    private String processInstanceId;
    /**
     * 任务分配人
     */
    private String assigneeUser;
    /**
     * 候选组里的用户
     */
    private String candidateGroupUser;
    /**
     * 审核结果
     * 1-通过
     * 0-不通过
     */
    private String auditResult;
}
