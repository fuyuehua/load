package com.rip.load.otherPojo.vehicleDetailsEnquiry;

/**
 * 车辆信息核验
 */
public class VehicleDetailsEnquiry {

    /**
     * 请求状态（是否成功
     */
    private Boolean success;
    /**
     * 唯一订单号
     */
    private String requestOrder;

    private Data data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
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
