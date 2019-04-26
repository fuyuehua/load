package com.rip.load.pojo.operatorCreditReport;

/**
 * 消费能力
 */
public class ConsumptionAnalysis {
    /**
     * 项目
     */
    private String item;
    /**
     * 描述
     */
    private String desc;
    /**
     * 近1月
     */
    private String lately1m;
    /**
     * 近3月
     */
    private String lately3m;
    /**
     * 近6月
     */
    private String lately6m;
    /**
     * 月均
     */
    private String avgMonth;


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLately1m() {
        return lately1m;
    }

    public void setLately1m(String lately1m) {
        this.lately1m = lately1m;
    }

    public String getLately3m() {
        return lately3m;
    }

    public void setLately3m(String lately3m) {
        this.lately3m = lately3m;
    }

    public String getLately6m() {
        return lately6m;
    }

    public void setLately6m(String lately6m) {
        this.lately6m = lately6m;
    }

    public String getAvgMonth() {
        return avgMonth;
    }

    public void setAvgMonth(String avgMonth) {
        this.avgMonth = avgMonth;
    }
}
