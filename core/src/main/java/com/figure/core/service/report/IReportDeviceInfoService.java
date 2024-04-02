package com.figure.core.service.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.report.ReportDeviceInfo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 上报服务设备管理 IService
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
public interface IReportDeviceInfoService extends IService<ReportDeviceInfo> {

    List<HashMap<String, Object>> listRel(QueryWrapper<ReportDeviceInfo> reportDeviceInfoQueryWrapper);

}