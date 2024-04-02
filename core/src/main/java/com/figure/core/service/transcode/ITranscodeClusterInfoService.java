package com.figure.core.service.transcode;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.transcode.TranscodeClusterInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 转码集群管理 IService
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */
public interface ITranscodeClusterInfoService extends IService<TranscodeClusterInfo> {

    List<Object> selectClusterWithServiceInfoList();
}