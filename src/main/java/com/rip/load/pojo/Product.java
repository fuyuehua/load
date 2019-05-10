package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
@TableName("rip_product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 产品名称
     */
    @NotBlank(message = "产品名称不能为空")
    private String name;

    /**
     * 贷款配置ID
     */
    @NotNull(message = "贷款配置ID不能为空")
    @TableField("config_id")
    private Integer configId;

    /**
     * 风控规则ID
     */
    @NotNull(message = "风控规则ID不能为空")
    @TableField("risk_id")
    private Integer riskId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 产品介绍
     */
    @NotBlank(message = "产品介绍不能为空")
    private String info;

    /**
     * 产品图片
     */
    @NotBlank(message = "产品图片不能为空")
    private String img;

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
    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }
    public Integer getRiskId() {
        return riskId;
    }

    public void setRiskId(Integer riskId) {
        this.riskId = riskId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Product{" +
        "id=" + id +
        ", name=" + name +
        ", configId=" + configId +
        ", riskId=" + riskId +
        ", userId=" + userId +
        ", info=" + info +
        ", img=" + img +
        "}";
    }
}
