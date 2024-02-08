package com.christer.project.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-07 14:08
 * Description:
 * 接口信息枚举
 */
@Getter
public enum InterfaceInfoStatusEnum {
    OFFLINE(0, "下线"),
    ONLINE(1, "上线");


    private final Integer code;

    private final String value;


    InterfaceInfoStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
}
