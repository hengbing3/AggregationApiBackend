package com.christer.myapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-16 16:17
 * Description:
 */
@Setter
@Getter
@ToString
@TableName("t_interface_info_apply_record")
public class InterfaceInfoApplyRecord {

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
     * 审核用户ID
     */
    private Long auditUserId;

    /**
     * 申请人ID
     */
    private Long createUserId;

    /**
     * 更新人ID
     */
    private Long updateUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer deletedFlag;
}
