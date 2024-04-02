package com.figure.core.model.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.figure.core.base.BaseModel;
import com.figure.core.helper.IntegerArrayDeserializer;
import com.figure.core.helper.IntegerArraySerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 定时录制时间规则
 * </p>
 *
 * @author feather
 * @date 2023-04-11 11:21:04
 */
@Data
@Accessors(chain = true)
@TableName("record_timing_rule")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RecordTimingRule", description = "定时录制时间规则")
public class RecordTimingRule extends BaseModel {

    /**
     * 规则ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("规则ID")
    private Integer ruleId;
    /**
     * 任务ID
     */
    @ApiModelProperty("任务ID")
    private Integer recordTaskId;
    /**
     * 排列顺序
     */
    @ApiModelProperty("排列顺序")
    private Integer serno;
    /**
     * 是否全天 0:是 1：否
     */
    @ApiModelProperty("是否全天 0:是 1：否")
    private Integer isFullDay;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty("结束时间")
    private Date endTime;
    /**
     * 规则类型：1每天、2每周、3单次
     */
    @ApiModelProperty("规则类型：1每天、2每周、3单次")
    private Integer regularMode;
    /**
     * 星期几（按周时可以多选一周中的星期） 格式采用java日期对应编码星期天为1 顺沿星期六为7  如周一 周三  则数据为 2:4
     */
    @ApiModelProperty("星期几（按周时可以多选一周中的星期） 格式采用java日期对应编码星期天为1 顺沿星期六为7  如周一 周三  则数据为 2:4")
    @JsonDeserialize(using = IntegerArrayDeserializer.class)
    @JsonSerialize(using = IntegerArraySerializer.class)
    private String dayOfWeek;
    /**
     * 工作日是否循环：0单次执行、1循环执行
     */
    @ApiModelProperty("工作日是否循环：0单次执行、1循环执行")
    private Integer workRepeatMode;
    /**
     * 开始日期
     */
    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束日期
     */
    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 任务性质 0包含 1剔除
     */
    @ApiModelProperty("任务性质 0包含 1剔除")
    @TableField(exist = false)
    private Integer existModus;
}