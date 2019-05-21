package com.rip.load.pojo;

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
 * @since 2019-04-16
 */
@TableName("rip_user_customer")
public class UserCustomer extends Model<UserCustomer> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull
    private Integer userId;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 银行卡号
     */
    private String bankcard;

    /**
     * 银行卡号信息
     */
    @TableField("bankcard_info")
    private String bankcardInfo;

    /**
     * 车牌号
     */
    @TableField("plate_number")
    private String plateNumber;

    /**
     * 工作单位
     */
    @TableField("work_unit")
    private String workUnit;

    /**
     * 工作地址
     */
    @TableField("work_address")
    private String workAddress;

    /**
     * 社保
     */
    @TableField("social_security")
    private String socialSecurity;
    /**
     * 紧急联系人A
     */
    @TableField("emergency_manA")
    private String emergencyMana;

    /**
     * 紧急联系人A姓名
     */
    @TableField("A_realname")
    private String aRealname;

    /**
     * 紧急联系人A手机号码
     */
    @TableField("A_phone")
    private String aPhone;

    /**
     * 紧急联系人A与当事人关系
     */
    @TableField("A_relation")
    private String aRelation;

    /**
     * 紧急联系人B
     */
    @TableField("emergency_manB")
    private String emergencyManb;

    /**
     * 紧急联系人B姓名
     */
    @TableField("B_realname")
    private String bRealname;

    /**
     * 紧急联系人B手机号码
     */
    @TableField("B_phone")
    private String bPhone;

    /**
     * 紧急联系人B与当事人关系
     */
    @TableField("B_relation")
    private String bRelation;

    /**
     * 父级用户ID
     */
    @TableField("father_id")
    private Integer fatherId;

    /**
     * 身份证正面照片
     */
    @TableField("idcard_photoA")
    private String idcardPhotoa;
    /**
     * 身份证反面照片
     */
    @TableField("idcard_photoB")
    private String idcardPhotob;
    /**
     * 人脸识别1
     */
    @TableField("face_photoA")
    private String facePhotoa;
    /**
     * 人脸识别2
     */
    @TableField("face_photoB")
    private String facePhotob;
    /**
     * 人脸识别3
     */
    @TableField("face_photoC")
    private String facePhotoC;
    /**
     * 银行卡号
     */
    @TableField("bankcard_photo")
    private String bankcardPhoto;

    /**
     * 身份证图片识别-住址
     */
    @TableField("idcard_address")
    private String idcardAddress;

    /**
     * 身份证图片识别-出生日期
     */
    @TableField("idcard_birthday")
    private String idcardBirthday;

    /**
     * 身份证图片识别-公民身份证号码
     */
    @TableField("idcard_idcard")
    private String idcardIdcard;

    /**
     * 身份证图片识别-姓名
     */
    @TableField("idcard_name")
    private String idcardName;

    /**
     * 身份证图片识别-性别
     */
    @TableField("idcard_sex")
    private String idcardSex;

    /**
     * 身份证图片识别-民族
     */
    @TableField("idcard_nation")
    private String idcardNation;

    /**
     * 身份证图片识别-签发日期
     */
    @TableField("idcard_sign")
    private String idcardSign;

    /**
     * 身份证图片识别-签发机关
     */
    @TableField("idcard_location")
    private String idcardLocation;

    /**
     * 身份证图片识别-失效日期
     */
    @TableField("idcard_expiration")
    private String idcardExpiration;

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


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }
    public String getEmergencyMana() {
        return emergencyMana;
    }

    public void setEmergencyMana(String emergencyMana) {
        this.emergencyMana = emergencyMana;
    }
    public String getaRealname() {
        return aRealname;
    }

    public void setaRealname(String aRealname) {
        this.aRealname = aRealname;
    }
    public String getaPhone() {
        return aPhone;
    }

    public void setaPhone(String aPhone) {
        this.aPhone = aPhone;
    }
    public String getaRelation() {
        return aRelation;
    }

    public void setaRelation(String aRelation) {
        this.aRelation = aRelation;
    }
    public String getEmergencyManb() {
        return emergencyManb;
    }

    public void setEmergencyManb(String emergencyManb) {
        this.emergencyManb = emergencyManb;
    }
    public String getbRealname() {
        return bRealname;
    }

    public void setbRealname(String bRealname) {
        this.bRealname = bRealname;
    }
    public String getbPhone() {
        return bPhone;
    }

    public void setbPhone(String bPhone) {
        this.bPhone = bPhone;
    }
    public String getbRelation() {
        return bRelation;
    }

    public void setbRelation(String bRelation) {
        this.bRelation = bRelation;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public String getBankcardInfo() {
        return bankcardInfo;
    }

    public void setBankcardInfo(String bankcardInfo) {
        this.bankcardInfo = bankcardInfo;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getIdcardPhotoa() {
        return idcardPhotoa;
    }

    public void setIdcardPhotoa(String idcardPhotoa) {
        this.idcardPhotoa = idcardPhotoa;
    }

    public String getIdcardPhotob() {
        return idcardPhotob;
    }

    public void setIdcardPhotob(String idcardPhotob) {
        this.idcardPhotob = idcardPhotob;
    }

    public String getFacePhotoa() {
        return facePhotoa;
    }

    public void setFacePhotoa(String facePhotoa) {
        this.facePhotoa = facePhotoa;
    }

    public String getFacePhotob() {
        return facePhotob;
    }

    public void setFacePhotob(String facePhotob) {
        this.facePhotob = facePhotob;
    }

    public String getFacePhotoC() {
        return facePhotoC;
    }

    public void setFacePhotoC(String facePhotoC) {
        this.facePhotoC = facePhotoC;
    }

    public String getBankcardPhoto() {
        return bankcardPhoto;
    }

    public void setBankcardPhoto(String bankcardPhoto) {
        this.bankcardPhoto = bankcardPhoto;
    }

    public String getIdcardAddress() {
        return idcardAddress;
    }

    public void setIdcardAddress(String idcardAddress) {
        this.idcardAddress = idcardAddress;
    }

    public String getIdcardBirthday() {
        return idcardBirthday;
    }

    public void setIdcardBirthday(String idcardBirthday) {
        this.idcardBirthday = idcardBirthday;
    }

    public String getIdcardIdcard() {
        return idcardIdcard;
    }

    public void setIdcardIdcard(String idcardIdcard) {
        this.idcardIdcard = idcardIdcard;
    }

    public String getIdcardName() {
        return idcardName;
    }

    public void setIdcardName(String idcardName) {
        this.idcardName = idcardName;
    }

    public String getIdcardSex() {
        return idcardSex;
    }

    public void setIdcardSex(String idcardSex) {
        this.idcardSex = idcardSex;
    }

    public String getIdcardNation() {
        return idcardNation;
    }

    public void setIdcardNation(String idcardNation) {
        this.idcardNation = idcardNation;
    }

    public String getIdcardSign() {
        return idcardSign;
    }

    public void setIdcardSign(String idcardSign) {
        this.idcardSign = idcardSign;
    }

    public String getIdcardLocation() {
        return idcardLocation;
    }

    public void setIdcardLocation(String idcardLocation) {
        this.idcardLocation = idcardLocation;
    }

    public String getIdcardExpiration() {
        return idcardExpiration;
    }

    public void setIdcardExpiration(String idcardExpiration) {
        this.idcardExpiration = idcardExpiration;
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

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "UserCustomer{" +
        "userId=" + userId +
        ", realname=" + realname +
        ", cellphone=" + cellphone +
        ", plateNumber=" + plateNumber +
        ", workUnit=" + workUnit +
        ", emergencyMana=" + emergencyMana +
        ", aRealname=" + aRealname +
        ", aPhone=" + aPhone +
        ", aRelation=" + aRelation +
        ", emergencyManb=" + emergencyManb +
        ", bRealname=" + bRealname +
        ", bPhone=" + bPhone +
        ", bRelation=" + bRelation +
        "}";
    }
}
