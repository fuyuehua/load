package com.rip.load.pojo.personalEnterprise;
/**
 *行政处罚历史信息
 */
public class CaseInfos {

    /**
     * 处罚决定文书
     */
    private String penDecNo;

    /**
     * 处罚决定书签发日期
     */
    private String penDecIssDate;

    /**
     * 处罚机关
     */
    private String penAuth;

    /**
     * 处罚机关名
     */
    private String penAuthCn;

    /**
     * 主要违法事实
     */
    private String illegFact;

    /**
     * 处罚种类
     */
    private String penType;

    /**
     * 处罚种类中文
     */
    private String penTypeDescription;

    /**
     * 处罚内容
     */
    private String penContent;

    /**
     * 公示日期
     */
    private String announcementDate;

    public String getPenDecNo() {
        return penDecNo;
    }

    public void setPenDecNo(String penDecNo) {
        this.penDecNo = penDecNo;
    }

    public String getPenDecIssDate() {
        return penDecIssDate;
    }

    public void setPenDecIssDate(String penDecIssDate) {
        this.penDecIssDate = penDecIssDate;
    }

    public String getPenAuth() {
        return penAuth;
    }

    public void setPenAuth(String penAuth) {
        this.penAuth = penAuth;
    }

    public String getPenAuthCn() {
        return penAuthCn;
    }

    public void setPenAuthCn(String penAuthCn) {
        this.penAuthCn = penAuthCn;
    }

    public String getIllegFact() {
        return illegFact;
    }

    public void setIllegFact(String illegFact) {
        this.illegFact = illegFact;
    }

    public String getPenType() {
        return penType;
    }

    public void setPenType(String penType) {
        this.penType = penType;
    }

    public String getPenTypeDescription() {
        return penTypeDescription;
    }

    public void setPenTypeDescription(String penTypeDescription) {
        this.penTypeDescription = penTypeDescription;
    }

    public String getPenContent() {
        return penContent;
    }

    public void setPenContent(String penContent) {
        this.penContent = penContent;
    }

    public String getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(String announcementDate) {
        this.announcementDate = announcementDate;
    }
}
