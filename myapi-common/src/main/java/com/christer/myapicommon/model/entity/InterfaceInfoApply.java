package com.christer.myapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-14 23:10
 * Description:
 */
@Setter
@Getter
@ToString
@TableName("t_interface_info_apply")
public class InterfaceInfoApply {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流程实例id
     */
    private String processInstanceId;

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
     * 请求类型
     */
    private String method;

    /**
     * 代码块
     */
    private String codeJson;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 申请人
     */
    private Long createUserId;

    /**
     * 更新人
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
     * 是否删除
     */
    private int deletedFlag;
}
