package com.suncompass.plateform.sys.web;

import javax.swing.plaf.PanelUI;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/6.
 *
 * @author by Administrator.
 * @version 1.0.0.
 */
public class RequestResult implements Serializable {
    /**
     * 请求结果代码;0表示正常，-1表示错误
     */
    int code;

    /**
     * 返回请求结果消息
     */
    String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RequestResult() {
        this.code = 0;
        this.message = "";
    }

    public RequestResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
