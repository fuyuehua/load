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
 * @since 2019-05-09
 */
@TableName("rip_user_customer_bankcard")
public class UserCustomerBankcard extends Model<UserCustomerBankcard> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 绑定的用户ID
     */
    private Integer userId;

    /**
     * 银行卡号
     */
    private String bankcard;

    /**
     * 是否为主卡
     */
    @TableField("main_card_flag")
    private Integer mainCardFlag;

    /**
     * 银行卡信息
     */
    @TableField("bankcard_info")
    private String bankcardInfo;

    /**
     * 银行卡照片
     */
    @TableField("bankcard_photo")
    private String bankcardPhoto;

    /**
     * 银行卡图片识别-卡号
     */
    @TableField("bankcard_cardnumber")
    private String bankcardCardnumber;

    /**
     * 银行卡图片识别-卡类型
     */
    @TableField("bankcard_cardtype")
    private String bankcardCardtype;

    /**
     * 银行卡图片识别-发卡银行
     */
    @TableField("bankcard_cardname")
    private String bankcardCardname;

    /**
     * 绑定状态
     */
    private String status;

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
    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }
    public Integer getMainCardFlag() {
        return mainCardFlag;
    }

    public void setMainCardFlag(Integer mainCardFlag) {
        this.mainCardFlag = mainCardFlag;
    }
    public String getBankcardInfo() {
        return bankcardInfo;
    }

    public void setBankcardInfo(String bankcardInfo) {
        this.bankcardInfo = bankcardInfo;
    }
    public String getBankcardPhoto() {
        return bankcardPhoto;
    }

    public void setBankcardPhoto(String bankcardPhoto) {
        this.bankcardPhoto = bankcardPhoto;
    }
    public String getBankcardCardnumber() {
        return bankcardCardnumber;
    }

    public void setBankcardCardnumber(String bankcardCardnumber) {
        this.bankcardCardnumber = bankcardCardnumber;
    }
    public String getBankcardCardtype() {
        return bankcardCardtype;
    }

    public void setBankcardCardtype(String bankcardCardtype) {
        this.bankcardCardtype = bankcardCardtype;
    }
    public String getBankcardCardname() {
        return bankcardCardname;
    }

    public void setBankcardCardname(String bankcardCardname) {
        this.bankcardCardname = bankcardCardname;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserCustomerBankcard{" +
        "id=" + id +
        ", userId=" + userId +
        ", bankcard=" + bankcard +
        ", mainCardFlag=" + mainCardFlag +
        ", bankcardInfo=" + bankcardInfo +
        ", bankcardPhoto=" + bankcardPhoto +
        ", bankcardCardnumber=" + bankcardCardnumber +
        ", bankcardCardtype=" + bankcardCardtype +
        ", bankcardCardname=" + bankcardCardname +
        ", status=" + status +
        "}";
    }

}
