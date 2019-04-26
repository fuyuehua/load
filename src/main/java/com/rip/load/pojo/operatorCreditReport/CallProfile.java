package com.rip.load.pojo.operatorCreditReport;

/**
 * 通话概况
 */
public class CallProfile {

    /**
     * 日均通话次数
     */
    private String avgCallCnt;
    /**
     * 日均通话时长(m)
     */
    private String avgCallTime;
    /**
     * 静默次数
     */
    private String silenceCnt;
    /**
     * 夜间通话次数
     */
    private String nightCallCnt;
    /**
     * 夜间平均通话时长
     */
    private String nightCallTime;

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

    public String getSilenceCnt() {
        return silenceCnt;
    }

    public void setSilenceCnt(String silenceCnt) {
        this.silenceCnt = silenceCnt;
    }

    public String getNightCallCnt() {
        return nightCallCnt;
    }

    public void setNightCallCnt(String nightCallCnt) {
        this.nightCallCnt = nightCallCnt;
    }

    public String getNightCallTime() {
        return nightCallTime;
    }

    public void setNightCallTime(String nightCallTime) {
        this.nightCallTime = nightCallTime;
    }
}
