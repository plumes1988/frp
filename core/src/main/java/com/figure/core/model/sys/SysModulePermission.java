package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_module_permission")
public class SysModulePermission implements Serializable {

    public static String[] BASE_PERMISSION_CODES = new String[]{"PAGE","ADD","VIEW","EDIT","DELETE"};

    public static String[] BASE_PERMISSION_NAMES = new String[]{"菜单","添加","查看","修改","删除"};

    public static String[] BASE_PERMISSION_DESES = new String[]{"菜单是否可见","添加数据项","查看数据项","修改数据项","删除数据项目"};

    @Id
    @GeneratedValue
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 模块Id
     */
    @TableField("moduleId")
    private Integer moduleId;

    /**
     * 权限编码
     */
    @TableField("permissionCode")
    private String permissionCode;

    /**
     * 权限名称
     */
    @TableField("permissionName")
    private String permissionName;

    /**
     * 说明
     */
    @TableField("mark")
    private String mark;


}
