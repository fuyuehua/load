package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-04-22
 */
@TableName("rip_item")
public class Item extends Model<Item> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * json
     */
    @TableField("result_json")
    private String resultJson;

    /**
     * 创建时间
     */
    private Date time;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 征信信息接口类型
     * 1：身份证二要素
     * 2：运营商（手机号）实名三要素
     * 3：手机在网时长
     * 4：车辆详情核验
     * 5：企业工商数据查询
     * 6：个人名下关联企业
     * 7：公安不良信息
     * 8：综合风险查询
     * 9：个人涉诉报告高级版
     * 10：运营商报告
     * 11:银行卡四要素
     * 12:身份证拍照
     * 13:银行卡拍照
     * 14:运营商二要素
     */
    private String type;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OperatorReport{" +
        "id=" + id +
        ", resultJson=" + resultJson +
        ", time=" + time +
        ", userId=" + userId +
        "}";
    }
}
