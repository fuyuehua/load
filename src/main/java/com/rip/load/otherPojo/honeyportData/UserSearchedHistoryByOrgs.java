package com.rip.load.otherPojo.honeyportData;

public class UserSearchedHistoryByOrgs {

    /**
     * 主动查询用户信息的机构类型
     */
    private String searched_org;
    /**
     *是否是本机构查询
     */
    private String org_self;
    /**
     *查询时间
     */
    private String searched_date;

    public String getSearched_org() {
        return searched_org;
    }

    public void setSearched_org(String searched_org) {
        this.searched_org = searched_org;
    }

    public String getOrg_self() {
        return org_self;
    }

    public void setOrg_self(String org_self) {
        this.org_self = org_self;
    }

    public String getSearched_date() {
        return searched_date;
    }

    public void setSearched_date(String searched_date) {
        this.searched_date = searched_date;
    }
}
