package com.rip.load.otherPojo.personalComplaintInquiryC;

/**
 * 裁判文书
 */
public class CPWX {


    /**
     * 裁判文书ID
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
     * 审结时间（时间戳）
     */
    private String recordTime;
    /**
     * 审结时间（年月日形式）
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
}
