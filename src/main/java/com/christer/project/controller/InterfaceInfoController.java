package com.christer.project.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.christer.project.WebURLConstant;
import com.christer.project.common.CommonResult;
import com.christer.project.common.ResultBody;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoParam;
import com.christer.project.model.dto.interfaceinfo.QueryInterfaceInfoParam;
import com.christer.project.model.entity.InterfaceInfo;
import com.christer.project.service.InterfaceInfoService;
import com.christer.project.util.ValidateGroup;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.christer.project.common.ResultCode.*;

/**
 * 接口信息(InterfaceInfo)表控制层
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
    public CommonResult<Void> deleteById(@RequestParam("id") Long id) {
        log.info("删除接口数据:{}", id);
        final Long currentUserId = getCurrentUserId();
        return interfaceInfoService.deleteById(id, currentUserId) ?
                ResultBody.success() :
                ResultBody.failed(FAILED.getCode(), FAILED.getMessage());
    }

}

