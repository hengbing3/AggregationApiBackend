package com.christer.project.model.vo;

import com.christer.myapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 21:44
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Integer totalNum;
}
