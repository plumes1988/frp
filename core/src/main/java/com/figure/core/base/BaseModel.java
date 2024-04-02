package com.figure.core.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BaseModel {
    /**
     * 创建人员ID
     */

    @TableField(value = "createUserId", fill = FieldFill.INSERT)
    private Integer createUserId;


    @TableField(exist = false)
    private String createUsername;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(value = "createTime", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人员ID
     */
    @TableField(value = "updateUserId", fill = FieldFill.UPDATE)
    private Integer updateUserId;


    @TableField(exist = false)
    private String updateUsername;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime", fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String ids;

    @TableField(exist = false)
    private Integer parentId;

    @TableField(exist = false)
    private String parentIdString;

    @TableField(exist = false)
    private List<Object> children;
}
