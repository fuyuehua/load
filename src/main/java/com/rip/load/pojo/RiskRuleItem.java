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
 * @since 2019-05-21
 */
@TableName("rip_risk_rule_item")
public class RiskRuleItem extends Model<RiskRuleItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 表：报告小项与风控规则表小项连接
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 风控规则小项目
     */
    @TableField("risk_rule_id")
    private Integer riskRuleId;

    /**
     * 报告项目
     */
    @TableField("item_id")
    private Integer itemId;

    /**
     * 通过：1：通过  0：未通过
     */
    private Integer flag;

    @TableField(exist = false)
    private Item item;
    @TableField(exist = false)
    private RiskRule riskRule;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public RiskRule getRiskRule() {
        return riskRule;
    }

    public void setRiskRule(RiskRule riskRule) {
        this.riskRule = riskRule;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRiskRuleId() {
        return riskRuleId;
    }

    public void setRiskRuleId(Integer riskRuleId) {
        this.riskRuleId = riskRuleId;
    }
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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
        return "RiskRuleItem{" +
        "id=" + id +
        ", riskRuleId=" + riskRuleId +
        ", itemId=" + itemId +
        ", flag=" + flag +
        "}";
    }
}
