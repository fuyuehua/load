package com.rip.load.pojo.operatorCreditReport;

/**
 * 基本信息
 */
public class BasicInfo {

    /**
     *姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String identityNo;
    /**
     * 性别
     */
    private String gender;
    /**
     * 年龄
     */
    private String age;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 入网时间
     */
    private String regTime;
    /**
     * 用户地址
     */
    private String nativeAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getNativeAddress() {
        return nativeAddress;
    }

    public void setNativeAddress(String nativeAddress) {
        this.nativeAddress = nativeAddress;
    }
}
