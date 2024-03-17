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
     * API审核中
     */
    AUDITING("0", "API审核中"),
    /**
     * API开放审核中
     */
    API_OPEN_AUDITING("1", "API开放审核中"),

    /**
     * API审核不通过
     */
    API_AUDIT_NOT_PASS("2", "API审核不通过"),
    /**
     * API开放不通过
     */
    API_OPEN_NOT_PASS("3","API开放不通过"),
    /**
     * API申请通过
     */
    API_APPLY_PASS("4","API申请通过");

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
