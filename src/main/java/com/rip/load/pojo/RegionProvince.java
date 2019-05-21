package com.rip.load.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-05-18
 */
@TableName("rip_region_province")
public class RegionProvince extends Model<RegionProvince> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 城市代码
     */
    private String code;

    private String name;

//    /**
//     * 管理人员ID
//     */
//    @TableField("user_id")
//    private Integer userId;

    @TableField(exist = false)
    private List<RegionCity> cityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RegionCity> getCityList() {
        return cityList;
    }

    public void setCityList(List<RegionCity> cityList) {
        this.cityList = cityList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RegionProvince{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        "}";
    }
}
