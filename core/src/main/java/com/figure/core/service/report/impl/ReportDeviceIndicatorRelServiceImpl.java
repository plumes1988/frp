package com.figure.core.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.report.ReportDeviceIndicatorRel;
import com.figure.core.repository.report.ReportDeviceIndicatorRelMapper;
import com.figure.core.service.report.IReportDeviceIndicatorRelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 上报设备与指标关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
@Service
public class ReportDeviceIndicatorRelServiceImpl extends ServiceImpl<ReportDeviceIndicatorRelMapper, ReportDeviceIndicatorRel> implements IReportDeviceIndicatorRelService {

    @Override
    public List<Map<String, Object>> listRel(QueryWrapper<ReportDeviceIndicatorRel> reportDeviceIndicatorRelQueryWrapper) {
        return this.baseMapper.listRel(reportDeviceIndicatorRelQueryWrapper);
    }
}