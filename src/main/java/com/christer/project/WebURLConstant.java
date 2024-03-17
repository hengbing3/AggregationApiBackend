package com.christer.project;

/**
 * @author Christer
 * @version 1.0
 * @date 2023-12-02 15:00
 * Description:
 * Web URL 统一管理
 */
public final class WebURLConstant {




    private WebURLConstant() {
    }

    public static final String URI_USER = "/user";
    public static final String URI_PAGE = "/page";
    public static final String URI_LIST = "/list";

    public static final String URI_USER_REGISTER = URI_USER +  "/register";

    public static final String URI_USER_LOGIN = URI_USER + "/login";

    public static final String URI_USER_LOGOUT = URI_USER + "/logout";

    public static final String URI_USER_INFO = URI_USER + "/info";
    public static final String URI_USER_PAGE = URI_USER + URI_PAGE;

    public static final String URI_POST = "/post";
    public static final String URI_DEPARTMENT = "/department";

    public static final String URI_USER_GROUP = "/userGroup";

    public static final String URI_POST_PAGE = URI_POST + URI_PAGE;

    public static final String URI_INTERFACE_INFO = "/interfaceInfo";
    public static final String URI_USER_INTERFACE_INFO = "/userInterfaceInfo";

    public static final String URI_ONLINE = "/online";
    public static final String URI_OUTLINE = "/outline";
    public static final String URI_INVOKE = "/invoke";

    public static final String URI_ANALYSIS = "/analysis";

    public static final String URL_TOP_INTERFACE_INVOKE = "/top/interface/invoke";

    public static final String URI_APPLY = "/apply";

    public static final String URI_APPLY_HISTORY = "/applyHistory";

    public static final String URI_APPLY_TODO = "/applyTodo";

    public static final String URI_APPLY_DONE = "/applyDone";

    public static final String URI_APPROVE = "/approve";


}
