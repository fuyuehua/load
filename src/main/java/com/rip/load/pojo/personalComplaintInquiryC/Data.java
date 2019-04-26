package com.rip.load.pojo.personalComplaintInquiryC;

import java.util.List;

public class Data {
    /**
     * 身份证
     */
    private String identityCard;
    /**
     * 姓名
     */
    private String name;
    /**
     * 涉诉信息
     */
    private List<PageData> pageData;
    /**
     * 检查状态
     */
    private String checkStatus;
    /**
     * 分页信息
     */
    private PageInfo pageInfo;

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PageData> getPageData() {

        return pageData;
    }

    public void setPageData(List<PageData> pageData) {

        this.pageData = pageData;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
