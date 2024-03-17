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
    /**
     * 角色常量：user
     */
    public static final String USER_ROLE = "user";


    public static final String SORT_ORDER_ASC = "asc";

    public static final String SORT_ORDER_DESC = "desc";

    public static final String DEFAULT_AVATAR = "https://upload-images.jianshu.io/upload_images/5809200-caf66b935fd00e18.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
    /**
     * 注册的默认用户均为普通的用户部门
     */
    public static final Long DEFAULT_DEPARTMENT = 3L;
}
