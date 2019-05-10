package com.rip.load.otherPojo.operatorCreditReport;

public class OperatorCreditReport {

    /**
     * 状态码
     */
    private String code;
    /**
     *睿普ID
     */
    private String rip_id;

    private String msg;
    private String token;
    private Data data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRip_id() {
        return rip_id;
    }

    public void setRip_id(String rip_id) {
        this.rip_id = rip_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
