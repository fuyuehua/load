package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-05-24
 */
@TableName("rip_repayplan")
public class Repayplan extends Model<Repayplan> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("order_id")
    private Integer orderId;

    /**
     * 还款方式
1-等额本息
2-等额本金
3-一次性还本付息
4-先息后本(即按频率付息、一次还本)
     */
    private Integer type;

    /**
     * PRI-本金
    INT-利息
    ODP-罚息
     */
    @TableField("money_type")
    private String moneyType;

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

    /**
     * 上一次还款日期
     */
    @TableField("last_deal_date")
    private String lastDealDate;

    /**
     * 下一期还款日期
     */
    @TableField("next_deal_date")
    private String nextDealDate;

    /**
     * 单次金额
     */
    private String amount;

    /**
     * 总金额
     */
    @TableField("total_amount")
    private String totalAmount;

    /**
     * 计划总次数
     */
    @TableField("total_times")
    private String totalTimes;

    /**
     * 还款日
     */
    @TableField("deal_day")
    private String dealDay;

    /**
     * 填 M
     */
    private String frequency;

    @TableField(exist = false)
    private List<RepayplanItem> repayplanItemList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
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
    public String getLastDealDate() {
        return lastDealDate;
    }

    public void setLastDealDate(String lastDealDate) {
        this.lastDealDate = lastDealDate;
    }
    public String getNextDealDate() {
        return nextDealDate;
    }

    public void setNextDealDate(String nextDealDate) {
        this.nextDealDate = nextDealDate;
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    public String getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(String totalTimes) {
        this.totalTimes = totalTimes;
    }
    public String getDealDay() {
        return dealDay;
    }

    public void setDealDay(String dealDay) {
        this.dealDay = dealDay;
    }
    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<RepayplanItem> getRepayplanItemList() {
        return repayplanItemList;
    }

    public void setRepayplanItemList(List<RepayplanItem> repayplanItemList) {
        this.repayplanItemList = repayplanItemList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Repayplan{" +
        "id=" + id +
        ", type=" + type +
        ", moneyType=" + moneyType +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", lastDealDate=" + lastDealDate +
        ", nextDealDate=" + nextDealDate +
        ", amount=" + amount +
        ", totalAmount=" + totalAmount +
        ", totalTimes=" + totalTimes +
        ", dealDay=" + dealDay +
        ", frequency=" + frequency +
        "}";
    }
}
