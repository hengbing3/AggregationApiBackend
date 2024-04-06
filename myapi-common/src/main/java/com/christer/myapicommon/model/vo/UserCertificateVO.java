package com.christer.myapicommon.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-06 16:08
 * Description:
 * 用户调用接口凭证
 */
@Setter
@Getter
@ToString
public class UserCertificateVO {

    private String accessKey;

    private String secretKey;
}
