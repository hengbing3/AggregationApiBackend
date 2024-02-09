package com.christer.project.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.christer.project.model.dto.userinterfaceinfo.QueryUserInterfaceInfoListParam;
import com.christer.project.model.dto.userinterfaceinfo.QueryUserInterfaceInfoParam;
import com.christer.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddParam;
import com.christer.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateParam;
import com.christer.project.model.entity.UserInterfaceInfo;

import java.util.List;

/**
 * @author Christer
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
 * @createDate 2024-02-09 15:00:15
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 用户调用接口信息-分页
     *
     * @param interfaceInfoParam
     * @return
     */
    Page<UserInterfaceInfo> queryByPage(QueryUserInterfaceInfoParam interfaceInfoParam);

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    UserInterfaceInfo queryById(Long id);

    /**
     * 新增
     *
     * @param userInterfaceInfo
     * @return
     */
    boolean addUserInterFaceInfo(UserInterfaceInfoAddParam userInterfaceInfo);

    /**
     * 更新
     *
     * @param userInterfaceInfoUpdateParam
     * @return
     */
    boolean updateUserInterFaceInfo(UserInterfaceInfoUpdateParam userInterfaceInfoUpdateParam);

    /**
     * 删除
     *
     * @param id id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * list
     *
     * @param param
     * @return
     */
    List<UserInterfaceInfo> selectListByCondition(QueryUserInterfaceInfoListParam param);

    /**
     * 用户接口调用次数统计
     *
     * @param interfaceInfoId 接口id
     * @param userId          用户id
     * @return true : 成功 false: 失败
     */
    boolean invokeCount(Long interfaceInfoId, Long userId);
}
