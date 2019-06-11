package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-05-24
 */
@TableName("rip_repayplan_item")
public class RepayplanItem extends Model<RepayplanItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 还款计划表ID
     */
    @TableField("plan_id")
    private Integer planId;

    /**
     * 期次
     */
    private Integer time;

    /**
     * 金额类型PRI 本金
INT 利息  
ALL 本加息 
FEE 费用
     */
    private String type;

    /**
     * 本期需还金额
     */
    @TableField("plan_amount")
    private String planAmount;

    /**
     * 已还金额
     */
    @TableField("in_amount")
    private String inAmount;

    /**
     * 未还金额
     */
    @TableField("out_amount")
    private String outAmount;

    /**
     * 开始时间
     */
    @TableField("start_date")
    private String startDate;

    /**
     * 终止日期
     */
    @TableField("end_date")
    private String endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(String planAmount) {
        this.planAmount = planAmount;
    }
    public String getInAmount() {
        return inAmount;
    }

    public void setInAmount(String inAmount) {
        this.inAmount = inAmount;
    }
    public String getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(String outAmount) {
        this.outAmount = outAmount;
    }
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RepayplanItem{" +
        "id=" + id +
        ", planId=" + planId +
        ", time=" + time +
        ", type=" + type +
        ", planAmount=" + planAmount +
        ", inAmount=" + inAmount +
        ", outAmount=" + outAmount +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        "}";
    }
}
