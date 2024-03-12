package com.christer.myapicommon.model.vo;

import com.christer.myapicommon.model.entity.DepartmentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-11 23:20
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentVO extends DepartmentEntity {

    /**
     * 父级部门名称
     */
    private String parentDeptName;


}
