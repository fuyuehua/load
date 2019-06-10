package com.rip.load.otherPojo.honeyportData;

import com.alibaba.fastjson.JSON;

public class UserGray {

    /**
     * 手机号
     */
    private String user_phone;

    /**
     * 直接联系人在黑名单的数量
     */
    private String contacts_class1_blacklist_cnt;

    /**
     * 间接联系人在黑名单的数量
     */
    private String contacts_class2_blacklist_cnt;

    /**
     * 灰度分
     */
    private String phone_gray_score;

    /**
     * 一阶联系人总数
     */
    private String contacts_class1_cnt;

    /**
     * 引起二阶黑名单人数
     */
    private String contacts_router_cnt;

    /**
     * 引起占比=引起二阶黑名单人数/一阶联系人总数
     */
    private String contacts_router_ratio;

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getContacts_class1_blacklist_cnt() {
        return contacts_class1_blacklist_cnt;
    }

    public void setContacts_class1_blacklist_cnt(String contacts_class1_blacklist_cnt) {
        this.contacts_class1_blacklist_cnt = contacts_class1_blacklist_cnt;
    }

    public String getContacts_class2_blacklist_cnt() {
        return contacts_class2_blacklist_cnt;
    }

    public void setContacts_class2_blacklist_cnt(String contacts_class2_blacklist_cnt) {
        this.contacts_class2_blacklist_cnt = contacts_class2_blacklist_cnt;
    }

    public String getPhone_gray_score() {
        return phone_gray_score;
    }

    public void setPhone_gray_score(String phone_gray_score) {
        this.phone_gray_score = phone_gray_score;
    }

    public String getContacts_class1_cnt() {
        return contacts_class1_cnt;
    }

    public void setContacts_class1_cnt(String contacts_class1_cnt) {
        this.contacts_class1_cnt = contacts_class1_cnt;
    }

    public String getContacts_router_cnt() {
        return contacts_router_cnt;
    }

    public void setContacts_router_cnt(String contacts_router_cnt) {
        this.contacts_router_cnt = contacts_router_cnt;
    }

    public String getContacts_router_ratio() {
        return contacts_router_ratio;
    }

    public void setContacts_router_ratio(String contacts_router_ratio) {
        this.contacts_router_ratio = contacts_router_ratio;
    }

    @Override
    public String toString() {
        return "UserGray{" +
                "user_phone='" + user_phone + '\'' +
                ", contacts_class1_blacklist_cnt='" + contacts_class1_blacklist_cnt + '\'' +
                ", contacts_class2_blacklist_cnt='" + contacts_class2_blacklist_cnt + '\'' +
                ", phone_gray_score='" + phone_gray_score + '\'' +
                ", contacts_class1_cnt='" + contacts_class1_cnt + '\'' +
                ", contacts_router_cnt='" + contacts_router_cnt + '\'' +
                ", contacts_router_ratio='" + contacts_router_ratio + '\'' +
                '}';
    }

}
