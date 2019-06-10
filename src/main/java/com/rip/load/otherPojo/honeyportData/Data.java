package com.rip.load.otherPojo.honeyportData;

import java.util.List;

public class Data {

    /**
     * 灰度分数据
     */
    private UserGray user_gray;

    /**
     * 手机号码存疑
     */
    private UserPhoneSuspicion user_phone_suspicion;

    /**
     * 更新时间
     */
    private Long update_time;

    /**
     * 身份证存疑
     */
    private UserIdcardIuspicion user_idcard_suspicion;

    /**
     * 用户被机构查询历史
     */
    private List<UserSearchedHistoryByOrgs> user_searched_history_by_orgs;

    /**
     * 被机构查询数量（去重数据）
     */
    private UserSearchedStatistic user_searched_statistic;

    /**
     * 黑名单信息
     */
    private UserBlacklist user_blacklist;

    /**
     * 基本信息
     */
    private UserBasic user_basic;

    /**
     * 用户注册信息情况（来源于聚信立千寻项目）
     */
    private UserRegisterOrgs user_register_orgs;

    /**
     * 授权机构
     */
    private String auth_org;

    /**
     * 蜜罐编号
     */
    private String user_grid_id;

    public UserGray getUser_gray() {
        return user_gray;
    }

    public void setUser_gray(UserGray user_gray) {
        this.user_gray = user_gray;
    }

    public UserPhoneSuspicion getUser_phone_suspicion() {
        return user_phone_suspicion;
    }

    public void setUser_phone_suspicion(UserPhoneSuspicion user_phone_suspicion) {
        this.user_phone_suspicion = user_phone_suspicion;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    public UserIdcardIuspicion getUser_idcard_suspicion() {
        return user_idcard_suspicion;
    }

    public void setUser_idcard_suspicion(UserIdcardIuspicion user_idcard_suspicion) {
        this.user_idcard_suspicion = user_idcard_suspicion;
    }

    public List<UserSearchedHistoryByOrgs> getUser_searched_history_by_orgs() {
        return user_searched_history_by_orgs;
    }

    public void setUser_searched_history_by_orgs(List<UserSearchedHistoryByOrgs> user_searched_history_by_orgs) {
        this.user_searched_history_by_orgs = user_searched_history_by_orgs;
    }

    public UserSearchedStatistic getUser_searched_statistic() {
        return user_searched_statistic;
    }

    public void setUser_searched_statistic(UserSearchedStatistic user_searched_statistic) {
        this.user_searched_statistic = user_searched_statistic;
    }

    public UserBlacklist getUser_blacklist() {
        return user_blacklist;
    }

    public void setUser_blacklist(UserBlacklist user_blacklist) {
        this.user_blacklist = user_blacklist;
    }

    public UserBasic getUser_basic() {
        return user_basic;
    }

    public void setUser_basic(UserBasic user_basic) {
        this.user_basic = user_basic;
    }

    public UserRegisterOrgs getUser_register_orgs() {
        return user_register_orgs;
    }

    public void setUser_register_orgs(UserRegisterOrgs user_register_orgs) {
        this.user_register_orgs = user_register_orgs;
    }

    public String getAuth_org() {
        return auth_org;
    }

    public void setAuth_org(String auth_org) {
        this.auth_org = auth_org;
    }

    public String getUser_grid_id() {
        return user_grid_id;
    }

    public void setUser_grid_id(String user_grid_id) {
        this.user_grid_id = user_grid_id;
    }
}
