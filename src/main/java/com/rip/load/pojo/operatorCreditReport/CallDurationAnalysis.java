package com.rip.load.pojo.operatorCreditReport;

/**
 * 通话时间段分析
 */
public class CallDurationAnalysis {

    /**
     * 项目
     */
    private String item;
    /**
     * 描述
     */
    private String desc;
    /**
     * 通话次数
     */
    private String callCnt;
    /**
     * 通话号码处
     */
    private String callNumCnt;
    /**
     * 最常联系号码
     */
    private String freqContactNum;
    /**
     * 最长联系号码次数
     */
    private String freqContactNumCnt;
    /**
     * 平均通话时长
     */
    private String avgCallTime;
    /**
     * 主叫次数
     */
    private String callingCnt;
    /**
     * 主叫时长(s)
     */
    private String callingTime;
    /**
     * 被叫次数
     */
    private String calledCnt;
    /**
     * 被叫时长(s)
     */
    private String calledTime;

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

    public String getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(String callCnt) {
        this.callCnt = callCnt;
    }

    public String getCallNumCnt() {
        return callNumCnt;
    }

    public void setCallNumCnt(String callNumCnt) {
        this.callNumCnt = callNumCnt;
    }

    public String getFreqContactNum() {
        return freqContactNum;
    }

    public void setFreqContactNum(String freqContactNum) {
        this.freqContactNum = freqContactNum;
    }

    public String getFreqContactNumCnt() {
        return freqContactNumCnt;
    }

    public void setFreqContactNumCnt(String freqContactNumCnt) {
        this.freqContactNumCnt = freqContactNumCnt;
    }

    public String getAvgCallTime() {
        return avgCallTime;
    }

    public void setAvgCallTime(String avgCallTime) {
        this.avgCallTime = avgCallTime;
    }

    public String getCallingCnt() {
        return callingCnt;
    }

    public void setCallingCnt(String callingCnt) {
        this.callingCnt = callingCnt;
    }

    public String getCallingTime() {
        return callingTime;
    }

    public void setCallingTime(String callingTime) {
        this.callingTime = callingTime;
    }

    public String getCalledCnt() {
        return calledCnt;
    }

    public void setCalledCnt(String calledCnt) {
        this.calledCnt = calledCnt;
    }

    public String getCalledTime() {
        return calledTime;
    }

    public void setCalledTime(String calledTime) {
        this.calledTime = calledTime;
    }
}
