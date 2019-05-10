package com.rip.load.otherPojo.personalRiskInformation;

/**
 * 综合风险查询 个人黑名单多头借贷综合
 */
public class PersonalRiskInformation {
    /**
     *返回订单号
     */
    private String orderNo;

    /**
     *返回查询结果
     */
    private Data data;

    /**
     *返回状态码
     */
    private String rc;

    /**
     *返回信息
     */
    private String msg;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
