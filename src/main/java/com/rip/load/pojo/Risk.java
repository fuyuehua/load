package com.rip.load.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
@TableName("rip_risk")
public class Risk extends Model<Risk> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 风控规则名字
     */
    @NotBlank
    private String name;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    private String remark;

    @TableField(exist=false)
    private List<RiskRule> riskRuleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRemark() {

        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<RiskRule> getRiskRuleList() {
        return riskRuleList;
    }

    public void setRiskRuleList(List<RiskRule> riskRuleList) {
        this.riskRuleList = riskRuleList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Risk{" +
        "id=" + id +
        ", name=" + name +
        "}";
    }
}
