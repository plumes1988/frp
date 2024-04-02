package com.figure.core.repository.transcode;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.transcode.TranscodeTaskInfo;
import com.figure.core.model.transcode.TranscodeTaskInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 转码规则管理 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */

public interface TranscodeTaskInfoMapper extends BaseMapper<TranscodeTaskInfo> {

    List<Map<String, Object>> queryTranscodeTaskInfoByServiceId(Integer transcodeServiceId);

    List<Map<String, Object>> getByChannelId(List<String> channelIdList);

    List<TranscodeTaskInfoList> listByQuery(@Param(Constants.WRAPPER) Wrapper<TranscodeTaskInfo> queryWrapper);

}