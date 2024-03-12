package com.christer.myapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-11 19:58
 * Description:
 */
@Setter
@Getter
@ToString
@TableName("sys_department")
public class DepartmentEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 父级部门id
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Boolean deletedFlag;
}
