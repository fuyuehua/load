package com.rip.load.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 产品的利率配置实体类
 * </p>
 *
 * @author zxh
 * @since 2019-04-02
 */
@TableName("rip_config")
public class Config extends Model<Config> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    @NotBlank(message = "fuck")
    private String name;

    /**
     * 虚拟金额，用户未完成资料时看到的额度
     */
    @TableField("invented_money")
    private String inventedMoney;

    /**
     * 借款额度，用户实际申请的额度
     */
    @TableField("load_limit")
    private String loadLimit;

    /**
     * 履约期限，借款期限
     */
    @NotBlank
    private String limit;

    /**
     * 履约期限单位：年Y、月M、日D
     */
    @NotBlank
    @TableField("limit_type")
    private String limitType;

    /**
     * 1-等额本息
     * 2-等额本金
     * 3-一次性还本付息
     * 4-先息后本
     */
    @NotNull
    @TableField("repay_type")
    private Integer repayType;

    /**
     * 利率 单位：% 默认月利率 如果履约期限单位是日，那就是日利率
     */
    @NotBlank
    private String interest;

    /**
     * 续期手续费 单位：%，用户续期需要支付的手续费
     */
    @NotBlank
    @TableField("renewal_money")
    private String renewalMoney;

    /**
     * 逾期费率 单位：%，逾期日开始每日按照借款金额(非应还金额)百分比加收逾期费用
     */
    @NotBlank
    @TableField("overdue_money")
    private String overdueMoney;

    /**
     * 每日注册名额限制
     */
    @TableField("register_limit")
    private String registerLimit;

    /**
     * 满额限制填资料,每日名额满后是否允许新用户填写资料，开启则不允许
     */
    @TableField("register_limit_onoff")
    private Integer registerLimitOnoff;

    /**
     * 前期费用,单位元
     */
    @TableField("prophase_money")
    private String prophaseMoney;

    /**
     * 前期费收取,开：1，关：0
     */
    @TableField("prophase_money_onoff")
    private Integer prophaseMoneyOnoff;

    /**
     * 前期费收取方式
     */
    @TableField("prophase_money_type")
    private Integer prophaseMoneyType;

    /**
     * 驳回等待天数,单位：天，如设置60，则标识被拒绝的用户60天后才能再次申请。
     */
    @TableField("reject_wait")
    private String rejectWait;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInventedMoney() {
        return inventedMoney;
    }

    public void setInventedMoney(String inventedMoney) {
        this.inventedMoney = inventedMoney;
    }
    public String getLoadLimit() {
        return loadLimit;
    }

    public void setLoadLimit(String loadLimit) {
        this.loadLimit = loadLimit;
    }

    public String getRenewalMoney() {
        return renewalMoney;
    }

    public void setRenewalMoney(String renewalMoney) {
        this.renewalMoney = renewalMoney;
    }
    public String getOverdueMoney() {
        return overdueMoney;
    }

    public void setOverdueMoney(String overdueMoney) {
        this.overdueMoney = overdueMoney;
    }
    public String getRegisterLimit() {
        return registerLimit;
    }

    public void setRegisterLimit(String registerLimit) {
        this.registerLimit = registerLimit;
    }
    public Integer getRegisterLimitOnoff() {
        return registerLimitOnoff;
    }

    public void setRegisterLimitOnoff(Integer registerLimitOnoff) {
        this.registerLimitOnoff = registerLimitOnoff;
    }
    public String getProphaseMoney() {
        return prophaseMoney;
    }

    public void setProphaseMoney(String prophaseMoney) {
        this.prophaseMoney = prophaseMoney;
    }
    public Integer getProphaseMoneyOnoff() {
        return prophaseMoneyOnoff;
    }

    public void setProphaseMoneyOnoff(Integer prophaseMoneyOnoff) {
        this.prophaseMoneyOnoff = prophaseMoneyOnoff;
    }
    public Integer getProphaseMoneyType() {
        return prophaseMoneyType;
    }

    public void setProphaseMoneyType(Integer prophaseMoneyType) {
        this.prophaseMoneyType = prophaseMoneyType;
    }
    public String getRejectWait() {
        return rejectWait;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public Integer getRepayType() {
        return repayType;
    }

    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setRejectWait(String rejectWait) {
        this.rejectWait = rejectWait;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", inventedMoney='" + inventedMoney + '\'' +
                ", loadLimit='" + loadLimit + '\'' +
                ", limit='" + limit + '\'' +
                ", limitType='" + limitType + '\'' +
                ", repayType=" + repayType +
                ", interest='" + interest + '\'' +
                ", renewalMoney='" + renewalMoney + '\'' +
                ", overdueMoney='" + overdueMoney + '\'' +
                ", registerLimit='" + registerLimit + '\'' +
                ", registerLimitOnoff=" + registerLimitOnoff +
                ", prophaseMoney='" + prophaseMoney + '\'' +
                ", prophaseMoneyOnoff=" + prophaseMoneyOnoff +
                ", prophaseMoneyType=" + prophaseMoneyType +
                ", rejectWait='" + rejectWait + '\'' +
                '}';
    }
}
