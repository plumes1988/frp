package com.figure.core.repository.record;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.core.model.record.RecordServiceInfo;
import com.figure.core.model.record.RecordServiceInfoList;

import java.util.List;

/**
 * <p>
 * 收录服务 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2021-10-19 10:36:18
 */

public interface RecordServiceInfoMapper extends BaseMapper<RecordServiceInfo> {

    List<RecordServiceInfoList> selectRecordServiceInfoByClusterId(Integer recordClusterId);
}