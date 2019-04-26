package com.rip.load.pojo.operatorThreeElements;

public class Data {

    /**
     * 核对一致标识（0000：一致； 9998：不一致；3：无记录）
     */
    private String key;
    /**
     * 移动，联通，电信，未知
     */
    private String isp;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
}
