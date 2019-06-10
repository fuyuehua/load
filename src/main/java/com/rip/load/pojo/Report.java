package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-04-30
 */
@TableName("rip_report")
public class Report extends Model<Report> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 备注
     */
    private String remark;

    @TableField("order_id")
    private Integer orderId;

    /**
     * 1:第一次完整报告 2：后来的弱授权报告
     */
    private Integer type;

    @TableField("base_url")
    private String baseUrl;

    @TableField("operator_url")
    private String operatorUrl;

    @TableField("taobao_url")
    private String taobaoUrl;

    @TableField(exist = false)
    private List<RiskRuleItem> riskRuleItems;

    @TableField(exist = false)
    private List<Item> itemList;

    @TableField(exist = false)
    private UserCustomer userCustomer;

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
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<RiskRuleItem> getRiskRuleItems() {
        return riskRuleItems;
    }

    public void setRiskRuleItems(List<RiskRuleItem> riskRuleItems) {
        this.riskRuleItems = riskRuleItems;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public UserCustomer getUserCustomer() {
        return userCustomer;
    }

    public void setUserCustomer(UserCustomer userCustomer) {
        this.userCustomer = userCustomer;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getOperatorUrl() {
        return operatorUrl;
    }

    public void setOperatorUrl(String operatorUrl) {
        this.operatorUrl = operatorUrl;
    }

    public String getTaobaoUrl() {
        return taobaoUrl;
    }

    public void setTaobaoUrl(String taobaoUrl) {
        this.taobaoUrl = taobaoUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Report{" +
        "id=" + id +
        ", userId=" + userId +
        ", createtime=" + createtime +
        ", remark=" + remark +
        "}";
    }
}
