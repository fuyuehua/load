package com.rip.load.pojo.businessData;
/**
 *严重违法信息
 */
public class BreakLaw {

    /**
     * 列入日期
     */
    private String inDate;

    /**
     * 列入原因
     */
    private String inReason;

    /**
     * 列入作出决定机关
     */
    private String inRegOrg;
    /**
     * 列入作出决定文号
     */
    private String inSn;
    /**
     * 移出日期
     */
    private String outDate;

    /**
     * 移出原因
     */
    private String outReason;


    /**
     * 移出作出决定机关
     */
    private String outRegOrg;

    /**
     * 移出作出决定文号
     */
    private String outSn;


    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getInReason() {
        return inReason;
    }

    public void setInReason(String inReason) {
        this.inReason = inReason;
    }

    public String getInRegOrg() {
        return inRegOrg;
    }

    public void setInRegOrg(String inRegOrg) {
        this.inRegOrg = inRegOrg;
    }

    public String getInSn() {
        return inSn;
    }

    public void setInSn(String inSn) {
        this.inSn = inSn;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    public String getOutRegOrg() {
        return outRegOrg;
    }

    public void setOutRegOrg(String outRegOrg) {
        this.outRegOrg = outRegOrg;
    }

    public String getOutSn() {
        return outSn;
    }

    public void setOutSn(String outSn) {
        this.outSn = outSn;
    }
}
