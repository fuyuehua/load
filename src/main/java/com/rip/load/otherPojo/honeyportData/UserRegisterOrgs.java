package com.rip.load.otherPojo.honeyportData;

import java.util.List;

public class UserRegisterOrgs {

    private String phone_num;

    /**
     * 用户注册信息情况（来源于聚信立千寻项目）
     */
    private String register_cnt;

    /**
     * 注册机构
     */
    private List<RegisterOrgs> register_orgs;


    /**
     *统计
     */
    private List<RegisterOrgsStatistics> register_orgs_statistics;

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getRegister_cnt() {
        return register_cnt;
    }

    public void setRegister_cnt(String register_cnt) {
        this.register_cnt = register_cnt;
    }

    public List<RegisterOrgs> getRegister_orgs() {
        return register_orgs;
    }

    public void setRegister_orgs(List<RegisterOrgs> register_orgs) {
        this.register_orgs = register_orgs;
    }

    public List<RegisterOrgsStatistics> getRegister_orgs_statistics() {
        return register_orgs_statistics;
    }

    public void setRegister_orgs_statistics(List<RegisterOrgsStatistics> register_orgs_statistics) {
        this.register_orgs_statistics = register_orgs_statistics;
    }
}
