package com.christer.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.christer.myapicommon.model.dto.interfaceinfo.*;
import com.christer.myapicommon.model.entity.InterfaceInfo;
import com.christer.myapicommon.model.entity.InterfaceInfoApply;
import com.christer.myapicommon.model.vo.InterfaceInfoApplyRecordVO;
import com.christer.myapicommon.model.vo.InterfaceInfoApplyVO;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoInvokeParam;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoParam;
import com.christer.project.model.dto.interfaceinfo.QueryInterfaceInfoParam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


/**
* @author Christer
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-01-21 22:28:55
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    boolean addInterFaceInfo(InterfaceInfoParam interfaceInfo);

    Page<InterfaceInfo> queryByPage(QueryInterfaceInfoParam interfaceInfoParam);

    InterfaceInfo queryById(Long id);

    boolean editInterfaceInto(InterfaceInfoParam interfaceInfo);

    boolean deleteById(Long id, Long currentUserId);

    /**
     * 上线接口
     * @param id
     * @param currentUserId
     * @return
     */
    boolean onlineInterfaceInfo(Long id, Long currentUserId);

    /**
     * 接口下线
     * @param id
     * @param currentUserId
     * @return
     */
    boolean outlineInterfaceInfo(Long id, Long currentUserId);

    /**
     * 接口调试
     * @param param
     * @return
     */
    Object invokeInterfaceInfo(InterfaceInfoInvokeParam param, Long currentUserId);

    /**
     * 申请接口
     * @param param
     * @return
     */
    boolean applyInterfaceInfo(InterfaceInfoApplyParam param);

    /**
     * 获取接口申请历史流程记录
     * @param interfaceInfoApplyId
     * @return
     */
    List<InterfaceInfoApplyRecordVO> getHistoryList(Long interfaceInfoApplyId);

    /**
     * 获取分页代办信息
     * @param param
     * @return
     */
    Page<InterfaceInfoApply> getTodoPage(InterfaceInfoApplyQueryParam param);

    /**
     * 获取分页已办信息
     * @param param
     * @return
     */
    Page<InterfaceInfoApply> getDonePage(InterfaceInfoApplyQueryParam param);

    /**
     * 审批接口
     * @param param
     * @return
     */
    boolean approveInterfaceInfo(InterfaceInfoApproveParam param);

    /**
     * 重新提交-接口申请
     * @param param
     * @return
     */
    boolean reApplyInterfaceInfo(InterfaceInfoReApplyParam param);

    /**
     * 我的接口申请-分页查询
     * @param param
     * @return
     */
    Page<InterfaceInfoApplyVO> myInterfaceInfoApplyPage(MyInterfaceInfoApplyQueryParam param);

    /**
     * 查看接口申请详情
     * @param id
     * @return
     */
    InterfaceInfoApply queryInterfaceInfoApplyById(Long id);
}
