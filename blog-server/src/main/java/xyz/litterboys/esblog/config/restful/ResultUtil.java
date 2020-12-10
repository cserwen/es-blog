package xyz.litterboys.esblog.config.restful;

import java.io.Serializable;

public class ResultUtil implements Serializable {

    private static final long serialVersionUID = 7498483649536881777L;

    private Integer code;
    private String msg;
    private Object Data;

    public ResultUtil(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        Data = data;
    }

    //返回成功数据
    public static ResultUtil success(Object data) {
        return new ResultUtil(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    //返回出错数据
    public static ResultUtil error(ResponseCode code) {
        return new ResultUtil(code.getCode(), code.getMsg(), null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
