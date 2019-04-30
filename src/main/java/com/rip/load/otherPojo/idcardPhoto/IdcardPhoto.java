package com.rip.load.otherPojo.idcardPhoto;

import java.util.List;

public class IdcardPhoto {

    /**
     *唯一的log id，用于问题定位
     */
    private String log_id;

    /**
     *图像方向，当detect_direction=true时存在。
     * - -1:未定义，
     * - 0:正向，
     * - 1: 逆时针90度，
     * - 2:逆时针180度，
     * - 3:逆时针270度
     */
    private String direction;

    /**
     *normal-识别正常
     * reversed_side-身份证正反面颠倒
     * non_idcard-上传的图片中不包含身份证
     * blurred-身份证模糊
     * other_type_card-其他类型证照
     * over_exposure-身份证关键字段反光或过曝
     * over_dark-身份证欠曝（亮度过低）
     * unknown-未知状态
     */
    private String image_status;

    /**
     *
     */
    private String idcard_type;

    /**
     *如果参数 detect_risk = true 时，
     * 则返回此字段。如果检测身份证被编辑过，
     * 该字段指定编辑软件名称，
     * 如:Adobe Photoshop CC 2014 (Macintosh),如果没有被编辑过则返回值无此参数
     */
    private String edit_tool;

    /**
     *定位和识别结果数组
     */
    private Words_result words_result;

    /**
     *识别结果数，表示words_result的元素个数
     */
    private String words_result_num;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getImage_status() {
        return image_status;
    }

    public void setImage_status(String image_status) {
        this.image_status = image_status;
    }

    public String getIdcard_type() {
        return idcard_type;
    }

    public void setIdcard_type(String idcard_type) {
        this.idcard_type = idcard_type;
    }

    public String getEdit_tool() {
        return edit_tool;
    }

    public void setEdit_tool(String edit_tool) {
        this.edit_tool = edit_tool;
    }

    public Words_result getWords_result() {

        return words_result;
    }

    public void setWords_result(Words_result words_result) {
        this.words_result = words_result;
    }

    public String getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(String words_result_num) {
        this.words_result_num = words_result_num;
    }
}
