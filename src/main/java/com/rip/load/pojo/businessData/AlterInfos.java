package com.rip.load.pojo.businessData;

/**
 *变更信息
 */
public class AlterInfos {

    /**
     *变更日期
     */
    private String altDate;

    /**
     *变更事项
     */
    private String altItem;

    /**
     *变更前内容
     */
    private String altBe;

    /**
     *变更后内容
     */
    private String altAf;

    public String getAltDate() {
        return altDate;
    }

    public void setAltDate(String altDate) {
        this.altDate = altDate;
    }

    public String getAltItem() {
        return altItem;
    }

    public void setAltItem(String altItem) {
        this.altItem = altItem;
    }

    public String getAltBe() {
        return altBe;
    }

    public void setAltBe(String altBe) {
        this.altBe = altBe;
    }

    public String getAltAf() {
        return altAf;
    }

    public void setAltAf(String altAf) {
        this.altAf = altAf;
    }
}
