package com.fanzhuo.framework;

/**
 * 2018-07-02.
 */
public class ErrorCode {
    // system
    public static final String err_unknow_error = "err-0000";
    public static final String err_dynamic = "err-0001";
    public static final String err_not_found_record = "err-0010";
    public static final String err_more_than_one_record = "err-0011";

    // common
    public static final String err_not_allow_delete = "err-1010";
    public static final String err_not_allow_copy = "err-1011";
    public static final String err_not_allow_blank = "err-1012";
    public static final String err_not_right_value = "err-1013";
    public static final String err_not_allow_duplicate = "err-1014";
    public static final String err_has_child_node = "err-1015";

    // user
    public static final String err_user_not_login = "err-1110";
    public static final String err_user_not_exists = "err-1111";
    public static final String err_user_disable = "err-1112";
    public static final String err_passwd_wrong = "err-1113";
    public static final String err_has_no_role = "err-1114";

    // parameters
    public static final String err_parameters_not_exists = "err-1210";
    public static final String err_excel_not_exists = "err-1211";
}
