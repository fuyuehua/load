package com.rip.load.otherPojo.operatorCreditReport;

/**
 * 风险概况
 */
public class RiskProfile {

    /**
     * 命中风险清单次数
     */
    private String riskListCnt;
    /**
     * 信贷逾期次数
     */
    private String overdueLoanCnt;
    /**
     * 多头借贷次数
     */
    private String multiLendCnt;
    /**
     * 风险通话次数
     */
    private String riskCallCnt;

    public String getRiskListCnt() {
        return riskListCnt;
    }

    public void setRiskListCnt(String riskListCnt) {
        this.riskListCnt = riskListCnt;
    }

    public String getOverdueLoanCnt() {
        return overdueLoanCnt;
    }

    public void setOverdueLoanCnt(String overdueLoanCnt) {
        this.overdueLoanCnt = overdueLoanCnt;
    }

    public String getMultiLendCnt() {
        return multiLendCnt;
    }

    public void setMultiLendCnt(String multiLendCnt) {
        this.multiLendCnt = multiLendCnt;
    }

    public String getRiskCallCnt() {
        return riskCallCnt;
    }

    public void setRiskCallCnt(String riskCallCnt) {
        this.riskCallCnt = riskCallCnt;
    }
}
