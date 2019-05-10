package com.rip.load.otherPojo.operatorCreditReport;

/**
 * 社交概况
 */
public class SocialContactProfile {

    /**
     * 最长联系人区域
     */
    private String freContactArea;
    /**
     * 联系人号码总数
     */
    private String contactNumCnt;
    /**
     * 互通号码数
     */
    private String interflowContactCnt;
    /**
     * 联系人风险名单总数
     */
    private String contactRishCnt;

    public String getFreContactArea() {
        return freContactArea;
    }

    public void setFreContactArea(String freContactArea) {
        this.freContactArea = freContactArea;
    }

    public String getContactNumCnt() {
        return contactNumCnt;
    }

    public void setContactNumCnt(String contactNumCnt) {
        this.contactNumCnt = contactNumCnt;
    }

    public String getInterflowContactCnt() {
        return interflowContactCnt;
    }

    public void setInterflowContactCnt(String interflowContactCnt) {
        this.interflowContactCnt = interflowContactCnt;
    }

    public String getContactRishCnt() {
        return contactRishCnt;
    }

    public void setContactRishCnt(String contactRishCnt) {
        this.contactRishCnt = contactRishCnt;
    }
}
