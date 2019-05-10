package com.rip.load.otherPojo.operatorCreditReport;

public class RiskCallCheckDetails {

    /**
     * 通话标记
     */
    private String callTag;
    /**
     * 通话类型
     */
    private String callType;
    /**
     * 通话次数
     */
    private String callCnt;
    /**
     * 通话时长(s)
     */
    private String callTime;

    public String getCallTag() {
        return callTag;
    }

    public void setCallTag(String callTag) {
        this.callTag = callTag;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
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
}
