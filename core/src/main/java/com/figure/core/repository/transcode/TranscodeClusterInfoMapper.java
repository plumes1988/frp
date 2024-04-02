package com.figure.core.repository.transcode;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.core.model.transcode.TranscodeClusterInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 转码集群管理 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */

public interface TranscodeClusterInfoMapper extends BaseMapper<TranscodeClusterInfo> {

    List<Map<String, Object>> selectClusterWithServiceInfoMap();
}