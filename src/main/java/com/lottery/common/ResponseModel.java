package com.lottery.common;

public class ResponseModel {
    Long code;
    String msg;
    Object data;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseModel(Long code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public ResponseModel(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseModel(Object data) {
        this.code = 0L;
        this.msg = "";
        this.data = data;
    }

    public ResponseModel(){

    }
}
