package com.figure.core.repository.transcode;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.core.model.transcode.TranscodeServiceInfo;

import java.util.List;

/**
 * <p>
 * 转码资源管理 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */

public interface TranscodeServiceInfoMapper extends BaseMapper<TranscodeServiceInfo> {

    List<TranscodeServiceInfo> selectServiceInfoByClusterId(Integer transcodeClusterId);
}