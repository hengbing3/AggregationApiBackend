package com.christer.myapicommon.model.dto.interfaceinfo;

import com.christer.myapicommon.common.PageCondition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-23 16:10
 * Description:
 * 我的接口申请-分页查询-请求参数
 */
@Setter
@Getter
@ToString
public class MyInterfaceInfoApplyQueryParam extends PageCondition {

    /**
     * 名称
     */
    private String name;
    /**
     * 审核状态
     */
    private String auditStatus;
    /**
     * 请求类型
     */
    private String method;
    /**
     * 描述
     */
    private String description;
    /**
     * 当前用户id
     */
    private Long currentUserId;
}
