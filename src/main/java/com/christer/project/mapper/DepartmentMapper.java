package com.christer.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.christer.myapicommon.model.dto.department.DepartmentPageParam;
import com.christer.myapicommon.model.entity.DepartmentEntity;
import com.christer.myapicommon.model.vo.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-10 16:45
 * Description:
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<DepartmentEntity> {
    /**
     * 新增用户和部门关系
     * @param id 用户id
     * @param defaultDepartment 默认用户关联的部门id
     */
    Boolean insertUserAndDepartmentRelation(@Param("id") Long id, @Param("defaultDepartment") Long defaultDepartment);

    /**
     * 关联用户数量
     * @param id
     * @return
     */
    int selectRelationUserCount(@Param("id") Long id);

    /**
     * 查询父级部门id
     * @param id
     * @return
     */
    Long selectParentIdById(Long id);

    /**
     * 查询子部门数量
     * @param id
     * @return
     */
    int selectChildCount(@Param("id") Long id);

    /**
     * 分页数量
     * @param param
     * @return
     */
    int selectCountByParam(DepartmentPageParam param);

    /**
     * 分页列表
     * @param param
     * @return
     */
    List<DepartmentVO> selectListByParam(DepartmentPageParam param);
}
