package com.christer.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.christer.myapiclientsdk.client.MyApiClient;
import com.christer.myapiclientsdk.model.User;
import com.christer.myapicommon.enums.ApiAuditStatusEnum;
import com.christer.myapicommon.model.dto.interfaceinfo.*;
import com.christer.myapicommon.model.entity.*;
import com.christer.myapicommon.model.vo.InterfaceInfoApplyRecordVO;
import com.christer.project.common.ResultCode;
import com.christer.project.exception.BusinessException;
import com.christer.project.exception.ThrowUtils;
import com.christer.project.mapper.InterfaceInfoApplyMapper;
import com.christer.project.mapper.InterfaceInfoMapper;
import com.christer.project.mapper.UserMapper;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoInvokeParam;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoParam;
import com.christer.project.model.dto.interfaceinfo.QueryInterfaceInfoParam;
import com.christer.project.model.enums.InterfaceInfoStatusEnum;
import com.christer.project.service.InterfaceInfoService;
import com.christer.project.util.EncryptUtil;
import com.christer.project.util.ValidateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.christer.myapicommon.common.CommonConstant.API_OPEN_AUDIT_KEY;
import static com.christer.myapicommon.common.CommonConstant.FLOWABLE_SALT;
import static com.christer.myapicommon.enums.ApiAuditResultEnum.PASS;
import static com.christer.myapicommon.enums.ApiAuditStatusEnum.*;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-24 11:19
 * Description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService {

    private final InterfaceInfoMapper interfaceInfoMapper;

    private final UserMapper userMapper;

    private final InterfaceInfoApplyMapper interfaceInfoApplyMapper;

    @Value("${default-flowable-ui-service}")
    private String flowableServiceUI;

    @Override
    public boolean addInterFaceInfo(InterfaceInfoParam interfaceInfo) {
        InterfaceInfo info = BeanUtil.copyProperties(interfaceInfo, InterfaceInfo.class);
        final int insert = interfaceInfoMapper.insert(info);
        return insert > 0;
    }

    @Override
    public Page<InterfaceInfo> queryByPage(QueryInterfaceInfoParam interfaceInfoParam) {
        //分页参数
        final Page<InterfaceInfo> rowPage = new Page<>(interfaceInfoParam.getCurrentPage(), interfaceInfoParam.getPageSize());
        final LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(interfaceInfoParam.getName()), InterfaceInfo::getName, interfaceInfoParam.getName())
                .like(StringUtils.hasText(interfaceInfoParam.getDescription()), InterfaceInfo::getDescription, interfaceInfoParam.getDescription())
                .eq(StringUtils.hasText(interfaceInfoParam.getUrl()), InterfaceInfo::getUrl, interfaceInfoParam.getUrl())
                .eq(StringUtils.hasText(interfaceInfoParam.getMethod()), InterfaceInfo::getMethod, interfaceInfoParam.getMethod())
                .eq(!Objects.isNull(interfaceInfoParam.getStatus()), InterfaceInfo::getStatus, interfaceInfoParam.getStatus());
        return interfaceInfoMapper.selectPage(rowPage, queryWrapper);
    }

    @Override
    public InterfaceInfo queryById(Long id) {
        return interfaceInfoMapper.selectById(id);
    }

    @Override
    public boolean editInterfaceInto(InterfaceInfoParam interfaceInfo) {
        InterfaceInfo info = BeanUtil.copyProperties(interfaceInfo, InterfaceInfo.class);
        final int update = interfaceInfoMapper.updateById(info);
        return update > 0;
    }

    @Override
    public boolean deleteById(Long id, Long currentUserId) {
        return interfaceInfoMapper.deleteById(id) > 0;
    }

    @Override
    public boolean onlineInterfaceInfo(Long id, Long currentUserId) {
        Objects.requireNonNull(id, "接口id不存在！");
        Objects.requireNonNull(currentUserId, "当前用户id不存在！");
        // 获取接口信息
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(id);
        ThrowUtils.throwIf(null == interfaceInfo, "接口信息不存在！");
        final MyApiClient myApiClient = getMyApiClient(currentUserId);
        // 判断接口是否能够调用
        User user = new User();
        user.setUsername("加油上线！！！");
        String result = myApiClient.getUsernameByPost(user);
        if (!StringUtils.hasText(result)) {
            throw new BusinessException(ResultCode.FAILED, "接口调用失败！");
        }
        // 修改接口状态
        InterfaceInfo updateInterfaceInfo = new InterfaceInfo();
        updateInterfaceInfo.setId(id);
        updateInterfaceInfo.setUpdateUserId(currentUserId);
        updateInterfaceInfo.setUpdateTime(new Date());
        updateInterfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getCode());
        return interfaceInfoMapper.updateById(updateInterfaceInfo) > 0;
    }

    @NotNull
    private MyApiClient getMyApiClient(Long currentUserId) {
        // 获取用户信息
        final UserEntity userEntity = userMapper.selectById(currentUserId);
        ThrowUtils.throwIf(null == userEntity, "用户信息不存在！");
        // 根据用户信息获取 accessKey 和 secretKey
        // 获取api客户端并调用接口
        return new MyApiClient(userEntity.getAccessKey(), userEntity.getSecretKey());
    }

    @Override
    public boolean outlineInterfaceInfo(Long id, Long currentUserId) {
        Objects.requireNonNull(id, "接口id不存在！");
        Objects.requireNonNull(currentUserId, "当前用户id不存在！");
        // 获取接口信息
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(id);
        ThrowUtils.throwIf(null == interfaceInfo, "接口信息不存在！");
        // 判断接口是否能够调用
        User user = new User();
        user.setUsername("加油下线！！！");
        final MyApiClient myApiClient = getMyApiClient(currentUserId);
        String result = myApiClient.getUsernameByPost(user);
        if (!StringUtils.hasText(result)) {
            throw new BusinessException(ResultCode.FAILED, "接口调用失败！");
        }
        // 修改接口状态
        InterfaceInfo updateInterfaceInfo = new InterfaceInfo();
        updateInterfaceInfo.setId(id);
        updateInterfaceInfo.setUpdateUserId(currentUserId);
        updateInterfaceInfo.setUpdateTime(new Date());
        updateInterfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getCode());
        return interfaceInfoMapper.updateById(updateInterfaceInfo) > 0;
    }

    @Override
    public Object invokeInterfaceInfo(InterfaceInfoInvokeParam param, Long currentUserId) {
        // 业务层校验
        ValidateUtil.validateBean(param);
        Objects.requireNonNull(currentUserId, "当前用户信息不存在！");
        // 根据接口id获取接口信息
        final InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(param.getId());
        ThrowUtils.throwIf(null == interfaceInfo, "接口信息不存在！");
        // 判断接口是否已经下线
        ThrowUtils.throwIf(InterfaceInfoStatusEnum.OFFLINE.getCode().equals(interfaceInfo.getStatus()), "该接口已下线，无法调用！");
        // 获取当前登录用户的密钥
        final UserEntity userEntity = userMapper.selectById(currentUserId);
        final String accessKey = userEntity.getAccessKey();
        final String secretKey = userEntity.getSecretKey();
        final MyApiClient myClient = new MyApiClient(accessKey, secretKey);
        // 调用接口
        // TODO 调用接口的逻辑必须修改
        final Gson gson = new Gson();
        User user = null;
        try {
            user = gson.fromJson(param.getUserRequestParams(), User.class);
        } catch (JsonSyntaxException e) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "请按照规范填写请求参数！");
        }
        final String usernameByPost = myClient.getUsernameByPost(user);
        log.info("调用结果：{}", usernameByPost);
        return usernameByPost;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean applyInterfaceInfo(final InterfaceInfoApplyParam param) {
        // 参数校验
        ValidateUtil.validateBean(param);
        // 根据流程的key 启动工作流
        final HashMap<String, String> headParam = new HashMap<>();
        final FlowableStartParam flowableStartParam = new FlowableStartParam();
        flowableStartParam.setProcessInstanceKey(API_OPEN_AUDIT_KEY);
        flowableStartParam.setAssigneeUser(String.valueOf(param.getCreateUserId()));
        final String jsonStr = JSONUtil.toJsonStr(flowableStartParam);
        final String sign = EncryptUtil.getDigestSign(jsonStr, FLOWABLE_SALT);
        headParam.put("sign", sign);
        // 启动工作流并完成任务
        final HttpResponse response = HttpRequest.post(flowableServiceUI + "/process/startAndCompleteTask")
                .addHeaders(headParam)
                .body(jsonStr, "application/json;charset=utf-8")
                .execute();
        if (response.getStatus() != 200) {
            log.error("工作流执行错误：{}", response.body());
            throw new BusinessException("工作流启动失败！");
        }
        // 工作流返回结果
        final String body = response.body();
        final FlowableInfo flowableInfo = JSONUtil.toBean(body, FlowableInfo.class);
        final InterfaceInfoApply interfaceInfoApply = BeanUtil.copyProperties(param, InterfaceInfoApply.class);
        interfaceInfoApply.setProcessInstanceId(flowableInfo.getProcessInstanceId());
        interfaceInfoApply.setAuditStatus(AUDITING.getCode());
        // 新增接口申请记录
        interfaceInfoApplyMapper.insert(interfaceInfoApply);
        // 新增接口审核记录
        final InterfaceInfoApplyRecord interfaceInfoApplyRecord = new InterfaceInfoApplyRecord();
        interfaceInfoApplyRecord.setInterfaceInfoApplyId(interfaceInfoApply.getId());
        interfaceInfoApplyRecord.setProcessNode(flowableInfo.getTaskName());
        interfaceInfoApplyRecord.setProcessNodeId(flowableInfo.getTaskId());
        interfaceInfoApplyRecord.setAuditResult(PASS.getCode());
        interfaceInfoApplyRecord.setCreateUserId(Long.valueOf(flowableInfo.getAssignee()));

        return interfaceInfoApplyMapper.insertInterfaceInfoApplyRecord(interfaceInfoApplyRecord) > 0;
    }

    @Override
    public List<InterfaceInfoApplyRecordVO> getHistoryList(final Long interfaceInfoApplyId) {
        Assert.notNull(interfaceInfoApplyId, "接口申请记录ID不能为空!");

        final List<InterfaceInfoApplyRecordVO> interfaceInfoApplyRecordList = interfaceInfoApplyMapper.selectApplyRecordList(interfaceInfoApplyId);

        return CollectionUtils.isEmpty(interfaceInfoApplyRecordList) ? Collections.emptyList() : interfaceInfoApplyRecordList;
    }

    @Override
    public Page<InterfaceInfoApply> getTodoPage(final InterfaceInfoApplyQueryParam param) {
        // 获取工作流的代办 流程实例id列表
        final HttpResponse response = HttpRequest.post(flowableServiceUI + "/process/totoTask?userId=" + param.getCurrentUserId())
                .execute();
        if (response.getStatus() != 200) {
            log.error("获取工作流的代办失败：{}", response.body());
            throw new BusinessException("无法获取工作流中的代办任务！");
        }
        final String body = response.body();
        final List<String> processInstanceIds = JSONUtil.toList(body, String.class);
        if (CollectionUtils.isEmpty(processInstanceIds)) {
            return new Page<>(param.getCurrentPage(), param.getPageSize(), 0);
        }
        // 根据流程实例id 分页查询数量
        final int count = interfaceInfoApplyMapper.selectApplyCount(param);
        // 根据流程实例id，查询代办信息列表
        final List<InterfaceInfoApply> list = count > 0 ? interfaceInfoApplyMapper.selectListByCondition(param) : Collections.emptyList();
        final Page<InterfaceInfoApply> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page.setRecords(list);
        page.setTotal(count);
        return page;
    }

    @Override
    public Page<InterfaceInfoApply> getDonePage(final InterfaceInfoApplyQueryParam param) {
        // 根据当前用户，去获取已办信息的流程实例id列表
        final HttpResponse response = HttpRequest.post(flowableServiceUI + "/process/doneTask?userId=" + param.getCurrentUserId())
                .execute();
        if (response.getStatus() != 200) {
            log.error("获取工作流的代办失败：{}", response.body());
            throw new BusinessException("无法获取工作流中的已办任务！");
        }
        final String body = response.body();
        final List<String> processInstanceIds = JSONUtil.toList(body, String.class);
        if (CollectionUtils.isEmpty(processInstanceIds)) {
            return new Page<>(param.getCurrentPage(), param.getPageSize(), 0);
        }
        // 根据已获取的流程实例id，获取已办信息列表
        final int count = interfaceInfoApplyMapper.selectApplyDoneCount(param);
        // 根据流程实例id，查询代办信息列表
        final List<InterfaceInfoApply> list = count > 0 ? interfaceInfoApplyMapper.selectApplyDoneList(param) : Collections.emptyList();
        final Page<InterfaceInfoApply> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        // 分装并返回page
        page.setRecords(list);
        page.setTotal(count);
        return page;


    }

    @Override
    public boolean approveInterfaceInfo(final InterfaceInfoApproveParam param) {
        // 获取流程请求参数
        final FlowableCompleteTaskParam taskParam = new FlowableCompleteTaskParam();
        taskParam.setAuditResult(param.getAuditResult());
        taskParam.setCandidateGroupUser(String.valueOf(param.getAuditUserId()));
        taskParam.setProcessInstanceId(param.getProcessInstanceId());
        // 调用工作流，完成任务
        final HttpResponse response = getCompleteTaskResponse(taskParam);
        // 工作流返回结果
        final String body = response.body();
        final FlowableInfo flowableInfo = JSONUtil.toBean(body, FlowableInfo.class);
        // 获取流程申请信息
        final InterfaceInfoApply interfaceInfoApply = interfaceInfoApplyMapper.selectById(param.getId());
        // 设置审核状态
        extractedSetAuditStatus(param, interfaceInfoApply);
        // 新增接口申请记录
        interfaceInfoApplyMapper.updateById(interfaceInfoApply);

        // 新增接口审核记录
        final InterfaceInfoApplyRecord interfaceInfoApplyRecord = new InterfaceInfoApplyRecord();
        interfaceInfoApplyRecord.setInterfaceInfoApplyId(interfaceInfoApply.getId());
        interfaceInfoApplyRecord.setProcessNode(flowableInfo.getTaskName());
        interfaceInfoApplyRecord.setProcessNodeId(flowableInfo.getTaskId());
        interfaceInfoApplyRecord.setAuditResult(param.getAuditResult());
        interfaceInfoApplyRecord.setAuditOpinion(param.getAuditOpinion());
        interfaceInfoApplyRecord.setAuditUserId(param.getAuditUserId());
        interfaceInfoApplyRecord.setCreateUserId(interfaceInfoApply.getCreateUserId());

        return interfaceInfoApplyMapper.insertInterfaceInfoApplyRecord(interfaceInfoApplyRecord) > 0;
    }

    /**
     * 调用工作流，完成任务
     * @param taskParam
     * @return
     */
    private HttpResponse getCompleteTaskResponse(final FlowableCompleteTaskParam taskParam) {
        // 封装签名认证
        final String jsonStr = JSONUtil.toJsonStr(taskParam);
        final String sign = EncryptUtil.getDigestSignByDigestAlgorithm(jsonStr, FLOWABLE_SALT, DigestAlgorithm.SHA1);
        final HashMap<String, String> headers = new HashMap<>();
        headers.put("sign", sign);
        // 调用工作流，完成审核
        final HttpResponse response = HttpRequest.post(flowableServiceUI + "/process/completeTask")
                .addHeaders(headers)
                .body(jsonStr, "application/json;charset=utf-8")
                .execute();
        if (response.getStatus() != 200) {
            log.error("工作流执行错误：{}", response.body());
            throw new BusinessException("工作流执行失败-无法完成任务！");
        }
        return response;
    }

    @Override
    public boolean reApplyInterfaceInfo(final InterfaceInfoReApplyParam param) {
        // 1.参数校验
        ValidateUtil.validateBean(param);
        // 2.获取流程实例id
        final InterfaceInfoApply oldInterfaceInfoApply = interfaceInfoApplyMapper.selectById(param.getId());
        if (!StrUtil.equals(oldInterfaceInfoApply.getAuditStatus(), API_AUDIT_NOT_PASS.getCode()) || !StrUtil.equals(oldInterfaceInfoApply.getAuditStatus(), API_OPEN_NOT_PASS.getCode())) {
            throw new BusinessException("该流程无法重新提交！");
        }
        // 3.根据流程实例id,调用工作流，完成任务
        final FlowableCompleteTaskParam taskParam = new FlowableCompleteTaskParam();
        taskParam.setProcessInstanceId(oldInterfaceInfoApply.getProcessInstanceId());
        taskParam.setAssigneeUser(String.valueOf(param.getUpdateUserId()));
        final HttpResponse response = getCompleteTaskResponse(taskParam);
        // 工作流返回结果
        final String body = response.body();
        final FlowableInfo flowableInfo = JSONUtil.toBean(body, FlowableInfo.class);
        // 4.更新接口申请表
        final InterfaceInfoApply interfaceInfoApply = BeanUtil.copyProperties(param, InterfaceInfoApply.class);
        interfaceInfoApplyMapper.updateById(interfaceInfoApply);
        // 5.新增接口申请记录表
        final InterfaceInfoApplyRecord interfaceInfoApplyRecord = new InterfaceInfoApplyRecord();
        interfaceInfoApplyRecord.setInterfaceInfoApplyId(param.getId());
        interfaceInfoApplyRecord.setProcessNode(flowableInfo.getTaskName());
        interfaceInfoApplyRecord.setProcessNodeId(flowableInfo.getTaskId());
        interfaceInfoApplyRecord.setAuditResult(PASS.getCode());
        interfaceInfoApplyRecord.setCreateUserId(Long.valueOf(flowableInfo.getAssignee()));
        // 6.返回结果
        return interfaceInfoApplyMapper.insertInterfaceInfoApplyRecord(interfaceInfoApplyRecord) > 0;
    }

    private static void extractedSetAuditStatus(final InterfaceInfoApproveParam param, final InterfaceInfoApply interfaceInfoApply) {
        if (StrUtil.equals(interfaceInfoApply.getAuditStatus(), ApiAuditStatusEnum.AUDITING.getCode())) {
            if (StrUtil.equals(PASS.getCode(), param.getAuditResult())) {
                interfaceInfoApply.setAuditStatus(API_OPEN_AUDITING.getCode());
            } else {
                interfaceInfoApply.setAuditStatus(API_AUDIT_NOT_PASS.getCode());
            }
        } else if (StrUtil.equals(interfaceInfoApply.getAuditStatus(), API_OPEN_AUDITING.getCode())) {
            if (StrUtil.equals(PASS.getCode(), param.getAuditResult())) {
                interfaceInfoApply.setAuditStatus(API_APPLY_PASS.getCode());
            } else {
                interfaceInfoApply.setAuditStatus(API_OPEN_NOT_PASS.getCode());
            }
        }
    }
}
