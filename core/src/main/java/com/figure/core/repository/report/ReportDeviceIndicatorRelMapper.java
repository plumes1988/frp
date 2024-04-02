package com.figure.core.repository.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.report.ReportDeviceIndicatorRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 上报设备与指标关联 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
public interface ReportDeviceIndicatorRelMapper extends BaseMapper<ReportDeviceIndicatorRel> {

    List<Map<String, Object>> listRel(@Param(Constants.WRAPPER) QueryWrapper<ReportDeviceIndicatorRel> reportDeviceIndicatorRelQueryWrapper);
}