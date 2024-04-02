package com.figure.core.model.audit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 素材表
 * </p>
 *
 * @author jobob
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("audit_clip_info")
public class AuditClipInfo implements Serializable {


    /**
     * 素材ID
     */
    @TableId(value="clipId",type= IdType.AUTO)
    private Integer id;

    /**
     * 素材名称
     */
    @TableField("clipName")
    private String clipName;

    /**
     * 素材入点
     */
    @TableField("trimIn")
    private Integer trimIn;

    /**
     * 素材时长
     */
    @TableField("clipDuration")
    private Integer clipDuration;

    /**
     * 素材后缀名
     */
    @TableField("clipExtension")
    private String clipExtension;

    /**
     * 素材存储位置
     */
    @TableField("storageLocation")
    private String storageLocation;

    /**
     * 语种、关联数据字典 language
     */
    @TableField("language")
    private String language;

    /**
     * 审核状态：0未审核、1通过自动技审、2未通过自动技审、3通过人工复检、4未通过人工复检
     */
    @TableField("auditStatus")
    private Integer auditStatus;

    /**
     * 视频格式
     */
    @TableField("video")
    private Integer video;

    /**
     * AFD信息
     */
    @TableField("AFD")
    private Integer AFD;

    /**
     * 音频声道信息
     */
    @TableField("audio")
    private Integer audio;

}
