package com.rip.load.otherPojo.taoBao;

import java.util.List;

/**
 *订单信息
 */
public class OrderDetails {

    /**
     *订单号
     */
    private String orderId;

    /**
     *订单时间
     */
    private String orderTime;


    /**
     *订单金额
     */
    private String orderAmt;


    /**
     *订单状态（交易成功、交易关闭、等待买家付款、买家已付款、买家已发货、退款中）
     */
    private String orderStatus;


    /**
     *商品信息
     */
    private List<Items> items;

    /**
     *物流信息
     */
    private LogisticsInfo logisticsInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public LogisticsInfo getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(LogisticsInfo logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }
}
