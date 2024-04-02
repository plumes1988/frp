package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.audit.AuditAlarmResult;
import com.figure.core.model.device.DeviceMaintainRecord;
import com.figure.core.repository.device.DeviceMaintainRecordMapper;
import com.figure.core.service.device.IDeviceMaintainRecordService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-06-21
 */
@Service
public class DeviceMaintainRecordServiceImpl extends ServiceImpl<DeviceMaintainRecordMapper, DeviceMaintainRecord> implements IDeviceMaintainRecordService {

    public IPage<DeviceMaintainRecord> selectPage(Page<DeviceMaintainRecord> page, Map<String,String> conditions) {
        return getBaseMapper().selectPage(page, conditions);
    }
}
