package com.rip.load.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-05-10
 */
@TableName("rip_rule")
public class Rule extends Model<Rule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 绑定的平台商ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 单个规则名字
     */
    private String name;

    /**
     * 单个规则信息
     */
    private String info;

    /**
     * 参数1
     */
    private String paramA;

    /**
     * 参数2
     */
    private String paramB;

    /**
     * 参数3
     */
    private String paramC;

    /**
     * 参数4
     */
    private String paramD;

    /**
     * 参数5
     */
    private String paramE;

    /**
     * 使用的方法名称
     */
    private String method;

    private Integer onOff = 1;

    /**
     * 分数
     */
    private String grade;

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
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getParamA() {
        return paramA;
    }

    public void setParamA(String paramA) {
        this.paramA = paramA;
    }
    public String getParamB() {
        return paramB;
    }

    public void setParamB(String paramB) {
        this.paramB = paramB;
    }
    public String getParamC() {
        return paramC;
    }

    public void setParamC(String paramC) {
        this.paramC = paramC;
    }
    public String getParamD() {
        return paramD;
    }

    public void setParamD(String paramD) {
        this.paramD = paramD;
    }
    public String getParamE() {
        return paramE;
    }

    public void setParamE(String paramE) {
        this.paramE = paramE;
    }
    public String getMethod() {
        return method;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Rule{" +
        "id=" + id +
        ", name=" + name +
        ", info=" + info +
        ", paramA=" + paramA +
        ", paramB=" + paramB +
        ", paramC=" + paramC +
        ", paramD=" + paramD +
        ", paramE=" + paramE +
        ", method=" + method +
        "}";
    }
}
