package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-06-11
 */
@TableName("rip_loanlater")
public class Loanlater extends Model<Loanlater> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createtime;

    /**
     * 操作ID
     */
    @TableField("opertor_id")
    private Integer opertorId;

    /**
     * 操作
     */
    private String opertor;

    /**
     * 贷后处理详情
     */
    private String remark;

    /**
     * 对应订单
     */
    @TableField("order_id")
    private Integer orderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public Integer getOpertorId() {
        return opertorId;
    }

    public void setOpertorId(Integer opertorId) {
        this.opertorId = opertorId;
    }
    public String getOpertor() {
        return opertor;
    }

    public void setOpertor(String opertor) {
        this.opertor = opertor;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Loanlater{" +
        "id=" + id +
        ", createtime=" + createtime +
        ", opertorId=" + opertorId +
        ", opertor=" + opertor +
        ", remark=" + remark +
        "}";
    }
}
