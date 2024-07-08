package com.christer.project.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-07-08 22:04
 * Description:
 * 产品信息表
 */
@Setter
@Getter
@ToString
@TableName("t_payment_info")
public class ProductInfoEntity {

    /**
     * 产品的唯一标识。
     */
    private Long id;

    /**
     * 产品名称。
     */
    private String name;

    /**
     * 产品描述。
     */
    private String description;

    /**
     * 金额（分）。
     */
    private Long total;

    /**
     * 增加的积分个数。
     */
    private Long addPoints;

    /**
     * 产品类型（0-会员，1-充值，2-充值活动）。
     */
    private String productType;

    /**
     * 过期时间。
     */
    private LocalDateTime expirationTime;

    /**
     * 创建人ID。
     */
    private Long createUserId;

    /**
     * 更新人ID。
     */
    private Long updateUserId;

    /**
     * 创建时间。
     */
    private LocalDateTime createTime;

    /**
     * 更新时间。
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除标识（0-未删除，1-已删除）。
     */
    private Integer deletedFlag;
}
