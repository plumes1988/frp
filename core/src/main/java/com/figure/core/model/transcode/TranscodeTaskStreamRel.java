package com.figure.core.model.transcode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 转码任务输出关联
 * </p>
 *
 * @author feather
 * @date 2023-04-11 09:45:29
 */
@Data
@Accessors(chain = true)
@TableName("transcode_task_stream_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TranscodeTaskStreamRel", description = "转码任务输出关联")
public class TranscodeTaskStreamRel extends BaseModel {

    /**
     * 规则ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Integer streamId;
    /**
     * 任务ID
     */
    @ApiModelProperty("任务ID")
    private Integer transcodeTaskId;
    /**
     * 数据字典获取媒体类型：1UDP、2HLS、3RTMP、4HTTP_FLV
     */
    @ApiModelProperty("数据字典获取媒体类型：1UDP、2HLS、3RTMP、4HTTP_FLV")
    private Integer streamType;
    /**
     * 流媒体输出地址
     */
    @ApiModelProperty("流媒体输出地址")
    private String streamURL;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}