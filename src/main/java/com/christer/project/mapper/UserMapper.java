package com.christer.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.christer.myapicommon.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Christer
 * @version 1.0
 * @date 2023-12-02 15:49
 * Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 删除用户与部门的关联关系
     * @param id 用户id
     */
    void deleteUserAndDeptRelation(@Param("userId") Long id);
}
