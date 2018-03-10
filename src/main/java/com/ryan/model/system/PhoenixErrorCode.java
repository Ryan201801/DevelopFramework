package com.ryan.model.system;

public enum PhoenixErrorCode {
    //无法识别的错误
    INTERNAL_SERVER_ERROR(1000, "Phoenix service encounter an unrecognized error."),
    //IMS服务子组件无法识别
    SUB_COMPONENT_ERROR(1001, "IMS service received an unrecognized error from sub-component."),
    //请求为授权
    UNAUTHORIZED(1002, "The request is unauthorized."),
    //数据库操作异常
    DATABASE_OPT_ERROR(1003, "Database opt error."),
    //找不到用户
    DATA_NOT_FOUND(1004, "Data not found."),
    //短信验证码错误
    SMS_CODE_ERROR(1005, "SMS code error."),
    //用户没有权限
    NO_PERMISSION_ERROR(1006, "Do not have permission."),
    //user id not the same between path and body
    USER_ID_NOT_SAME(1007, "user id not the same between path and body."),
    //parrent sellerid 不许改变
    PARENTSELLERID_CANNOTCHANGE(1009, "parrent sellerid can not change."),
    //数据已经存在
    DATA_ALREADY_EXIST(1010, "data already exists."),
    //该用户已经有订单了
    USER_CAR_ORDER_EXIST(1011, "This user's car hava order already.");

    private Integer code;
    private String message;

    PhoenixErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
