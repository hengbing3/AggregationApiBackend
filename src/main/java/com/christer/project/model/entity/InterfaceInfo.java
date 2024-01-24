package com.christer.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Setter
@Getter
@ToString
public class InterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人id
     */
    private Long createUserId;
    /**
     * 更新人id
     */
    private Long updateUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer deletedFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}