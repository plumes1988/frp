package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * 系统参数表
 * </p>
 *
 * @author jobob
 * @since 2021-03-22
 */
@Data
@Entity
@TableName("sys_para")
@Table(name = "sys_para")
public class SysPara {


    /**
     * 参数ID
     */
    @Id
    @GeneratedValue
    @TableId(value="paraId",type= IdType.AUTO)
    private Integer paraId;

    /**
     * 参数名称
     */
    @TableField("paraName")
    private String paraName;

    /**
     * 参数值
     */
    @TableField("paraValue")
    private String paraValue;

    /**
     * 参数类别
     */
    @TableField("paraType")
    private String paraType;

    /**
     * 父级参数ID
     */
    @TableField("parentParaId")
    private Integer parentParaId;

    /**
     * 操作时间
     */
    @TableField("operateTime")
    private java.sql.Timestamp operateTime;

    /**
     * 备注
     */
    private String remark;


}
