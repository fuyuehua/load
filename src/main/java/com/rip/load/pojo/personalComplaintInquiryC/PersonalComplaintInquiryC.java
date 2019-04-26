package com.rip.load.pojo.personalComplaintInquiryC;

/**
 * 个人涉诉
 */
public class PersonalComplaintInquiryC {

    /**
     * 请求状态
     */
    private String success;
    /**
     * 唯一订单号
     */
    private String requestOrder;
    private Data data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getRequestOrder() {
        return requestOrder;
    }

    public void setRequestOrder(String requestOrder) {
        this.requestOrder = requestOrder;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
