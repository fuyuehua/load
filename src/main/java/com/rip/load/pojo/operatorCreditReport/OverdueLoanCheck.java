package com.rip.load.pojo.operatorCreditReport;

import java.util.List;

/**
 * 信贷逾期检查
 */
public class OverdueLoanCheck {

    /**
     * 检查项
     */
    private String item;
    /**
     * 检查项描述
     */
    private String desc;
    private List<Details> details;

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

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }
}
