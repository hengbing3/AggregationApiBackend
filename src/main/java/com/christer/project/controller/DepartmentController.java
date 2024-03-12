package com.christer.project.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.christer.myapicommon.model.dto.department.DepartmentAddParam;
import com.christer.myapicommon.model.dto.department.DepartmentEditParam;
import com.christer.myapicommon.model.dto.department.DepartmentPageParam;
import com.christer.myapicommon.model.vo.DepartmentVO;
import com.christer.project.common.CommonResult;
import com.christer.project.common.ResultBody;
import com.christer.project.common.ResultCode;
import com.christer.project.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.christer.project.WebURLConstant.URI_DEPARTMENT;
import static com.christer.project.WebURLConstant.URI_PAGE;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-10 16:40
 * Description:
 */
@RestController
@RequiredArgsConstructor
public class DepartmentController extends AbstractSessionController {

    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    /**
     * 新增部门
     */
    @PostMapping(URI_DEPARTMENT)
    public CommonResult<Void> addDepartment(@RequestBody @Validated DepartmentAddParam param) {
        log.info("新增部门，请求参数:{}", param);
        final boolean flag = departmentService.addDepartment(param);
        return flag ? ResultBody.success() : ResultBody.failed(ResultCode.FAILED);
    }

    /**
     * 编辑部门
     */
    @PutMapping(URI_DEPARTMENT)
    public CommonResult<Void> editDepartment(@RequestBody @Validated DepartmentEditParam param) {
        log.info("编辑部门，请求参数:{}", param);
        final boolean flag = departmentService.editDepartment(param);
        return flag ? ResultBody.success() : ResultBody.failed(ResultCode.FAILED);
    }

    /**
     * 删除部门
     */
    @DeleteMapping(URI_DEPARTMENT)
    public CommonResult<Void> deleteDepartment(@RequestParam Long id) {
        log.info("删除部门，请求参数:{}", id);
        final boolean flag = departmentService.deleteDepartment(id);
        return flag ? ResultBody.success() : ResultBody.failed(ResultCode.FAILED);
    }

    /**
     * 分页查询
     */
    @PostMapping(URI_DEPARTMENT + URI_PAGE)
    public CommonResult<Page<DepartmentVO>> selectDepartmentPage(@RequestBody @Validated DepartmentPageParam param) {
        log.info("分页查询部门，请求参数:{}", param);
        final Page<DepartmentVO> page = departmentService.selectDepartmentPage(param);
        return ResultBody.success(page);
    }


}
