package com.christer.project.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.christer.project.util.ValidateGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-24 10:19
 * Description:
 */
@Setter
@Getter
@ToString
public class InterfaceInfoParam implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @NotNull(groups = {ValidateGroup.Update.class})
    private Long id;

    /**
     * 名称
     */
    @NotBlank(groups = {ValidateGroup.Save.class,ValidateGroup.Update.class})
    @Length(max = 20, message = "接口名称过长！",groups = {ValidateGroup.Save.class,ValidateGroup.Update.class})
    private String name;

    /**
     * 描述
     */
    @NotBlank(groups = {ValidateGroup.Save.class,ValidateGroup.Update.class})
    private String description;

    /**
     * 接口地址
     */
    @NotBlank(groups = {ValidateGroup.Save.class,ValidateGroup.Update.class})
    private String url;

    /**
     * 请求参数
     */
    @NotBlank(groups = {ValidateGroup.Save.class,ValidateGroup.Update.class})
    private String requestParams;

    /**
     * 请求头
     */
    @NotBlank(groups = {ValidateGroup.Save.class,ValidateGroup.Update.class})
    private String requestHeader;

    /**
     * 响应头
     */
    @NotBlank(groups = {ValidateGroup.Save.class,ValidateGroup.Update.class})
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    @NotBlank(groups = {ValidateGroup.Save.class,ValidateGroup.Update.class})
    private String method;

    /**
     * 创建人id
     */
    private Long createUserId;
    /**
     * 更新人id
     */
    private Long updateUserId;


}
