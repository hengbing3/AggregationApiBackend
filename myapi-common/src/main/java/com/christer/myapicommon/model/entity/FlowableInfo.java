package com.christer.myapicommon.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-16 15:45
 * Description:
 * 工作流实体相关信息
 */
@Getter
@Setter
@ToString
public class FlowableInfo {

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务执行人编号
     */
    private String assignee;

    /**
     * 任务执行人名称
     */
    private String assigneeName;
}
