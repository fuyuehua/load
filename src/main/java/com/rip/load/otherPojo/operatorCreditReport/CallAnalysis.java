package com.rip.load.otherPojo.operatorCreditReport;

/**
 * 通话概况
 */
public class CallAnalysis {

    /**
     * 日均通话次数
     */
    private String avgCallCnt;
    /**
     * 日均通话时长(s)
     */
    private String avgCallTime;
    /**
     * 日均主叫次数
     */
    private String avgCallingCnt;
    /**
     * 日均主叫时长(s)
     */
    private String avgCallingTime;
    /**
     * 日均被叫次数
     */
    private String avgCalledCnt;
    /**
     * 日均被叫时长
     */
    private String avgCalledTime;
    /**
     * 本地通话占比
     */
    private String locCallPct;

    public String getAvgCallCnt() {
        return avgCallCnt;
    }

    public void setAvgCallCnt(String avgCallCnt) {
        this.avgCallCnt = avgCallCnt;
    }

    public String getAvgCallTime() {
        return avgCallTime;
    }

    public void setAvgCallTime(String avgCallTime) {
        this.avgCallTime = avgCallTime;
    }

    public String getAvgCallingCnt() {
        return avgCallingCnt;
    }

    public void setAvgCallingCnt(String avgCallingCnt) {
        this.avgCallingCnt = avgCallingCnt;
    }

    public String getAvgCallingTime() {
        return avgCallingTime;
    }

    public void setAvgCallingTime(String avgCallingTime) {
        this.avgCallingTime = avgCallingTime;
    }

    public String getAvgCalledCnt() {
        return avgCalledCnt;
    }

    public void setAvgCalledCnt(String avgCalledCnt) {
        this.avgCalledCnt = avgCalledCnt;
    }

    public String getAvgCalledTime() {
        return avgCalledTime;
    }

    public void setAvgCalledTime(String avgCalledTime) {
        this.avgCalledTime = avgCalledTime;
    }

    public String getLocCallPct() {
        return locCallPct;
    }

    public void setLocCallPct(String locCallPct) {
        this.locCallPct = locCallPct;
    }
}
