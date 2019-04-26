package com.rip.load.pojo.businessData;

/**
 *高管信息
 */
public class ShareholderPersons {

    /**
     *姓名
     */
    private String name;
    /**
     *职位
     */
    private String position;
    /**
     *性别
     */
    private String sex;
    /**
     *总人数
     */
    private String personAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPersonAmount() {
        return personAmount;
    }

    public void setPersonAmount(String personAmount) {
        this.personAmount = personAmount;
    }
}
