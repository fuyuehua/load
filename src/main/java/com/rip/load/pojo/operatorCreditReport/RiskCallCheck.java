package com.rip.load.pojo.operatorCreditReport;

import java.util.List;

/**
 * 风险通话检测
 */
public class RiskCallCheck {

    /**
     * 检查项
     */
    private String item;
    /**
     * 检查项描述
     */
    private String desc;
    /**
     * 命中描述
     */
    private String hitDesc;
    /**
     * 命中次数
     */
    private String cnt;
    /**
     * 时长(s)
     */
    private String duration;

    private List<RiskCallCheckDetails> details;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHitDesc() {
        return hitDesc;
    }

    public void setHitDesc(String hitDesc) {
        this.hitDesc = hitDesc;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<RiskCallCheckDetails> getDetails() {
        return details;
    }

    public void setDetails(List<RiskCallCheckDetails> details) {
        this.details = details;
    }
}
