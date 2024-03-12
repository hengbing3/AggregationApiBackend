package com.christer.myapicommon.model.dto.department;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-11 20:12
 * Description:
 */
@Setter
@Getter
@ToString
public class DepartmentEditParam {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 父级部门id
     */
    @NotNull(message = "父级部门id不能为空")
    private Long parentId;
    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
}
