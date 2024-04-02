package com.figure.core.service.device;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.audit.AuditAlarmResult;
import com.figure.core.model.device.DeviceMaintainRecord;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-06-21
 */
public interface IDeviceMaintainRecordService extends IService<DeviceMaintainRecord> {
    IPage<DeviceMaintainRecord> selectPage(Page<DeviceMaintainRecord> page, Map<String, String> conditions);
}
