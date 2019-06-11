package com.rip.load.pojo;

import java.math.BigDecimal;
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
 * @since 2019-05-29
 */
@TableName("rip_bank_fund")
public class BankFund extends Model<BankFund> {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private Integer userId;

    /**
     * 资金总额
     */
    private BigDecimal fund;

    /**
     * 手续费
     */
    private BigDecimal poundage;

    /**
     * 风控资金
     */
    @TableField("risk_fund")
    private BigDecimal riskFund;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }
    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }
    public BigDecimal getRiskFund() {
        return riskFund;
    }

    public void setRiskFund(BigDecimal riskFund) {
        this.riskFund = riskFund;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "BankFund{" +
        "userId=" + userId +
        ", fund=" + fund +
        ", poundage=" + poundage +
        ", riskFund=" + riskFund +
        "}";
    }
}
