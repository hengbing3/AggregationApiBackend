package com.christer.project.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.christer.myapicommon.model.dto.interfaceinfo.InterfaceInfoApplyParam;
import com.christer.myapicommon.model.dto.interfaceinfo.InterfaceInfoApplyQueryParam;
import com.christer.myapicommon.model.dto.interfaceinfo.InterfaceInfoApproveParam;
import com.christer.myapicommon.model.dto.interfaceinfo.InterfaceInfoReApplyParam;
import com.christer.myapicommon.model.entity.InterfaceInfoApply;
import com.christer.myapicommon.model.vo.InterfaceInfoApplyRecordVO;
import com.christer.project.WebURLConstant;
import com.christer.project.common.CommonResult;
import com.christer.project.common.ResultBody;
import com.christer.project.constant.CommonConstant;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoInvokeParam;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoParam;
import com.christer.project.model.dto.interfaceinfo.QueryInterfaceInfoParam;


import com.christer.myapicommon.model.entity.InterfaceInfo;
import com.christer.project.service.InterfaceInfoService;
import com.christer.project.util.ValidateGroup;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.christer.project.common.ResultCode.*;

/**
 * 接口信息管理
 *
 * @author Christer
 * @since 2024-01-21 22:39:03
 */
@RestController
@RequiredArgsConstructor
@SaCheckLogin
@RequestMapping(WebURLConstant.URI_INTERFACE_INFO)
public class InterfaceInfoController extends AbstractSessionController {
    /**
     * 接口信息Service
     */
    private final InterfaceInfoService interfaceInfoService;


    private static final Logger log = LoggerFactory.getLogger(InterfaceInfoController.class);

