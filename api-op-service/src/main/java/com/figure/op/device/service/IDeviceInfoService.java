package com.figure.op.device.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.device.domain.bo.DeviceInfoQueryBo;
import com.figure.op.device.domain.bo.DeviceProductInfoBo;
import com.figure.op.device.domain.vo.*;
import com.figure.op.duty.domain.bo.DutyScheduleInfoQueryBo;
import com.figure.op.duty.domain.vo.DutyScheduleInfoPageVo;

import java.util.List;

/**
 * 设备服务接口
 * @author fsn
 */
public interface IDeviceInfoService {

    /**
     * 设备-全部列表
     * @return 全部列表
     */
    List<DeviceInfoListVo> queryDeviceList();


    /**
     * 设备-分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<DeviceInfoPageVo> queryDevicePage(DeviceInfoQueryBo queryBo, PageQuery pageQuery);


    List<MonitorStationVo> queryMonitorStationList();

    List<LogicPositionVo> queryLogicPositionList();

    List<String> queryDeviceTypeList();

    List<String> queryDeviceSubTypeList();

    List<ModelVo> queryModelList(Integer brandId);

    List<DeviceProductInfoVo> queryProductInfoList();

}
