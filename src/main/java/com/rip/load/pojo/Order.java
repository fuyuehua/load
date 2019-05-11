package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
    @TableField("product_id")
    private BigDecimal productId;

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
     * 驳回原因
     */
    @TableField("reject_reason")
    private String rejectReason;

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
    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public BigDecimal getProductId() {
        return productId;
    }

    public void setProductId(BigDecimal productId) {
        this.productId = productId;
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
        ", rejectReason=" + rejectReason +
        "}";
    }
}
