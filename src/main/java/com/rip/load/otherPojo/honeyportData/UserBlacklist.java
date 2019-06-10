package com.rip.load.otherPojo.honeyportData;

import java.util.List;

public class UserBlacklist {

    /**
     * 身份证和姓名是否在黑名单
     */
    private String blacklist_name_with_idcard;

    /**
     * 更新时间
     */
    private String blacklist_update_time_name_idcard;

    /**
     * 手机号和姓名是否在黑名单
     */
    private String blacklist_name_with_phone;

    /**
     * 更新时间
     */
    private String blacklist_update_time_name_phone;

    /**
     * 黑名单类型
     */
    private List<String> blacklist_category;

    /**
     * 细节
     */
    private List<BlacklistDetails> blacklist_details;

    public String getBlacklist_name_with_idcard() {
        return blacklist_name_with_idcard;
    }

    public void setBlacklist_name_with_idcard(String blacklist_name_with_idcard) {
        this.blacklist_name_with_idcard = blacklist_name_with_idcard;
    }

    public String getBlacklist_update_time_name_idcard() {
        return blacklist_update_time_name_idcard;
    }

    public void setBlacklist_update_time_name_idcard(String blacklist_update_time_name_idcard) {
        this.blacklist_update_time_name_idcard = blacklist_update_time_name_idcard;
    }

    public String getBlacklist_name_with_phone() {
        return blacklist_name_with_phone;
    }

    public void setBlacklist_name_with_phone(String blacklist_name_with_phone) {
        this.blacklist_name_with_phone = blacklist_name_with_phone;
    }

    public String getBlacklist_update_time_name_phone() {
        return blacklist_update_time_name_phone;
    }

    public void setBlacklist_update_time_name_phone(String blacklist_update_time_name_phone) {
        this.blacklist_update_time_name_phone = blacklist_update_time_name_phone;
    }

    public List<String> getBlacklist_category() {
        return blacklist_category;
    }

    public void setBlacklist_category(List<String> blacklist_category) {
        this.blacklist_category = blacklist_category;
    }

    public List<BlacklistDetails> getBlacklist_details() {
        return blacklist_details;
    }

    public void setBlacklist_details(List<BlacklistDetails> blacklist_details) {
        this.blacklist_details = blacklist_details;
    }
}
