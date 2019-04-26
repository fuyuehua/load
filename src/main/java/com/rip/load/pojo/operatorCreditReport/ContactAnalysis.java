package com.rip.load.pojo.operatorCreditReport;

/**
 * 通话联系人分析
 */
public class ContactAnalysis {
    /**
     * 号码
     */
    private String callNum;
    /**
     * 电话标记
     */
    private String callTag;
    /**
     * 是否命中风险名单（1：命中，0:未命中）
     */
    private String isHitRiskList;
    /**
     * 归属地
     */
    private String attribution;
    /**
     * 通话次数
     */
    private String callCnt;
    /**
     * 通话时长
     */
    private String callTime;
    /**
     * 主叫次数
     */
    private String callingCnt;
    /**
     * 主叫时长
     */
    private String callingTime;
    /**
     * 被叫次数
     */
    private String calledCnt;
    /**
     * 被叫时长（s）
     */
    private String calledTime;
    /**
     * 最近一次通话时间
     */
    private String lastStart;
    /**
     * 最近一次通话时长(s)
     */
    private String lastTime;


    public String getCallNum() {
        return callNum;
    }

    public void setCallNum(String callNum) {
        this.callNum = callNum;
    }

    public String getCallTag() {
        return callTag;
    }

    public void setCallTag(String callTag) {
        this.callTag = callTag;
    }

    public String getIsHitRiskList() {
        return isHitRiskList;
    }

    public void setIsHitRiskList(String isHitRiskList) {
        this.isHitRiskList = isHitRiskList;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(String callCnt) {
        this.callCnt = callCnt;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
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

    public String getLastStart() {
        return lastStart;
    }

    public void setLastStart(String lastStart) {
        this.lastStart = lastStart;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
