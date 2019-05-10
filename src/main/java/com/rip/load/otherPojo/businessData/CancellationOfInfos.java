package com.rip.load.otherPojo.businessData;
/**
 *股权出质信息-注销信息
 */
public class CancellationOfInfos {

    /**
     *注销日期
     */
    private String stkPawnDate;

    /**
     *注销原因
     */
    private String stkPawnRes;

    /**
     *关联内容
     */
    private String content;

    public String getStkPawnDate() {
        return stkPawnDate;
    }

    public void setStkPawnDate(String stkPawnDate) {
        this.stkPawnDate = stkPawnDate;
    }

    public String getStkPawnRes() {
        return stkPawnRes;
    }

    public void setStkPawnRes(String stkPawnRes) {
        this.stkPawnRes = stkPawnRes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
