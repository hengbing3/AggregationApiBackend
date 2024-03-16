package com.christer.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.christer.myapicommon.model.entity.InterfaceInfoApply;
import com.christer.myapicommon.model.entity.InterfaceInfoApplyRecord;
import com.christer.myapicommon.model.vo.InterfaceInfoApplyRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-16 15:49
 * Description:
 */
@Mapper
public interface InterfaceInfoApplyMapper extends BaseMapper<InterfaceInfoApply> {
    /**
     * 插入申请记录
     * @param interfaceInfoApplyRecord
     */
    int insertInterfaceInfoApplyRecord(InterfaceInfoApplyRecord interfaceInfoApplyRecord);

    /**
     * 获取历史流程记录
     * @param interfaceInfoApplyId
     * @return
     */
    List<InterfaceInfoApplyRecordVO> selectApplyRecordList(@Param("interfaceInfoApplyId") Long interfaceInfoApplyId);
}