package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 网管采集服务管理表
 * </p>
 *
 * @author jobob
 * @since 2023-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("spectrum_collect_server")
public class SpectrumCollectServer implements Serializable {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 服务编号
     */
    @TableField("serverCode")
    private String serverCode;

    /**
     * 关联频谱仪设备ID
     */
    @TableField("spectrumDeviceId")
    private Integer spectrumDeviceId;

    /**
     * 频率步长（单位Hz）
     */
    @TableField("frequencyStep")
    private Integer frequencyStep;

    /**
     * 起始频率（单位Hz）
     */
    @TableField("startFrequency")
    private Integer startFrequency;

    /**
     * 结束频率（单位Hz）
     */
    @TableField("endFrequency")
    private Integer endFrequency;

    /**
     * 实时频谱数据上报频率
     */
    @TableField("collectFreq")
    private Integer collectFreq;


}
