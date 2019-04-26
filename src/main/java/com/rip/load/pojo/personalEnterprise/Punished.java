package com.rip.load.pojo.personalEnterprise;

/**
 *被执行人信息
 */
public class Punished {

    /**
     *案号
     */
    private String caseCode;

    /**
     * 案件状态
     */
    private String caseState;

    /**
     *被执行人姓名/名称
     */
    private String name;

    /**
     *身份证号码
     */
    private String cardNum;

    /**
     * 性别
     */
    private String sex;

    /**
     *年龄
     */
    private String age;

    /**
     *省份
     */
    private String areaName;

    /**
     *身份证原始发证地
     */
    private String ysfzd;

    /**
     *执行法院
     */
    private String courtName;

    /**
     *立案时间
     */
    private String regDate;

    /**
     *执行标的（元）
     */
    private String execMoney;

    /**
     *关注次数
     */
    private String focusNum;

    /**
     *失信人类型
     */
    private String type;

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getCaseState() {
        return caseState;
    }

    public void setCaseState(String caseState) {
        this.caseState = caseState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getYsfzd() {
        return ysfzd;
    }

    public void setYsfzd(String ysfzd) {
        this.ysfzd = ysfzd;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getExecMoney() {
        return execMoney;
    }

    public void setExecMoney(String execMoney) {
        this.execMoney = execMoney;
    }

    public String getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(String focusNum) {
        this.focusNum = focusNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
