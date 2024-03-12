package com.christer.myapicommon.model.dto.department;

import com.christer.myapicommon.common.PageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-11 23:27
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentPageParam extends PageCondition {
    /**
     * 部门名称
     */
    private String deptName;
}
