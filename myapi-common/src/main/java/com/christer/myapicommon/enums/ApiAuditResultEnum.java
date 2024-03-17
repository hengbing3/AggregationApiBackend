package com.christer.myapicommon.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-17 14:22
 * Description:
 * API审核结果枚举
 */
@Getter
@NoArgsConstructor
public enum ApiAuditResultEnum {

    NO_PASS("0", "审核不通过"),
    PASS("1", "审核通过");

    private String code;

    private String value;

    ApiAuditResultEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
