package com.figure.core.service.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.report.ReportServiceInfo;
import com.figure.core.model.report.ReportServiceInfoList;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 上报服务管理 IService
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
public interface IReportServiceInfoService extends IService<ReportServiceInfo> {

    List<ReportServiceInfoList> listRel(QueryWrapper<ReportServiceInfo> reportServiceInfoQueryWrapper);

    HashMap getDetailById(Integer id);
}