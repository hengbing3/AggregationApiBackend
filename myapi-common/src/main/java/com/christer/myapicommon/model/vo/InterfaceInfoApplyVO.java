package com.christer.myapicommon.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-23 16:07
 * Description:
 */
@Setter
@Getter
@ToString
public class InterfaceInfoApplyVO {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求类型
     */
    private String method;
    /**
     * 审核状态
     */
    private String auditStatus;
    /**
     * 创建时间
     */
    private Date createTime;
}
