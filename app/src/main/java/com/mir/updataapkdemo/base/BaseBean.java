package com.mir.updataapkdemo.base;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/26
 * @desc
 */

public class BaseBean<T> {

    /**
     * status : 0
     * msg : success
     * data : {}
     */
    private T data;
    private int status;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
