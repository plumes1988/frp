package com.figure.core.repository.report;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.report.ReportDeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 上报服务设备管理 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
public interface ReportDeviceInfoMapper extends BaseMapper<ReportDeviceInfo> {

    List<HashMap<String, Object>> listRel(@Param(Constants.WRAPPER) Wrapper<ReportDeviceInfo> reportDeviceInfoQueryWrapper);

}