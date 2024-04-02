package com.figure.core.model.transcode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 转码规则管理
 * </p>
 *
 * @author feather
 * @date 2023-04-11 09:45:29
 */
@Data
@Accessors(chain = true)
@TableName("transcode_rule_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TranscodeRuleInfo", description = "转码规则管理")
public class TranscodeRuleInfo extends BaseModel {

    /**
     * 规则ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("规则ID")
    private Integer ruleId;
    /**
     * 规则名称
     */
    @ApiModelProperty("规则名称")
    private String ruleName;
    /**
     * 媒体类型 1-音频 2-标清 3-高清 4-4k
     */
    @ApiModelProperty("媒体类型 1-音频 2-标清 3-高清 4-4k")
    private Integer mediaType;
    /**
     * 工作模式：0：只做转发，1：编码转发
     */
    @ApiModelProperty("工作模式：0：源码转发，1：编码转发")
    private Integer workMode;
    /**
     * 视频编码 默认H264
     */
    @ApiModelProperty("视频编码 默认H264")
    private String videoCode;
    /**
     * 视频高度
     */
    @ApiModelProperty("视频高度")
    private Integer videoHeight;
    /**
     * 视频宽度
     */
    @ApiModelProperty("视频宽度")
    private Integer videoWidth;
    /**
     * 视频帧率 单位fps
     */
    @ApiModelProperty("视频帧率 单位fps")
    private Integer frameRate;
    /**
     * 视频码率 单位Kbps
     */
    @ApiModelProperty("视频码率 单位Kbps")
    private Integer videoCodeRate;
    /**
     * 音频编码 默认 AC3，0不转码，沿用原始
     */
    @ApiModelProperty("音频编码 默认 AC3，0不转码，沿用原始")
    private String audioCode;
    /**
     * 采样率
     */
    @ApiModelProperty("采样率")
    private Integer samplingRate;
    /**
     * 音频码率 单位Kbps
     */
    @ApiModelProperty("音频码率 单位Kbps")
    private Integer audioCodeRate;
    /**
     * 编码模式：1：性能（superfast），2：平衡（medium），3：质量（slow）
     */
    @ApiModelProperty("编码模式：1：性能（superfast），2：平衡（medium），3：质量（slow）")
    private Integer codingMode;
    /**
     * 开启RTMP,是否是默认规则（0，是。1，否）
     */
    @ApiModelProperty("开启RTMP,是否是默认规则（0，是。1，否）")
    private Integer showRTMP;
    /**
     * 开启UDP，是否是默认规则（0，是。1，否）
     */
    @ApiModelProperty("开启UDP，是否是默认规则（0，是。1，否）")
    private Integer showUDP;
    /**
     * 开启HTTP_TS，是否是默认规则（0，是。1，否）
     */
    @ApiModelProperty("开启HTTP_TS，是否是默认规则（0，是。1，否）")
    private Integer showHTTP_FLV;
    /**
     * 开启HLS，是否是默认规则（0，是。1，否）
     */
    @ApiModelProperty("开启HLS，是否是默认规则（0，是。1，否）")
    private Integer showHLS;
    /**
     * 开启OSD：0不开启  1开启
     */
    @ApiModelProperty("开启OSD：0不开启  1开启")
    private Integer isOSD;
    /**
     * 文字位置
     */
    @ApiModelProperty("文字位置")
    private Integer textXPosition;
    /**
     *
     */
    @ApiModelProperty("")
    private Integer textYPosition;
    /**
     * 文字字体大小
     */
    @ApiModelProperty("文字字体大小")
    private Integer textFontSize;
    /**
     * 时间位置
     */
    @ApiModelProperty("时间位置")
    private Integer timeXPosition;
    /**
     *
     */
    @ApiModelProperty("")
    private Integer timeYPosition;
    /**
     * 时间字体大小
     */
    @ApiModelProperty("时间字体大小")
    private Integer timeFontSize;
    /**
     * 是否开启时间信息：0不显示，1显示
     */
    @ApiModelProperty("是否开启时间信息：0不显示，1显示")
    private Integer showTimeInfo;
    /**
     * 是否开启位置信息：0不显示，1显示
     */
    @ApiModelProperty("是否开启位置信息：0不显示，1显示")
    private Integer showFrontName;
    /**
     * 是否显示采集点：0不显示，1显示
     */
    @ApiModelProperty("是否显示采集点：0不显示，1显示")
    private Integer showMonitorPoint;
    /**
     * 是否显示信号类型信息：0不显示，1显示
     */
    @ApiModelProperty("是否显示信号类型信息：0不显示，1显示")
    private Integer showSignal;
    /**
     * 是否显示频道名称信息：0不显示，1显示
     */
    @ApiModelProperty("是否显示频道名称信息：0不显示，1显示")
    private Integer showChannelName;
    /**
     * 设为该媒体类型默认规则（0，是。1，否）
     */
    @ApiModelProperty("设为该媒体类型默认规则（0，是。1，否）")
    private Integer isDefault;
    /**
     * 同步状态 0：未同步 1：已同步
     */
    @ApiModelProperty("同步状态 0：未同步 1：已同步 ")
    private Integer syncStatus;
    /**
     *状态：0:未删除、1:已删除、2:停用 
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

    @TableField(exist = false)
    private String showPositionInfo;
    @TableField(exist = false)
    private String showSignalInfo;
    @TableField(exist = false)
    private String showChannelNameInfo;

    private static Map<String, String> codingRateMap = new HashMap<String, String>() {{
        put("superfast", "3");
        put("faster", "0");
        put("medium", "1");
        put("slower", "2");
        put("veryslow", "4");
    }};
}