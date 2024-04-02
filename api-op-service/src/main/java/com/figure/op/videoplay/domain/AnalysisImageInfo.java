package com.figure.op.videoplay.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author liqiang
 * @Date 2023/9/28 8:54
 * @Version 1.5
 */
@Data
@TableName("analysis_image_info")
public class AnalysisImageInfo {

    /**
     * 监测id
     */
    @TableField("id")
    @TableId
    private Integer id;

    /**
     * 摄像机id
     */
    @TableField("cameraId")
    private Integer cameraId;

    /**
     * url
     */
    @TableField("url")
    private String url;

    /**
     * 是否被删除
     */
    @TableField("isDelete")
    private Integer isDelete;

}
