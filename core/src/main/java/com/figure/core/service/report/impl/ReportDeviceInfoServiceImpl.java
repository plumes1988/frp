package com.figure.core.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.report.ReportDeviceInfo;
import com.figure.core.repository.report.ReportDeviceInfoMapper;
import com.figure.core.service.report.IReportDeviceInfoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 上报服务设备管理Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
@Service
public class ReportDeviceInfoServiceImpl extends ServiceImpl<ReportDeviceInfoMapper, ReportDeviceInfo> implements IReportDeviceInfoService {

    @Override
    public List<HashMap<String, Object>> listRel(QueryWrapper<ReportDeviceInfo> reportDeviceInfoQueryWrapper) {
        return this.baseMapper.listRel(reportDeviceInfoQueryWrapper);
    }

}