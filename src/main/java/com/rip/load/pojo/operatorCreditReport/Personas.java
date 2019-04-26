package com.rip.load.pojo.operatorCreditReport;

/**
 * 用户画像
 */
public class Personas {

    /**
     * 风险概况
     */
    private RiskProfile riskProfile;
    /**
     * 社交概况
     */
    private SocialContactProfile socialContactProfile;
    /**
     * 通话概况
     */
    private CallProfile callProfile;
    /**
     * 消费概况
     */
    private ConsumptionProfile consumptionProfile;

    public RiskProfile getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }

    public SocialContactProfile getSocialContactProfile() {
        return socialContactProfile;
    }

    public void setSocialContactProfile(SocialContactProfile socialContactProfile) {
        this.socialContactProfile = socialContactProfile;
    }

    public CallProfile getCallProfile() {
        return callProfile;
    }

    public void setCallProfile(CallProfile callProfile) {
        this.callProfile = callProfile;
    }

    public ConsumptionProfile getConsumptionProfile() {
        return consumptionProfile;
    }

    public void setConsumptionProfile(ConsumptionProfile consumptionProfile) {
        this.consumptionProfile = consumptionProfile;
    }
}
