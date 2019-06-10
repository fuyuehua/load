package com.rip.load.otherPojo.honeyportData;

public class Result {

    private Boolean success;

    private String message;

    private Data data;

    /**
     * 流程码
     */
    private Integer process_code;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getProcess_code() {
        return process_code;
    }

    public void setProcess_code(Integer process_code) {
        this.process_code = process_code;
    }
}
