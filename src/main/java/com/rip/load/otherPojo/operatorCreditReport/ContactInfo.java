package com.rip.load.otherPojo.operatorCreditReport;

/**
 * 紧急联系人信息
 */
public class ContactInfo {

    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 身份证号
     */
    private String identityNo;
    /**
     * 与本人关系
     */
    private String relationship;
    /**
     * 通话次数
     */
    private String callCnt;
    /**
     * 通话时长(s)
     */
    private String callTime;
    /**
     * 通话频度排名
     */
    private String callRank;
    /**
     * 是否命中风险清单 1：命中，0：未命中
     */
    private String isHitRiskList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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

    public String getCallRank() {
        return callRank;
    }

    public void setCallRank(String callRank) {
        this.callRank = callRank;
    }

    public String getIsHitRiskList() {
        return isHitRiskList;
    }

    public void setIsHitRiskList(String isHitRiskList) {
        this.isHitRiskList = isHitRiskList;
    }
}
