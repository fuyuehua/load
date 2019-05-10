package com.rip.load.otherPojo.operatorCreditReport;

/**
 * 通话区域分析
 */
public class CallAreaAnalysis {

    /**
     * 通话地
     */
    private String attribution;
    /**
     * 通话次数
     */
    private String callCnt;
    /**
     * 通话号码数
     */
    private String callNumCnt;
    /**
     * 通话时长
     */
    private String callTime;
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

    public String getCallNumCnt() {
        return callNumCnt;
    }

    public void setCallNumCnt(String callNumCnt) {
        this.callNumCnt = callNumCnt;
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
}
