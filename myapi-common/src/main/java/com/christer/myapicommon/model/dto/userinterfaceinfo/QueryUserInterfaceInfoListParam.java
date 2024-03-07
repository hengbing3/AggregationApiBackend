package com.christer.myapicommon.model.dto.userinterfaceinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-09 16:23
 * Description:
 */
@Setter
@Getter
@ToString
public class QueryUserInterfaceInfoListParam {

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
