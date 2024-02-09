package com.christer.project.model.dto.userinterfaceinfo;

import com.christer.project.common.PageCondition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-23 22:33
 * Description:
 * 用户调用接口信息-分页查询参数
 */
@Setter
@Getter
@ToString(callSuper = true)
public class QueryUserInterfaceInfoParam extends PageCondition {
    /**
     * 主键
     */
    private Long id;

    /**
     * 调用用户 id
     */
    private Long userId;

    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-正常，1-禁用
     */
    private Integer status;
}
