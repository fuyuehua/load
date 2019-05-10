package com.rip.load.otherPojo.businessData;

/**
 *股权冻结历史信息
 */
public class SharesFrosts {

    /**
     * 冻结文号
     */
    private String froDocNo;

    /**
     *冻结机关
     */
    private String froAuth;

    /**
     * 冻结起始日期
     */
    private String froFrom;

    /**
     * 冻结截至日期
     */
    private String froTo;
    /**
     *冻结金额（万元）
     */
    private String froAm;

    /**
     *解冻机关
     */
    private String thawAuth;

    /**
     *解冻文号
     */
    private String thawDocNo;

    /**
     *解冻日期
     */
    private String thawDate;

    /**
     *解冻说明
     */
    private String thawComment;


    public String getFroDocNo() {
        return froDocNo;
    }

    public void setFroDocNo(String froDocNo) {
        this.froDocNo = froDocNo;
    }

    public String getFroAuth() {
        return froAuth;
    }

    public void setFroAuth(String froAuth) {
        this.froAuth = froAuth;
    }

    public String getFroFrom() {
        return froFrom;
    }

    public void setFroFrom(String froFrom) {
        this.froFrom = froFrom;
    }

    public String getFroTo() {
        return froTo;
    }

    public void setFroTo(String froTo) {
        this.froTo = froTo;
    }

    public String getFroAm() {
        return froAm;
    }

    public void setFroAm(String froAm) {
        this.froAm = froAm;
    }

    public String getThawAuth() {
        return thawAuth;
    }

    public void setThawAuth(String thawAuth) {
        this.thawAuth = thawAuth;
    }

    public String getThawDocNo() {
        return thawDocNo;
    }

    public void setThawDocNo(String thawDocNo) {
        this.thawDocNo = thawDocNo;
    }

    public String getThawDate() {
        return thawDate;
    }

    public void setThawDate(String thawDate) {
        this.thawDate = thawDate;
    }

    public String getThawComment() {
        return thawComment;
    }

    public void setThawComment(String thawComment) {
        this.thawComment = thawComment;
    }
}
