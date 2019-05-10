package com.rip.load.otherPojo.personalComplaintInquiryC;

public class PageData {

    /** 裁判文书 **/
    /**
     * 裁判文书ID/执行公告ID/开庭公告ID/失信公告ID
     */
    private String id;
    /**
     * 内容
     */
    private String content;
    /**
     * 法院名称
     */
    private String court;
    /**
     * 标题
     */
    private String title;
    /**
     * 案号
     */
    private String caseNO;
    /**
     * 类别
     */
    private String dataType;
    /***
     * 案由
     */
    private String caseCause;
    /**
     * 判决结果
     */
    private String judgeResult;
    /**
     * 匹配度
     */
    private String matchRatio;
    /**
     * 审结时间（时间戳）/立案时间/开庭时间
     */
    private String recordTime;
    /**
     * 审结时间（年月日形式）/立案时间/开庭时间
     */
    private String time;
    /**
     * 当事人ID
     */
    private String partyId;
    /**
     * 当事人列表
     */
    private Litigants litigants;
    /**
     * 案件类型
     */
    private String caseType;

    /** 执行公告 **/
    /**
     * 案件状态
     */
    private String caseStatus;
    /**
     * 被执行人姓名
     */
    private String name;

    /**
     * 执行标的
     */
    private String executionTarget;

    /**
     * 身份证
     */
    private String identificationNO;

    /** 开庭公告 **/
    /**
     * 被告
     */
    private String defendant;

    /**
     * 法官
     */
    private String judge;

    /**
     * 当事人
     */
    private String party;

    /**
     * 原告
     */
    private String plaintiff;
    /**
     * 法庭
     */
    private String courtroom;

    /** 失信公告 **/
    /**
     * 履行情况
     */
    private String implementationStatus;

    /**
     * 依据案号
     */
    private String evidenceCode;

    /**
     * 做出执行依据单位
     */
    private String executableUnit;

    /**
     * 失信被执行人行为具体情形
     */
    private String specificCircumstances;

    /**
     * 生效法律文书确定的义务
     */
    private String obligations;

    /**
     * 省份
     */
    private String province;

    /**
     * 发布时间
     */
    private String postTime;

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别
     */
    private String gender;

    /** 法院公告 **/

    /**
     * 公告类型
     */
    private String announcementType;

    /** 网贷黑名单 **/
    /**
     *本金/本息
     */
    private String principal;
    /**
     * 未还/罚息
     */
    private String penalty;
    /**
     * 已还金额
     */
    private String paid;
    /**
     * 信息更新时间
     */
    private String updateTime;
    /**
     * 相关当事人
     */
    private String relatedParty;
    /**
     * 数据来源单位名称
     */
    private String sourceName;
    /** 案件流程 **/
    /**
     * 流程状态
     */
    private String status;

    /** 曝光台 **/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaseNO() {
        return caseNO;
    }

    public void setCaseNO(String caseNO) {
        this.caseNO = caseNO;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCaseCause() {
        return caseCause;
    }

    public void setCaseCause(String caseCause) {
        this.caseCause = caseCause;
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult;
    }

    public String getMatchRatio() {
        return matchRatio;
    }

    public void setMatchRatio(String matchRatio) {
        this.matchRatio = matchRatio;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public Litigants getLitigants() {
        return litigants;
    }

    public void setLitigants(Litigants litigants) {
        this.litigants = litigants;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecutionTarget() {
        return executionTarget;
    }

    public void setExecutionTarget(String executionTarget) {
        this.executionTarget = executionTarget;
    }

    public String getIdentificationNO() {
        return identificationNO;
    }

    public void setIdentificationNO(String identificationNO) {
        this.identificationNO = identificationNO;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public String getCourtroom() {
        return courtroom;
    }

    public void setCourtroom(String courtroom) {
        this.courtroom = courtroom;
    }

    public String getImplementationStatus() {
        return implementationStatus;
    }

    public void setImplementationStatus(String implementationStatus) {
        this.implementationStatus = implementationStatus;
    }

    public String getEvidenceCode() {
        return evidenceCode;
    }

    public void setEvidenceCode(String evidenceCode) {
        this.evidenceCode = evidenceCode;
    }

    public String getExecutableUnit() {
        return executableUnit;
    }

    public void setExecutableUnit(String executableUnit) {
        this.executableUnit = executableUnit;
    }

    public String getSpecificCircumstances() {
        return specificCircumstances;
    }

    public void setSpecificCircumstances(String specificCircumstances) {
        this.specificCircumstances = specificCircumstances;
    }

    public String getObligations() {
        return obligations;
    }

    public void setObligations(String obligations) {
        this.obligations = obligations;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(String announcementType) {
        this.announcementType = announcementType;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(String relatedParty) {
        this.relatedParty = relatedParty;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
