package com.figure.core.service.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.report.ReportDeviceIndicatorRel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 上报设备与指标关联 IService
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
public interface IReportDeviceIndicatorRelService extends IService<ReportDeviceIndicatorRel> {

    List<Map<String, Object>> listRel(QueryWrapper<ReportDeviceIndicatorRel> reportDeviceIndicatorRelQueryWrapper);
}