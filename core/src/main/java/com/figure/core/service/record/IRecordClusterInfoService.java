package com.figure.core.service.record;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.record.RecordClusterInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 录制集群管理 IService
 * </p>
 *
 * @author feather
 * @date 2023-04-10 10:23:52
 */
public interface IRecordClusterInfoService extends IService<RecordClusterInfo> {

    List<Object> selectClusterWithServiceInfoList();

    Map<String, Object> removeRecordClusterInfoByIds(List<Integer> ids);
}