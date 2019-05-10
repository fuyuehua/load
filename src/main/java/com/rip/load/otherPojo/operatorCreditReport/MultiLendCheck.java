package com.rip.load.otherPojo.operatorCreditReport;

import java.util.List;

/**
 * 多重借贷检查
 */
public class MultiLendCheck {

    /**
     * 检查项
     */
    private String item;
    /**
     * 检查项描述
     */
    private String desc;
    /**
     * 细节
     */
    private List<MultiLendCheckDetails> details;

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

    public List<MultiLendCheckDetails> getDetails() {
        return details;
    }

    public void setDetails(List<MultiLendCheckDetails> details) {
        this.details = details;
    }
}