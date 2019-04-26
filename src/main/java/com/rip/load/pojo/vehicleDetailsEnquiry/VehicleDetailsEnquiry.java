package com.rip.load.pojo.vehicleDetailsEnquiry;

/**
 * 车辆信息核验
 */
public class VehicleDetailsEnquiry {

    /**
     * 请求状态（是否成功
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
