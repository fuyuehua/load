package com.rip.load.otherPojo.honeyportData;

import java.util.List;

public class UserPhoneSuspicion {
    /**
     *用这个手机号码绑定的其他身份证
     */
    private List<PhoneWithOtherIdcards> phone_with_other_idcards;

    /**
     *用这个手机号码绑定的其他姓名
     */
    private List<PhoneWithOtherNames> phone_with_other_names;

    /**
     *电话号码在那些类型的机构中使用过
     */
    private List<PhoneAppliedInOrgs> phone_applied_in_orgs;

    public List<PhoneWithOtherIdcards> getPhone_with_other_idcards() {
        return phone_with_other_idcards;
    }

    public void setPhone_with_other_idcards(List<PhoneWithOtherIdcards> phone_with_other_idcards) {
        this.phone_with_other_idcards = phone_with_other_idcards;
    }

    public List<PhoneWithOtherNames> getPhone_with_other_names() {
        return phone_with_other_names;
    }

    public void setPhone_with_other_names(List<PhoneWithOtherNames> phone_with_other_names) {
        this.phone_with_other_names = phone_with_other_names;
    }

    public List<PhoneAppliedInOrgs> getPhone_applied_in_orgs() {
        return phone_applied_in_orgs;
    }

    public void setPhone_applied_in_orgs(List<PhoneAppliedInOrgs> phone_applied_in_orgs) {
        this.phone_applied_in_orgs = phone_applied_in_orgs;
    }
}
