package com.christer.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.christer.project.model.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Christer
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-02-09 15:00:15
* @Entity generator.domain.UserInterfaceInfo
*/
@Mapper
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

}




