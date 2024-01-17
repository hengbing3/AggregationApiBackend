package com.christer.project.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Christer
 * @version 1.0
 * @date 2023-12-10 17:08
 * Description:
 */
@Setter
@Getter
@ToString
public class SearchVO implements Serializable {

    private static final long serialVersionUID = -2176710104665325312L;
    /**
     * 用户列表
     */
    private List<UserInfoVO> userList;
    /**
     * 帖子列表
     */
    private List<PostVO> postList;

    /**
     * 聚合搜索，返回单一类型
     */
    private List<?> dataList;
}