    /**
     * 分页查询
     *
     * @param interfaceInfoParam 筛选条件
     * @return 查询结果
     */
    @PostMapping(WebURLConstant.URI_PAGE)
    @ApiOperation("接口信息-分页查询")
    public CommonResult<Page<InterfaceInfo>> queryByPage(@RequestBody QueryInterfaceInfoParam interfaceInfoParam) {
        log.info("接口信息，分页查询条件:{}", interfaceInfoParam);
        final Page<InterfaceInfo> interfaceInfos = interfaceInfoService.queryByPage(interfaceInfoParam);
        return ResultBody.success(interfaceInfos);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping
    @ApiOperation("根据id查看接口详情")
    public CommonResult<InterfaceInfo> queryById(@RequestParam("id") Long id) {
        log.info("查看接口详情，id:{}", id);
        final InterfaceInfo interfaceInfo = interfaceInfoService.queryById(id);
        return ResultBody.success(interfaceInfo);
    }

    /**
     * 新增数据
     *
     * @param interfaceInfo 实体
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("新增接口数据")
    public CommonResult<Void> addInterFaceInfo(@RequestBody @Validated(ValidateGroup.Save.class) InterfaceInfoParam interfaceInfo) {
        log.info("新增接口数据:{}", interfaceInfo);
        interfaceInfo.setCreateUserId(getCurrentUserId());
        return interfaceInfoService.addInterFaceInfo(interfaceInfo) ?
                ResultBody.success() :
                ResultBody.failed(INTERFACE_ADD_ERROR.getCode(), INTERFACE_ADD_ERROR.getMessage());
    }

    /**
     * 编辑数据
     *
     * @param interfaceInfo 实体
     * @return 编辑结果
     */
    @PutMapping
    @ApiOperation("编辑接口数据")
    public CommonResult<Void> editInterFaceInfo(@RequestBody @Validated(ValidateGroup.Update.class) InterfaceInfoParam interfaceInfo) {
        log.info("编辑接口数据:{}", interfaceInfo);
        interfaceInfo.setUpdateUserId(getCurrentUserId());
        return interfaceInfoService.editInterfaceInto(interfaceInfo) ?
                ResultBody.success() :
                ResultBody.failed(INTERFACE_EDIT_ERROR.getCode(), INTERFACE_EDIT_ERROR.getMessage());
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    @ApiOperation("删除接口数据")
    public CommonResult<Void> deleteById(@RequestParam("id") Long id) {
        log.info("删除接口数据:{}", id);
        final Long currentUserId = getCurrentUserId();
        return interfaceInfoService.deleteById(id, currentUserId) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }

    /**
     * 接口发布
     */
    @PutMapping(WebURLConstant.URI_ONLINE)
    @ApiOperation("接口发布（上线）")
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<Void> onlineInterfaceInfo(@RequestParam Long id) {
        log.info("发布接口信息，接口id:{}", id);
        final Long currentUserId = getCurrentUserId();
        return interfaceInfoService.onlineInterfaceInfo(id, currentUserId) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }
    /**
     * 接口下线
     *
     */
    @PutMapping(WebURLConstant.URI_OUTLINE)
    @ApiOperation("接口下线")
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<Void> outlineInterfaceInfo(@RequestParam Long id) {
        log.info("接口下线，接口id:{}", id);
        final Long currentUserId = getCurrentUserId();
        return interfaceInfoService.outlineInterfaceInfo(id, currentUserId) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }
    /**
     * 接口调试
     */
    @PostMapping(WebURLConstant.URI_INVOKE)
    @ApiOperation("接口调试")
    public CommonResult<Object> invokeInterfaceInfo(@RequestBody @Validated InterfaceInfoInvokeParam param) {
        log.info("接口调试，请求参数:{}", param);
        Long currentUserId = getCurrentUserId();
        final Object invokeResult = interfaceInfoService.invokeInterfaceInfo(param, currentUserId);
        return ResultBody.success(invokeResult);
    }

    /**
     * 接口申请
     */
    @PostMapping(WebURLConstant.URI_APPLY)
    @SaCheckRole(CommonConstant.USER_ROLE)
    public CommonResult<Void> applyInterfaceInfo(@RequestBody @Validated InterfaceInfoApplyParam param) {
        log.info("接口申请，请求参数:{}", param);
        param.setCreateUserId(getCurrentUserId());
        param.setUpdateUserId(getCurrentUserId());
        return interfaceInfoService.applyInterfaceInfo(param) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }

    /**
     * 重新提交-接口申请
     */
    @PostMapping(WebURLConstant.URI_RE_APPLY)
    @SaCheckRole(CommonConstant.USER_ROLE)
    public CommonResult<Void> reApplyInterfaceInfo(@RequestBody @Validated InterfaceInfoReApplyParam param) {
        log.info("重新提交-接口申请，请求参数:{}", param);
        param.setUpdateUserId(getCurrentUserId());
        return interfaceInfoService.reApplyInterfaceInfo(param) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }

    /**
     * 接口审核
     */
    @PostMapping(WebURLConstant.URI_APPROVE)
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<Void> approveInterfaceInfo(@RequestBody @Validated InterfaceInfoApproveParam param) {
        log.info("接口审核，请求参数:{}", param);
        param.setAuditUserId(getCurrentUserId());
        return interfaceInfoService.approveInterfaceInfo(param) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }

    /**
     * 接口申请-历史流程记录
     */
    @GetMapping(WebURLConstant.URI_APPLY_HISTORY)
    public CommonResult<List<InterfaceInfoApplyRecordVO>> getHistoryList(@RequestParam Long interfaceInfoApplyId) {
        log.info("接口申请-历史流程记录，请求参数:{}", interfaceInfoApplyId);
        return ResultBody.success(interfaceInfoService.getHistoryList(interfaceInfoApplyId));
    }

    /**
     * 接口申请-待办信息
     */
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    @PostMapping(WebURLConstant.URI_APPLY_TODO)
    public CommonResult<Page<InterfaceInfoApply>> getTodoPage(@RequestBody @Validated InterfaceInfoApplyQueryParam param) {
        log.info("查询代办任务:{}",param);
        param.setCurrentUserId(getCurrentUserId());
        final Page<InterfaceInfoApply> page = interfaceInfoService.getTodoPage(param);
        return ResultBody.success(page);
    }

    /**
     * 接口申请-已办信息
     */
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    @PostMapping(WebURLConstant.URI_APPLY_DONE)
    public CommonResult<Page<InterfaceInfoApply>> getDonePage(@RequestBody @Validated InterfaceInfoApplyQueryParam param)
    {
        log.info("查询已办任务:{}",param);
        param.setCurrentUserId(getCurrentUserId());
        final Page<InterfaceInfoApply> page = interfaceInfoService.getDonePage(param);
        return ResultBody.success(page);
    }



}

