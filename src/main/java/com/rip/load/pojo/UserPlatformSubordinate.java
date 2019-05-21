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
 * @since 2019-05-18
 */
@TableName("rip_user_platform_subordinate")
public class UserPlatformSubordinate extends Model<UserPlatformSubordinate> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull
    @TableId("user_id")
    private Integer userId;

    /**
     * 父级用户ID
     */
    @NotNull
    @TableField("father_id")
    private Integer fatherId;

    /**
     * 真实姓名
     */
    @NotBlank
    @TableField("real_name")
    private String realName;

    /**
     * 职务，自定义的，比如：华东大区总监
     */
    private String job;

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
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "UserPlatformSubordinate{" +
        "userId=" + userId +
        ", fatherId=" + fatherId +
        ", realName=" + realName +
        ", job=" + job +
        "}";
    }
}
