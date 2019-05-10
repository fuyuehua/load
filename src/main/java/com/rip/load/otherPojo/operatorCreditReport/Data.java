package com.rip.load.otherPojo.operatorCreditReport;

import java.util.List;

public class Data {

    /**
     * 报告情况
     */
    private Report report;
    /**
     * 基本信息
     */
    private BasicInfo basicInfo;
    /**
     * 紧急联系人信息
     */
    private List<ContactInfo> contactInfo;
    /**
     * 关联信息
     */
    private RelationInfo relationInfo;
    /**
     * 用户画像
     */
    private Personas personas;
    /**
     * 基本信息检测
     */
    private List<BasicInfoCheck> basicInfoCheck;
    /**
     * 风险清单检测
     */
    private List<RiskListCheck> riskListCheck;
    /**
     * 信贷逾期检查
     */
    private List<OverdueLoanCheck> overdueLoanCheck;
    /**
     * 多重借贷检查
     */
    private List<MultiLendCheck> multiLendCheck;
    /**
     * 风险通话检测
     */
    private List<RiskCallCheck> riskCallCheck;
    /**
     * 通话概况
     */
    private CallAnalysis callAnalysis;
    /**
     * 活跃情况
     */
    private List<ActiveCallAnalysis> activeCallAnalysis;
    /**
     *静默情况
     */
    private SilenceAnalysis silenceAnalysis;
    /**
     * 通话时间段分析
     */
    private List<CallDurationAnalysis> callDurationAnalysis;
    /**
     * 社交关系概况
     */
    private List<SocialContactAnalysis> socialContactAnalysis;
    /**
     * 通话联系人分析
     */
    private List<ContactAnalysis> contactAnalysis;
    /**
     * 通话区域分析
     */
    private List<CallAreaAnalysis> callAreaAnalysis;
    /**
     * 消费能力
     */
    private List<ConsumptionAnalysis> consumptionAnalysis;
    /**
     * 出行信息
     */
    private List<TripAnalysis> tripAnalysis;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public List<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<ContactInfo> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public RelationInfo getRelationInfo() {
        return relationInfo;
    }

    public void setRelationInfo(RelationInfo relationInfo) {
        this.relationInfo = relationInfo;
    }

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }

    public List<BasicInfoCheck> getBasicInfoCheck() {
        return basicInfoCheck;
    }

    public void setBasicInfoCheck(List<BasicInfoCheck> basicInfoCheck) {
        this.basicInfoCheck = basicInfoCheck;
    }

    public List<RiskListCheck> getRiskListCheck() {
        return riskListCheck;
    }

    public void setRiskListCheck(List<RiskListCheck> riskListCheck) {
        this.riskListCheck = riskListCheck;
    }

    public List<OverdueLoanCheck> getOverdueLoanCheck() {
        return overdueLoanCheck;
    }

    public void setOverdueLoanCheck(List<OverdueLoanCheck> overdueLoanCheck) {
        this.overdueLoanCheck = overdueLoanCheck;
    }

    public List<MultiLendCheck> getMultiLendCheck() {
        return multiLendCheck;
    }

    public void setMultiLendCheck(List<MultiLendCheck> multiLendCheck) {
        this.multiLendCheck = multiLendCheck;
    }

    public List<RiskCallCheck> getRiskCallCheck() {
        return riskCallCheck;
    }

    public void setRiskCallCheck(List<RiskCallCheck> riskCallCheck) {
        this.riskCallCheck = riskCallCheck;
    }

    public CallAnalysis getCallAnalysis() {
        return callAnalysis;
    }

    public void setCallAnalysis(CallAnalysis callAnalysis) {
        this.callAnalysis = callAnalysis;
    }

    public List<ActiveCallAnalysis> getActiveCallAnalysis() {
        return activeCallAnalysis;
    }

    public void setActiveCallAnalysis(List<ActiveCallAnalysis> activeCallAnalysis) {
        this.activeCallAnalysis = activeCallAnalysis;
    }

    public SilenceAnalysis getSilenceAnalysis() {
        return silenceAnalysis;
    }

    public void setSilenceAnalysis(SilenceAnalysis silenceAnalysis) {
        this.silenceAnalysis = silenceAnalysis;
    }

    public List<CallDurationAnalysis> getCallDurationAnalysis() {
        return callDurationAnalysis;
    }

    public void setCallDurationAnalysis(List<CallDurationAnalysis> callDurationAnalysis) {
        this.callDurationAnalysis = callDurationAnalysis;
    }

    public List<SocialContactAnalysis> getSocialContactAnalysis() {
        return socialContactAnalysis;
    }

    public void setSocialContactAnalysis(List<SocialContactAnalysis> socialContactAnalysis) {
        this.socialContactAnalysis = socialContactAnalysis;
    }

    public List<ContactAnalysis> getContactAnalysis() {
        return contactAnalysis;
    }

    public void setContactAnalysis(List<ContactAnalysis> contactAnalysis) {
        this.contactAnalysis = contactAnalysis;
    }

    public List<CallAreaAnalysis> getCallAreaAnalysis() {
        return callAreaAnalysis;
    }

    public void setCallAreaAnalysis(List<CallAreaAnalysis> callAreaAnalysis) {
        this.callAreaAnalysis = callAreaAnalysis;
    }

    public List<ConsumptionAnalysis> getConsumptionAnalysis() {
        return consumptionAnalysis;
    }

    public void setConsumptionAnalysis(List<ConsumptionAnalysis> consumptionAnalysis) {
        this.consumptionAnalysis = consumptionAnalysis;
    }

    public List<TripAnalysis> getTripAnalysis() {
        return tripAnalysis;
    }

    public void setTripAnalysis(List<TripAnalysis> tripAnalysis) {
        this.tripAnalysis = tripAnalysis;
    }
}
