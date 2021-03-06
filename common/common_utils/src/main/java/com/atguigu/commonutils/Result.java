package com.atguigu.commonutils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    private boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    //构造器私有化
    private Result() {
    }

    //成功静态方法
    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage("成功！");
        return result;
    }


    //失败静态方法
    public static Result failure() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR.getCode());
        result.setMessage("失败！");
        return result;
    }

    public Result success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
