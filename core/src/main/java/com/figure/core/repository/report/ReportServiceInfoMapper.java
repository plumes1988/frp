package com.figure.core.repository.report;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.report.ReportServiceInfo;
import com.figure.core.model.report.ReportServiceInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 上报服务管理 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
public interface ReportServiceInfoMapper extends BaseMapper<ReportServiceInfo> {

    List<ReportServiceInfoList> listRel(@Param(Constants.WRAPPER) Wrapper<ReportServiceInfo> reportServiceInfoQueryWrapper);

    HashMap getDetailById(Integer id);
}