package com.rip.load.otherPojo.businessData;

/**
 *动产抵押-抵押人信息
 */
public class MortgagePersons {

    /**
     *抵押权人证照/证件号码
     */
    private String credentialNo;

    /**
     *抵押权人证照/证件类型
     */
    private String credentialType;

    /**
     *所在地
     */
    private String domain;
    /**
     *抵押权人名称
     */
    private String name;
    /**
     *登记编号
     */
    private String regNo;


    public String getCredentialNo() {
        return credentialNo;
    }

    public void setCredentialNo(String credentialNo) {
        this.credentialNo = credentialNo;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
}
