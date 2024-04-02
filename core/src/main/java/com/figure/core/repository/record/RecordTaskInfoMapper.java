package com.figure.core.repository.record;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.record.RecordTaskInfo;
import com.figure.core.model.record.RecordTaskInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 录制任务 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2021-10-19 10:36:18
 */

public interface RecordTaskInfoMapper extends BaseMapper<RecordTaskInfo> {

    List<Map<String, Object>> queryRecordTaskInfoByServiceId(Integer recordServiceId);

    List<RecordTaskInfoList> listByQuery(@Param(Constants.WRAPPER) Wrapper<RecordTaskInfo> queryWrapper);
}