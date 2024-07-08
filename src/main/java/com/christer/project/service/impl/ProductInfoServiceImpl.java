package com.christer.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.christer.myapicommon.model.entity.InterfaceInfo;
import com.christer.project.mapper.InterfaceInfoMapper;
import com.christer.project.mapper.ProductInfoMapper;
import com.christer.project.model.entity.ProductInfoEntity;
import com.christer.project.service.ProductInfoService;
import org.springframework.stereotype.Service;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-07-08 23:05
 * Description:
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfoEntity> implements ProductInfoService {
}
