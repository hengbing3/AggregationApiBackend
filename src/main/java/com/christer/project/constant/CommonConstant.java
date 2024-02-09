package com.christer.project.constant;

/**
 * @author Christer
 * @version 1.0
 * @date 2023-12-02 16:15
 * Description:
 * 常量存储
 */
public final class CommonConstant {

    private CommonConstant() {

    }

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "My*Christer";
    /**
     * 角色常量：admin
     */
    public static final String ADMIN_ROLE = "admin";


    public static final String SORT_ORDER_ASC = "asc";

    public static final String SORT_ORDER_DESC = "desc";
}
