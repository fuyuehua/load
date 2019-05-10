package com.rip.load.otherPojo.InTheNetworkTime;

public class Data {

    /**
     * 核对一致标识（0000：一致； 9998：不一致；3：无记录）
     */
    private String key;

    /**
     * 在网时长，(0,6)表示 0 到 6 个月，(24,+)表示 24 个月以上
     */
    private String OUTPUT1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOUTPUT1() {
        return OUTPUT1;
    }

    public void setOUTPUT1(String OUTPUT1) {
        this.OUTPUT1 = OUTPUT1;
    }
}
