package com.christer.myapicommon.model.dto.interfaceinfo;


import com.christer.myapicommon.common.PageCondition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-23 22:33
 * Description:
 * 接口信息-分页查询参数
 */
@Setter
@Getter
@ToString(callSuper = true)
public class QueryInterfaceInfoParam extends PageCondition {
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
}
