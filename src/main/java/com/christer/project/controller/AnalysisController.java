package com.christer.project.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.christer.project.WebURLConstant;
import com.christer.project.common.CommonResult;
import com.christer.project.common.ResultBody;
import com.christer.project.model.vo.InterfaceInfoVO;
import com.christer.project.service.AnalysisService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 21:30
 * Description:
 * 统计分析Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(WebURLConstant.URI_ANALYSIS)
public class AnalysisController extends AbstractSessionController {

    private final AnalysisService analysisService;


    @ApiOperation("统计接口使用")
    @GetMapping(WebURLConstant.URL_TOP_INTERFACE_INVOKE)
    @SaCheckRole("admin")
    public CommonResult<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        final List<InterfaceInfoVO> interfaceInfoVOS = analysisService.listTopInvokeInterfaceInfo();
        return ResultBody.success(interfaceInfoVOS);
    }
}
