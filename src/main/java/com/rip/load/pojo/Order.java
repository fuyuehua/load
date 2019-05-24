package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
@TableName("rip_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @NotNull
    private Integer uid;


    /**
     * 姓名
     */
    private String realname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 借款产品ID
     */
    @NotNull
    @TableField("product_id")
    private Integer productId;

    /**
     * 借款金额
     */
    @TableField("borrow_money")
    private BigDecimal borrowMoney;

    /**
     * 到账金额
     */
    @TableField("arrival_money")
    private BigDecimal arrivalMoney;

    /**
     * 期限
     */
    @TableField("time_limit")
    private Integer timeLimit;

    /**
     * 状态
     * 状态 1：初审通过，2初审资料待完善，3初审不通过，4复审通过，5复审不通过
     */
    private Integer status;

    /**
     * 申请时间
     */
    @TableField("application_time")
    private Date applicationTime;

    /**
     * 到款时间
     */
    @TableField("arrival_time")
    private Date arrivalTime;

    /**
     * 到期时间
     */
    @TableField("due_time")
    private Date dueTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 费用明细ID
     */
    @TableField("expense_detailId")
    private Integer expenseDetailid;

    /**
     * 合同ID
     */
    private Integer contractId;

    /**
     * 还款计划表ID
     */
    @TableField("repay_plan_id")
    private Integer repayPlanId;

    /**
     * 初审人员
     */
    @TableField("first_reject_man")
    private Integer firstRejectMan;

    /**
     * 初审驳回原因
     */
    @TableField("first_reject_reason")
    private String firstRejectReason;

    /**
     * 复审人员
     */
    @TableField("second_reject_man")
    private Integer secondRejectMan;

    /**
     * 复审驳回原因
     */
    @TableField("second_reject_reason")
    private String secondRejectReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public BigDecimal getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(BigDecimal borrowMoney) {
        this.borrowMoney = borrowMoney;
    }
    public BigDecimal getArrivalMoney() {
        return arrivalMoney;
    }

    public void setArrivalMoney(BigDecimal arrivalMoney) {
        this.arrivalMoney = arrivalMoney;
    }
    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getExpenseDetailid() {
        return expenseDetailid;
    }

    public void setExpenseDetailid(Integer expenseDetailid) {
        this.expenseDetailid = expenseDetailid;
    }
    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRepayPlanId() {
        return repayPlanId;
    }

    public void setRepayPlanId(Integer repayPlanId) {
        this.repayPlanId = repayPlanId;
    }

    public Integer getFirstRejectMan() {
        return firstRejectMan;
    }

    public void setFirstRejectMan(Integer firstRejectMan) {
        this.firstRejectMan = firstRejectMan;
    }

    public Integer getSecondRejectMan() {
        return secondRejectMan;
    }

    public void setSecondRejectMan(Integer secondRejectMan) {
        this.secondRejectMan = secondRejectMan;
    }

    public String getFirstRejectReason() {
        return firstRejectReason;
    }

    public void setFirstRejectReason(String firstRejectReason) {
        this.firstRejectReason = firstRejectReason;
    }

    public String getSecondRejectReason() {
        return secondRejectReason;
    }

    public void setSecondRejectReason(String secondRejectReason) {
        this.secondRejectReason = secondRejectReason;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Order{" +
        "id=" + id +
        ", realname=" + realname +
        ", phone=" + phone +
        ", borrowMoney=" + borrowMoney +
        ", arrivalMoney=" + arrivalMoney +
        ", timeLimit=" + timeLimit +
        ", status=" + status +
        ", applicationTime=" + applicationTime +
        ", dueTime=" + dueTime +
        ", remark=" + remark +
        ", expenseDetailid=" + expenseDetailid +
        ", contractId=" + contractId +
        "}";
    }
}
