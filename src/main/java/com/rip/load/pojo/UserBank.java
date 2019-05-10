package com.rip.load.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-04-09
 */
@TableName("rip_user_bank")
public class UserBank extends Model<UserBank> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 渠道商资金账户
     */
    private BigDecimal fund;

    /**
     * 渠道商预存手续费账户，平台收取手续费账户
     */
    private BigDecimal poundage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserBank{" +
        "id=" + id +
        ", fund=" + fund +
        ", poundage=" + poundage +
        "}";
    }
}
