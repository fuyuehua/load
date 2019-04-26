package com.rip.load.pojo.businessData;
/**
 *股权出质信息-变更信息
 */
public class EquityChangeInfos {

    /**
     *变更内容
     */
    private String stkPawnBgnr;

    /**
     *变更日期
     */
    private String stkPawnBgrq;

    /**
     *关联内容
     */
    private String content;

    public String getStkPawnBgnr() {
        return stkPawnBgnr;
    }

    public void setStkPawnBgnr(String stkPawnBgnr) {
        this.stkPawnBgnr = stkPawnBgnr;
    }

    public String getStkPawnBgrq() {
        return stkPawnBgrq;
    }

    public void setStkPawnBgrq(String stkPawnBgrq) {
        this.stkPawnBgrq = stkPawnBgrq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
