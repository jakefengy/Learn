package com.fanzhuo.framework;

/**
 * 2018-07-02.
 */
public class IConstants {

    public static String DEAULT_RESOURCE_FILE = "config.common.resource";

    // 缓存名称，可在ehcache.xml中增加其他周期的缓存
    public static final String menuCache = "menuCache";
    public static final String dataCache = "dataCache";// 120 seconds
    public static final String dataCache30min = "dataCache30min";
    public static final String dataCache60min = "dataCache60min";
    public static final String dataCache4hour = "dataCache4hour";
    public static final String dataCache24hour = "dataCache24hour";

    public static final String SESSION_USER = "_SESSION_USER_KEY";
    public static final String SESSION_USER_ID = "_SESSION_USER_ID";
    public static final String SESSION_USER_ACCOUNT = "_SESSION_USER_ACCOUNT";
    public static final String SESSION_USER_NAME = "_SESSION_USER_NAME";

    // 系统管理员用户主键、角色主键，不允许删除
    public static final String ADMIN_USER_ID = "admin";
    public static final String ADMIN_ROLE_ID = "admin";

    // 菜单资源类别：types=0 完全开放; types=1 需授权; types=2 需登录
    public static final String MENU_TYPES_0 = "0";
    public static final String MENU_TYPES_1 = "1";
    public static final String MENU_TYPES_2 = "2";

    // 角色权限类别：模块权限(菜单权限)、功能权限
    public static final String ROLE_AUTH_TYPES_1 = "1";
    public static final String ROLE_AUTH_TYPES_2 = "2";

    // 应答内容编码
    public static final String RESP_CHARACTER_ENCODE = "UTF-8";
    // 应答头部信息，excel文件下载
    public static final String RESP_CONTENT_TYPE_HTML = "text/html";
    public static final String RESP_CONTENT_TYPE_PLAIN = "text/plain";
    public static final String RESP_CONTENT_TYPE_CSS = "text/css";
    public static final String RESP_CONTENT_TYPE_JPEG = "image/jpeg";
    public static final String RESP_CONTENT_TYPE_GIF = "image/gif";
    public static final String RESP_CONTENT_TYPE_PNG = "image/png";
    public static final String RESP_CONTENT_TYPE_BMP = "image/bmp";
    public static final String RESP_CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
    public static final String RESP_CONTENT_TYPE_PDF = "application/pdf";
    public static final String RESP_CONTENT_TYPE_XML = "application/xml";
    public static final String RESP_CONTENT_TYPE_JSON = "application/json";
    public static final String RESP_CONTENT_TYPE_XJSON = "application/x-json";

    // 默认分页参数，默认值注意与前端js默认值一致
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_NO = 1;
    public static final int PAGE_BEGIN = 0;

    // 通用标志值，可能多处使用
    // 是否可用
    public static final String STATUS_Y = "Y";
    public static final String STATUS_N = "N";
    // 是否显示
    public static final String VISIBLE_Y = "Y";
    public static final String VISIBLE_N = "N";
    // 树形顶点ID值
    public static final String TREE_TOP_ID = "0";

    // 下拉选项是否增加选项：“无”
    public static final String BLANKABLE = "Y";

    // 常用配置
    // 文件保存根目录
    public static final String PAR_FILE_HOME = "file_home";
    // 会话有效时长，单位分钟(session)
    public static final String PAR_SESSION_TIMEOUT = "session_timeout";
    // 用户逻辑删除(状态禁用)
    public static final String PAR_USER_DELETE_LOGIC = "user_delete_logic";

}
