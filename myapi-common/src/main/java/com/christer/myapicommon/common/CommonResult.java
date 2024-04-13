package com.christer.myapicommon.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-13 16:11
 * Description:
 */
@Setter
@Getter
@ToString
public class CommonResult<T> implements Serializable {

    // 自定义状态码
    private Integer code;
    // 提示内容，如果接口出错，则存放异常信息
    private String message;
    // 返回数据体
    private T data;

    private static final long serialVersionUID = 1545698833843378645L;

    public CommonResult() {
    }

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


}