package com.rip.load.otherPojo.taoBao;
/**
 *物流信息
 */
public class LogisticsInfo {

    /**
     *运送方式
     */
    private String deliverType;

    /**
     *物流公司
     */
    private String deliverCompany;

    /**
     *送货单号
     */
    private String deliverId;

    /**
     *收货人姓名
     */
    private String receivePersonName;
    /**
     *收货人联系电话
     */
    private String receivePersonMobile;

    /**
     *收货地址
     */
    private String receiveAddress;

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public String getDeliverCompany() {
        return deliverCompany;
    }

    public void setDeliverCompany(String deliverCompany) {
        this.deliverCompany = deliverCompany;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getReceivePersonName() {
        return receivePersonName;
    }

    public void setReceivePersonName(String receivePersonName) {
        this.receivePersonName = receivePersonName;
    }

    public String getReceivePersonMobile() {
        return receivePersonMobile;
    }

    public void setReceivePersonMobile(String receivePersonMobile) {
        this.receivePersonMobile = receivePersonMobile;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }
}
