package com.rip.load.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-04-09
 */
@TableName("rip_user_distributor")
public class UserDistributor extends Model<UserDistributor> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull
    @TableId("user_id")
    private Integer userId;

    /**
     * 渠道商公司名称
     */
    @NotBlank
    @TableField("corporate_name")
    private String corporateName;

    /**
     * 渠道商公司地址
     */
    @NotBlank
    @TableField("corporate_location")
    private String corporateLocation;

    /**
     * 渠道商公司联系电话
     */
    @NotBlank
    @TableField("corporate_phone")
    private String corporatePhone;

    /**
     * 父级用户ID
     */
    @TableField("father_id")
    private Integer fatherId;

    /**
     * 手续费抽成额度
     */
    @NotNull
    private Integer poundage;

    /**
     * 绑定的征信秘钥账户
     */
    private String username;

    /**
     * 绑定的征信秘钥密码
     */
    @TableField("access_token")
    private String accessToken;

    @TableField("province_id")
    private Integer provinceId;

    @TableField("city_id")
    private Integer cityId;

    @TableField(value = "area_id")
    private Integer areaId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }
    public String getCorporateLocation() {
        return corporateLocation;
    }

    public void setCorporateLocation(String corporateLocation) {
        this.corporateLocation = corporateLocation;
    }
    public String getCorporatePhone() {
        return corporatePhone;
    }

    public void setCorporatePhone(String corporatePhone) {
        this.corporatePhone = corporatePhone;
    }
    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }
    public Integer getPoundage() {
        return poundage;
    }

    public void setPoundage(Integer poundage) {
        this.poundage = poundage;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "UserDistributor{" +
                "userId=" + userId +
                ", corporateName='" + corporateName + '\'' +
                ", corporateLocation='" + corporateLocation + '\'' +
                ", corporatePhone='" + corporatePhone + '\'' +
                ", fatherId=" + fatherId +
                ", poundage=" + poundage +
                ", username='" + username + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
