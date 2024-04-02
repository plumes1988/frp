package com.figure.core.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.report.ReportServiceInfo;
import com.figure.core.model.report.ReportServiceInfoList;
import com.figure.core.repository.report.ReportServiceInfoMapper;
import com.figure.core.service.report.IReportServiceInfoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 上报服务管理Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
@Service
public class ReportServiceInfoServiceImpl extends ServiceImpl<ReportServiceInfoMapper, ReportServiceInfo> implements IReportServiceInfoService {

    @Override
    public List<ReportServiceInfoList> listRel(QueryWrapper<ReportServiceInfo> reportServiceInfoQueryWrapper) {
        return this.baseMapper.listRel(reportServiceInfoQueryWrapper);
    }

    @Override
    public HashMap getDetailById(Integer id) {
        return this.baseMapper.getDetailById(id);
    }
}