package com.rip.load.pojo.personalComplaintInquiryC;
/**
 * 执行公告
 */
public class ZXGG {

    /**
     * 执行公告ID
     */
    private String id;
    /**
     * 案件状态
     */
    private String caseStatus;
    /**
     * 被执行人姓名
     */
    private String name;
    /**
     * 内容
     */
    private String content;
    /**
     * 执行标的
     */
    private String executionTarget;
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
     * 身份证
     */
    private String identificationNO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExecutionTarget() {
        return executionTarget;
    }

    public void setExecutionTarget(String executionTarget) {
        this.executionTarget = executionTarget;
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

    public String getIdentificationNO() {
        return identificationNO;
    }

    public void setIdentificationNO(String identificationNO) {
        this.identificationNO = identificationNO;
    }
}
