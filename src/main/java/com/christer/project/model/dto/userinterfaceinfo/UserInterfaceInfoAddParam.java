package com.christer.project.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-24 10:19
 * Description:
 * 用户调用接口信息新增
 */
@Setter
@Getter
@ToString
public class UserInterfaceInfoAddParam implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 调用用户 id
     */
    @NotNull
    private Long userId;

    /**
     * 接口 id
     */
    @NotNull
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;



}
