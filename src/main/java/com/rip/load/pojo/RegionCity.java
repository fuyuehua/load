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
@TableName("rip_region_city")
public class RegionCity extends Model<RegionCity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 城市代码
     */
    private String code;

    private String name;

    /**
     * 上级省的ID
     */
    @TableField("super_id")
    private Integer superId;

//    /**
//     * 管理人员ID
//     */
//    @TableField("user_id")
//    private Integer userId;

    @TableField(exist = false)
    private List<RegionArea> areaList;

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
    public Integer getSuperId() {
        return superId;
    }

    public void setSuperId(Integer superId) {
        this.superId = superId;
    }

    public List<RegionArea> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<RegionArea> areaList) {
        this.areaList = areaList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RegionCity{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", superId=" + superId +
        "}";
    }
}
