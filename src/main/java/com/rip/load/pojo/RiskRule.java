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
 * @since 2019-05-09
 */
@TableName("rip_risk_rule")
public class RiskRule extends Model<RiskRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 风控规则表ID
     */
    @TableField("risk_id")
    private Integer riskId;

    /**
     * 风控规则ID
     */
    @TableField("rule_id")
    private Integer ruleId;

    /**
     * 参数1
     */
    private String paramA;

    /**
     * 参数2
     */
    private String paramB;

    /**
     * 参数3
     */
    private String paramC;

    /**
     * 参数4
     */
    private String paramD;

    /**
     * 参数5
     */
    private String paramE;

    /**
     * 通过：1：通过  0：未通过
     */
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRiskId() {
        return riskId;
    }

    public void setRiskId(Integer riskId) {
        this.riskId = riskId;
    }
    public String getParamA() {
        return paramA;
    }

    public void setParamA(String paramA) {
        this.paramA = paramA;
    }
    public String getParamB() {
        return paramB;
    }

    public void setParamB(String paramB) {
        this.paramB = paramB;
    }
    public String getParamC() {
        return paramC;
    }

    public void setParamC(String paramC) {
        this.paramC = paramC;
    }
    public String getParamD() {
        return paramD;
    }

    public void setParamD(String paramD) {
        this.paramD = paramD;
    }
    public String getParamE() {
        return paramE;
    }

    public void setParamE(String paramE) {
        this.paramE = paramE;
    }
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RiskRule{" +
        "id=" + id +
        ", riskId=" + riskId +
        ", paramA=" + paramA +
        ", paramB=" + paramB +
        ", paramC=" + paramC +
        ", paramD=" + paramD +
        ", paramE=" + paramE +
        ", flag=" + flag +
        "}";
    }
}
