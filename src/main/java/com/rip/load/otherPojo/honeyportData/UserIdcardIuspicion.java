package com.rip.load.otherPojo.honeyportData;

import java.util.List;

/**
 * 身份证存疑
 */
public class UserIdcardIuspicion {

    /**
     *用这个身份证号码绑定的其他姓名
     */
    private List<IdcardWithOtherNames> idcard_with_other_names;
    /**
     *用这个身份证绑定的其他手机号码
     */
    private List<IdcardWithOtherPhones> idcard_with_other_phones;
    /**
     *身份证在那些类型的机构中使用过
     */
    private List<IdcardAppliedInOrgs> idcard_applied_in_orgs;

    public List<IdcardWithOtherNames> getIdcard_with_other_names() {
        return idcard_with_other_names;
    }

    public void setIdcard_with_other_names(List<IdcardWithOtherNames> idcard_with_other_names) {
        this.idcard_with_other_names = idcard_with_other_names;
    }

    public List<IdcardWithOtherPhones> getIdcard_with_other_phones() {
        return idcard_with_other_phones;
    }

    public void setIdcard_with_other_phones(List<IdcardWithOtherPhones> idcard_with_other_phones) {
        this.idcard_with_other_phones = idcard_with_other_phones;
    }

    public List<IdcardAppliedInOrgs> getIdcard_applied_in_orgs() {
        return idcard_applied_in_orgs;
    }

    public void setIdcard_applied_in_orgs(List<IdcardAppliedInOrgs> idcard_applied_in_orgs) {
        this.idcard_applied_in_orgs = idcard_applied_in_orgs;
    }
}
