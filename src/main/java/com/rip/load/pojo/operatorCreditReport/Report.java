package com.rip.load.pojo.operatorCreditReport;

/**
 * 报告情况
 */
public class Report {

    /**
     * 数据来源
     */
    private String dataSource;
    /**
     * 报告时间
     */
    private String reportTime;
    /**
     * 报告编号
     */
    private String reportNo;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }
}
