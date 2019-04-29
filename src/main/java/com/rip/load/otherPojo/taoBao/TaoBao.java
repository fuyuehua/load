package com.rip.load.otherPojo.taoBao;


import java.util.List;

public class TaoBao {

    /**
     *用户基本信息
     */
    private BasicInfo basicInfo;

    /**
     *绑定的支付宝信息
     */
    private AlipayInfo alipayInfo;

    /**
     *收货地址
     */
    private List<Addresses> addresses;

    /**
     *订单信息
     */
    private List<OrderDetails> orderDetails;

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public AlipayInfo getAlipayInfo() {
        return alipayInfo;
    }

    public void setAlipayInfo(AlipayInfo alipayInfo) {
        this.alipayInfo = alipayInfo;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Addresses> addresses) {
        this.addresses = addresses;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
