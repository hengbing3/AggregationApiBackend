package com.christer.myapicommon.model.dto.interfaceinfo;

import com.christer.myapicommon.common.PageCondition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-17 14:15
 * Description:
 * 接口申请-分页查询参数
 */
@Setter
@Getter
@ToString(callSuper = true)
public class InterfaceInfoApplyQueryParam extends PageCondition {


    /**
     * 申请人名称
     */
    private String createUserName;
    /**
     * 当前用户id
     */
    private Long currentUserId;
    /**
     * 流程实例id列表
     */
    private List<String> processInstanceIds;
}
