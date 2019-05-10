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
@TableName("rip_user_distributor_subordinate")
public class UserDistributorSubordinate extends Model<UserDistributorSubordinate> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("user_id")
    private Integer userId;

    /**
     * 父级用户ID
     */
    @TableField("father_id")
    private Integer fatherId;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "UserDistributorSubordinate{" +
        "userId=" + userId +
        ", fatherId=" + fatherId +
        ", realName=" + realName +
        "}";
    }
}
