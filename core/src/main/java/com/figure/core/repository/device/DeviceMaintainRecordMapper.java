package com.figure.core.repository.device;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.core.model.audit.AuditAlarmResult;
import com.figure.core.model.device.DeviceMaintainRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2022-06-21
 */

public interface DeviceMaintainRecordMapper extends BaseMapper<DeviceMaintainRecord> {

    IPage<DeviceMaintainRecord> selectPage(Page<?> page, @Param("conditions") Map<String,String> conditions);

}
