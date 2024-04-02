package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.figure.core.entity.TreeEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 模块信息表
 * </p>
 *
 * @author jobob
 * @since 2021-03-16
 */
@Data
@Table(name = "sys_module_info")
public class SysModuleInfo extends TreeEntity implements Serializable {

    @Id
    @GeneratedValue
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    /**
     * 模块类型:1:父节点、2:子节点
     */
    @TableField("moduleType")
    private Integer moduleType;

    /**
     * 父节点Id
     */
    @TableField("parentId")
    private Integer parentId;

    /**
     * 模块代码
     */
    @TableField("moduleCode")
    private String moduleCode;

    /**
     * 模块名称
     */
    @TableField("moduleName")
    private String moduleName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 模块图标的地址
     */
    @TableField("imgUrl")
    private String imgUrl;

    /**
     * 功能入口URL
     */
    @TableField("moduleURL")
    private String moduleURL;

    /**
     * 创建人员ID
     */
    @TableField("createUserId")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新人员ID
     */
    @TableField("updateUserId")
    private Integer updateUserId;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 状态：0:停用、1:启用
     */
    private Integer isEnable;

    /**
     * 状态：0:删除、1:未删除
     */
    private Integer isDelete;

    @Transient
    @TableField(exist = false)
    private List<TreeEntity> children = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private String name;

    @Override
    public String getEntityId() {
        return this.id.toString();
    }

    @Override
    public String getEntityParentId() {
        return this.parentId==null?null:this.parentId.toString();
    }


}
