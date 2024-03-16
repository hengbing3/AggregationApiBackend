package com.christer.myapicommon.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-16 16:09
 * Description:
 * 接口审核枚举
 */
@Getter
@NoArgsConstructor
public enum ApiAuditStatusEnum {


    /**
     * 审核中
     */
    AUDITING("0", "审核中"),

    /**
     * 审核通过
     */
    PASS("1", "审核通过"),

    /**
     * 审核不通过
     */
    NOT_PASS("2", "审核不通过");

    private String code;

    private String value;


    ApiAuditStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ApiAuditStatusEnum getByCode(String code) {
        for (ApiAuditStatusEnum apiAuditStatusEnum : ApiAuditStatusEnum.values()) {
            if (apiAuditStatusEnum.getCode().equals(code)) {
                return apiAuditStatusEnum;
            }
        }
        return null;
    }
}
