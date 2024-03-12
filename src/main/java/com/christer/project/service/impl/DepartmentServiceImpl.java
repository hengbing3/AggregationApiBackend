package com.christer.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.christer.myapicommon.model.dto.department.DepartmentAddParam;
import com.christer.myapicommon.model.dto.department.DepartmentEditParam;
import com.christer.myapicommon.model.dto.department.DepartmentPageParam;
import com.christer.myapicommon.model.entity.DepartmentEntity;
import com.christer.myapicommon.model.vo.DepartmentVO;
import com.christer.project.exception.BusinessException;
import com.christer.project.mapper.DepartmentMapper;
import com.christer.project.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.christer.myapicommon.common.CommonConstant.DEPARTMENT_SALT;
import static com.christer.myapicommon.common.CommonConstant.DEPARTMENT_SALT_ID;
import static com.christer.project.WebURLConstant.URI_USER_GROUP;
import static com.christer.project.util.EncryptUtil.getDigestSign;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-10 16:40
 * Description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentEntity> implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    @Value("${default-flowable-ui-service}")
    private String flowableServiceUI;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDepartment(final DepartmentAddParam param) {
        final DepartmentEntity departmentEntity = BeanUtil.copyProperties(param, DepartmentEntity.class);
        final int insert = departmentMapper.insert(departmentEntity);
        // 同步flowable 组
        extractedAddToFlowable(departmentEntity);
        return insert > 0;
    }

    private void extractedAddToFlowable(final DepartmentEntity departmentEntity) {
        final String jsonStr = JSONUtil.toJsonStr(departmentEntity);
        final HashMap<String, String> head = new HashMap<>();
        final String sign = getDigestSign(jsonStr, DEPARTMENT_SALT);
        head.put("sign", sign);
        final HttpResponse response = HttpRequest.post(flowableServiceUI + URI_USER_GROUP)
                .addHeaders(head)
                .charset(StandardCharsets.UTF_8)
                .body(JSONUtil.toJsonStr(departmentEntity), "application/json;charset=UTF-8")
                .execute();
        if (response.getStatus() != 200) {
            throw new BusinessException("部门添加失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editDepartment(final DepartmentEditParam param) {
        final DepartmentEntity departmentEntity = BeanUtil.copyProperties(param, DepartmentEntity.class);
        final int update = departmentMapper.updateById(departmentEntity);
        // 同步更新到flowable
        extractedUpdateToFlowable(departmentEntity);
        return update > 0;
    }

    private void extractedUpdateToFlowable(final DepartmentEntity departmentEntity) {
        final String jsonStr = JSONUtil.toJsonStr(departmentEntity);
        final HashMap<String, String> head = new HashMap<>();
        final String sign = getDigestSign(jsonStr, DEPARTMENT_SALT);
        head.put("editSign", sign);
        final HttpResponse response = HttpRequest.put(flowableServiceUI + URI_USER_GROUP)
                .addHeaders(head)
                .body(jsonStr, "application/json;charset=UTF-8")
                .execute();
        if (response.getStatus() != 200) {
            log.error("修改部门失败！:{}", response.body());
            throw new BusinessException("修改部门失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDepartment(final Long id) {
        Assert.notNull(id, "用户id不能为空！");
        // 判断部门是否关联用户
        final int count = departmentMapper.selectRelationUserCount(id);
        if (count > 0) {
            throw new BusinessException("部门关联用户，无法删除！");
        }
        final Long parentId = departmentMapper.selectParentIdById(id);
        if (0L == parentId) {
            throw new BusinessException("顶级部门无法删除！");
        }
        final int childCount = departmentMapper.selectChildCount(id);
        if (childCount > 0) {
            throw new BusinessException("存在子级部门，无法删除！");
        }
        // 删除部门
        final int delete = departmentMapper.deleteById(id);
        // 同步更新flowable的数据
        extractedDeleteToFlowable(id);
        return delete > 0;
    }

    @Override
    public Page<DepartmentVO> selectDepartmentPage(DepartmentPageParam param) {
        final int count = departmentMapper.selectCountByParam(param);
        final List<DepartmentVO> list = count > 0 ? departmentMapper.selectListByParam(param) : Collections.emptyList();
        final Page<DepartmentVO> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page.setCurrent(count);
        page.setRecords(list);
        return page;
    }

    private void extractedDeleteToFlowable(final Long id) {
        final HashMap<String, String> head = new HashMap<>();
        final String sign = getDigestSign(id.toString(), DEPARTMENT_SALT_ID);
        head.put("deleteSign", sign);
        final HttpResponse response = HttpRequest.delete(flowableServiceUI + URI_USER_GROUP + "?id=" + id)
                .addHeaders(head)
                .execute();
        if (response.getStatus() != 200) {
            log.error("删除部门失败！:{}", response.body());
            throw new BusinessException("删除部门同步flowable数据失败！");
        }
    }
}
