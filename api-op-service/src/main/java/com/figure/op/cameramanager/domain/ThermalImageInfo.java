package com.figure.op.cameramanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author liqiang
 * @Date 2023/9/14 9:20
 * @Version 1.5
 */
@Data
@TableName("thermal_image_info")
public class ThermalImageInfo {
    /**
     * 主键
     */
    @TableId
    @TableField("id")
    private Integer id;

    /**
     * 热感图像分析开关
     */
    @TableField("analysisSwitch")
    private Integer analysisSwitch;

    /**
     * 参考画面保存目录
     */
    @TableField("referenceImagePath")
    private String referenceImagePath;

    /**
     * 分析画面保存目录指定
     */
    @TableField("analysisImagePath")
    private String analysisImagePath;

    /**
     * 分析输出画面保存目录
     */
    @TableField("analysisImageOutPath")
    private String analysisImageOutPath;

    /**
     * 分析画面保存周期指定
     */
    @TableField("analysisCron")
    private String analysisCron;

    /**
     * 监测温度获取周期指定
     */
    @TableField("monitorCron")
    private String monitorCron;

    /**
     * 分析时间
     */
    @TableField("analysisCronTime")
    private Integer analysisCronTime;

    /**
     * 监测时间
     */
    @TableField("monitorCronTime")
    private Integer monitorCronTime;

    /**
     * 摄像机主键
     */
    @TableField("cameraId")
    private Integer cameraId;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;

}
