package com.rip.load.otherPojo.honeyportData;

/**
 * 用这个手机号码绑定的其他身份证
 */
public class PhoneWithOtherIdcards {

    /**
     * 更新时间
     */
    private String susp_updt;
    /**
     *绑定的身份证(脱敏)
     */
    private String susp_idcard;

    public String getSusp_updt() {
        return susp_updt;
    }

    public void setSusp_updt(String susp_updt) {
        this.susp_updt = susp_updt;
    }

    public String getSusp_idcard() {
        return susp_idcard;
    }

    public void setSusp_idcard(String susp_idcard) {
        this.susp_idcard = susp_idcard;
    }
}
