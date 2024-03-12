package com.christer.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.christer.myapicommon.model.dto.department.DepartmentAddParam;
import com.christer.myapicommon.model.dto.department.DepartmentEditParam;
import com.christer.myapicommon.model.dto.department.DepartmentPageParam;
import com.christer.myapicommon.model.entity.DepartmentEntity;
import com.christer.myapicommon.model.vo.DepartmentVO;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-10 16:40
 * Description:
 */
public interface DepartmentService extends IService<DepartmentEntity> {
    boolean addDepartment(DepartmentAddParam param);

    boolean editDepartment(DepartmentEditParam param);

    /**
     * 删除部门
     * @param id
     * @return
     */
    boolean deleteDepartment(Long id);

    /**
     * 分页查询部门信息
     * @param param
     * @return
     */
    Page<DepartmentVO> selectDepartmentPage(DepartmentPageParam param);
}
