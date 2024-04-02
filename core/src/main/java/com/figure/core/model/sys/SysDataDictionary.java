package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.figure.core.entity.TreeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统数据字典表
 * </p>
 *
 * @author jobob
 * @since 2021-03-29
 */
@Data
@Entity
@Table(name = "sys_data_dictionary")
public class SysDataDictionary extends TreeEntity implements Serializable {


    @Id
    @GeneratedValue
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 参数名称
     */
    @TableField("paraName")
    private String paraName;

    /**
     * 参数类别
     */
    @TableField("paraType")
    private String paraType;

    /**
     * 参数值
     */
    @TableField("paraValue")
    private String paraValue;

    /**
     * 父级参数值
     */
    @TableField("parentParaType")
    private String parentParaType;

    /**
     * 父级参数类别
     */
    @TableField("parentParaValue")
    private String parentParaValue;

    /**
     * 补充数据
     */
    @TableField("paraData")
    private String paraData;

    /**
     * 备注
     */
    private String remark;


    /**
     * 创建人员ID
     */

    @TableField("createUserId")
    private Integer createUserId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(value = "createTime", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新人员ID
     */
    @TableField("updateUserId")
    private Integer updateUserId;
    /**
     * 更新时间
     */
    @TableField(value = "updateTime", fill = FieldFill.UPDATE)
    private Date updateTime;

    @Transient
    @TableField(exist = false)
    private List<TreeEntity> children = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private String name;

    @Override
    public String getEntityId() {
        return this.paraType+"-"+this.paraValue;
    }

    @Override
    public String getEntityParentId() {
        if(this.parentParaValue==null){
           return null;
        }
        if("ROOT".equals(this.parentParaValue)){
            return null;
        }
        return this.parentParaType+"-"+this.parentParaValue;
    }


    @Transient
    @TableField(exist = false)
    private Integer parentId;

    @Override
    public void setParentId(Integer parentId) {
         this.parentId = parentId;
    }


    @Transient
    @TableField(exist = false)
    private String title;

    @Transient
    @TableField(exist = false)
    private Integer value;

    @Transient
    @TableField(exist = false)
    private List<Integer> ids;

}
