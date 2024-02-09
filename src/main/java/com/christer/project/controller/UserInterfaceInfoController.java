package com.christer.project.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.christer.project.WebURLConstant;
import com.christer.project.common.CommonResult;
import com.christer.project.common.ResultBody;
import com.christer.project.constant.CommonConstant;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoParam;
import com.christer.project.model.dto.userinterfaceinfo.QueryUserInterfaceInfoListParam;
import com.christer.project.model.dto.userinterfaceinfo.QueryUserInterfaceInfoParam;
import com.christer.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddParam;
import com.christer.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateParam;
import com.christer.project.model.entity.UserInterfaceInfo;
import com.christer.project.service.UserInterfaceInfoService;
import com.christer.project.util.ValidateGroup;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.christer.project.common.ResultCode.FAILED;

/**
 * 用户调用接口信息
 *
 * @author Christer
 * @since 2024-02-09 15:21:03
 */
@RestController
@RequiredArgsConstructor
@SaCheckLogin
@RequestMapping(WebURLConstant.URI_USER_INTERFACE_INFO)
public class UserInterfaceInfoController extends AbstractSessionController {
    /**
     * 用户调用接口信息Service
     */
    private final UserInterfaceInfoService userInterfaceInfoService;


    private static final Logger log = LoggerFactory.getLogger(UserInterfaceInfoController.class);

    /**
     * 用户调用接口信息分页查询
     *
     * @param interfaceInfoParam 筛选条件
     * @return 查询结果
     */
    @PostMapping(WebURLConstant.URI_PAGE)
    @ApiOperation("用户调用接口信息-分页查询")
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<Page<UserInterfaceInfo>> queryByPage(@RequestBody QueryUserInterfaceInfoParam interfaceInfoParam) {
        log.info("用户调用接口信息，分页查询条件:{}", interfaceInfoParam);
        final Page<UserInterfaceInfo> interfaceInfos = userInterfaceInfoService.queryByPage(interfaceInfoParam);
        return ResultBody.success(interfaceInfos);
    }

    /**
     * 用户调用接口信息-通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping
    @ApiOperation("用户调用接口信息-根据id查看接口详情")
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<UserInterfaceInfo> queryById(@RequestParam("id") Long id) {
        log.info("用户调用接口信息-查看接口详情，id:{}", id);
        final UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.queryById(id);
        return ResultBody.success(userInterfaceInfo);
    }

    /**
     * 用户调用接口信息-新增数据
     *
     * @param userInterfaceInfo 实体
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("用户调用接口信息-新增数据")
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<Void> addUserInterFaceInfo(@RequestBody @Validated UserInterfaceInfoAddParam userInterfaceInfo) {
        log.info("用户调用接口信息-新增接口数据:{}", userInterfaceInfo);
        return userInterfaceInfoService.addUserInterFaceInfo(userInterfaceInfo) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }

    /**
     * 用户调用接口信息-编辑数据
     *
     * @param userInterfaceInfoUpdateParam 实体
     * @return 编辑结果
     */
    @PutMapping
    @ApiOperation("用户调用接口信息-编辑接口数据")
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<Void> updateUserInterFaceInfo(@RequestBody UserInterfaceInfoUpdateParam userInterfaceInfoUpdateParam) {
        log.info("用户调用接口信息-编辑接口数据:{}", userInterfaceInfoUpdateParam);
        return userInterfaceInfoService.updateUserInterFaceInfo(userInterfaceInfoUpdateParam) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }

    /**
     * 用户调用接口信息-删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    @ApiOperation("用户调用接口信息-删除数据")
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<Void> deleteById(@RequestParam("id") Long id) {
        log.info("用户调用接口信息-删除数据:{}", id);
        return userInterfaceInfoService.deleteById(id) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }

    /**
     * 用户调用接口信息-列表查询
     */
    @PostMapping(WebURLConstant.URI_LIST)
    @ApiOperation("用户调用接口信息-列表查询")
    @SaCheckRole(CommonConstant.ADMIN_ROLE)
    public CommonResult<List<UserInterfaceInfo>> selectListByCondition(@RequestBody QueryUserInterfaceInfoListParam param) {
        log.info("用户调用接口信息-列表数据数据:{}", param);
        final List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoService.selectListByCondition(param);
        return ResultBody.success(userInterfaceInfos);
    }




}

