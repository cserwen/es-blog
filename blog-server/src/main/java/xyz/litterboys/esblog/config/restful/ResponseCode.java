package xyz.litterboys.esblog.config.restful;

public enum ResponseCode {

    SUCCESS(0, "操作成功"),
    ERROR(1, "操作失败"),

    //parameter
    PARAM_TYPE_MISMATCH(1000, "参数类型错误"),
    PARAM_BIND_EXCEPTION(1001, "参数绑定错误"),
    PARAM_VIOLATION(1002, "参数不符合要求"),
    PARAM_MISSING(1003, "参数未找到");

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
