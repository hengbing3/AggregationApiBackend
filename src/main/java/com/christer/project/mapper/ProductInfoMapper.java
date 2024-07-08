package com.christer.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.christer.myapicommon.model.entity.InterfaceInfo;
import com.christer.project.model.entity.ProductInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-07-08 23:07
 * Description:
 */
@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfoEntity> {
}
