package com.rip.load.pojo;

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
 * @since 2019-04-09
 */
@TableName("rip_user_platform")
public class UserPlatform extends Model<UserPlatform> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("user_id")
    private Integer userId;

    /**
     * 平台公司名称
     */
    @TableField("corporate_name")
    private String corporateName;

    /**
     * 平台公司地址
     */
    @TableField("corporate_location")
    private String corporateLocation;

    /**
     * 平台公司联系电话
     */
    @TableField("corporate_phone")
    private String corporatePhone;

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

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "UserPlatform{" +
        "userId=" + userId +
        ", corporateName=" + corporateName +
        ", corporateLocation=" + corporateLocation +
        ", corporatePhone=" + corporatePhone +
        "}";
    }
}
