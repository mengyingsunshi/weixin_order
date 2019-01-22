package com.ying.weixin_order.VO;

/**
 * @Author sunmengying
 * @Date 2018/12/18 18:23
 */
public class ResultVO<T> {
    private Integer code;

    private String message;
    /**
     * 具体数据
     */
    private T data;

    public ResultVO() {
    }

    public ResultVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}