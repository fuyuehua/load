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
 * @since 2019-05-29
 */
@TableName("rip_bank_record")
public class BankRecord extends Model<BankRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 交易金额
     */
    private BigDecimal money;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 付款人名称
     * 见鬼了，如果直接用drawee，一直报错，找不到原因的报错
     */
    @TableField("drawee_")
    private String drawee;

    /**
     * 付款人ID
     */
    @TableField("drawee_id")
    private Integer draweeId;

    /**
     * 收款人名称
     */
    private String payee;

    /**
     * 收款人ID
     */
    @TableField("payee_id")
    private Integer payeeId;

    /**
     * 操作者
     */
    private String operator;

    /**
     * 操作者ID
     */
    @TableField("operator_id")
    private Integer operatorId;

    /**
     * 资金流水记录类型
1：银行打款 
2：客户还款 
3：向银行充值
4：手续费扣除
5：手续费划拨
6：风控资金扣除
7：风控资金充值
     */
    private Integer type;

    /**
     * 客户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 订单ID
     */
    @TableField("order_id")
    private Integer orderId;

    /**
     * 手续费比例 %
     */
    @TableField("poundage_rate")
    private Integer poundageRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDrawee() {
        return drawee;
    }

    public void setDrawee(String drawee) {
        this.drawee = drawee;
    }

    public Integer getDraweeId() {
        return draweeId;
    }

    public void setDraweeId(Integer draweeId) {
        this.draweeId = draweeId;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Integer getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Integer payeeId) {
        this.payeeId = payeeId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPoundageRate() {
        return poundageRate;
    }

    public void setPoundageRate(Integer poundageRate) {
        this.poundageRate = poundageRate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
