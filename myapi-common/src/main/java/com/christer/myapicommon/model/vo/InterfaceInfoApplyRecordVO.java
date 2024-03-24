package com.christer.myapicommon.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-16 16:40
 * Description:
 */
@Setter
@Getter
@ToString
public class InterfaceInfoApplyRecordVO {

    /**
     * ID
     */
    private Long id;

    /**
     * 接口申请ID
     */
    private Long interfaceInfoApplyId;

    /**
     * 流程节点名称
     */
    private String processNode;
    /**
     * 流程节点id
     */
    private String processNodeId;

    /**
     * 审核结果
     * 1：通过
     * 0：不通过
     */
    private String auditResult;
    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 审核用户ID
     */
    private Long auditUserId;
    /**
     * 审核用户名称
     */
    private String auditUserName;

    /**
     * 申请人ID
     */
    private Long createUserId;
    /**
     * 申请用户名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
