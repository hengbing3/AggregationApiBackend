package com.christer.project.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-24 10:19
 * Description:
 * 用户调用接口信息更新
 */
@Setter
@Getter
@ToString
public class UserInterfaceInfoUpdateParam implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @NotNull
    private Long id;
    /**
     * 总调用次数
     */
    @NotNull
    private Integer totalNum;
    /**
     * 剩余调用次数
     */
    @NotNull
    private Integer leftNum;
    /**
     * 0-正常，1-禁用
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date updateTime;

}
