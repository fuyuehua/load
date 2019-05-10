package com.rip.load.otherPojo.operatorCreditReport;

/**
 *静默情况
 */
public class SilenceAnalysis {
    /**
     * 静默次数
     */
    private String silenceCnt;
    /**
     * 静默总时长
     */
    private String silenceTime;
    /**
     * 最后一次静默开始时间
     */
    private String longestSilenceStart;
    /**
     * 最后一次静默时长(s)
     */
    private String longestSilenceTime;
    /**
     * 最近一次静默开始时间
     */
    private String lastSilenceStart;
    /**
     * 最近一次静默时长(s)
     */
    private String lastSilenceTime;

    public String getSilenceCnt() {
        return silenceCnt;
    }

    public void setSilenceCnt(String silenceCnt) {
        this.silenceCnt = silenceCnt;
    }

    public String getSilenceTime() {
        return silenceTime;
    }

    public void setSilenceTime(String silenceTime) {
        this.silenceTime = silenceTime;
    }

    public String getLongestSilenceStart() {
        return longestSilenceStart;
    }

    public void setLongestSilenceStart(String longestSilenceStart) {
        this.longestSilenceStart = longestSilenceStart;
    }

    public String getLongestSilenceTime() {
        return longestSilenceTime;
    }

    public void setLongestSilenceTime(String longestSilenceTime) {
        this.longestSilenceTime = longestSilenceTime;
    }

    public String getLastSilenceStart() {
        return lastSilenceStart;
    }

    public void setLastSilenceStart(String lastSilenceStart) {
        this.lastSilenceStart = lastSilenceStart;
    }

    public String getLastSilenceTime() {
        return lastSilenceTime;
    }

    public void setLastSilenceTime(String lastSilenceTime) {
        this.lastSilenceTime = lastSilenceTime;
    }
}
