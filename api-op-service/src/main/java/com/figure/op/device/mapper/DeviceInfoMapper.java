package com.figure.op.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.op.device.domain.DeviceInfo;
import com.figure.op.device.domain.vo.DeviceProductInfoVo;
import com.figure.op.device.domain.vo.LogicPositionVo;
import com.figure.op.device.domain.vo.ModelVo;
import com.figure.op.device.domain.vo.MonitorStationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备表 Mapper 接口
 *
 * @author fsn
 */
public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

//    /**
//     * 查询分页列表2
//     * @param page 分页对象
//     * @param queryBo 查询对象
//     * @return 分页列表
//     */
//    Page<DeviceProductInfoPageVo> selectVoPage(@Param("page") Page<DeviceProductInfo> page, @Param("queryBo") DeviceProductInfoQueryBo queryBo);


    List<MonitorStationVo> selectMonitorStationList();


    List<LogicPositionVo> selectLogicPositionList();


    List<String> selectDeviceTypeList();

    List<String> selectDeviceSubTypeList();

    List<ModelVo> selectModelList(@Param(value = "brandId") Integer brandId);

    List<DeviceProductInfoVo> selectProductInfoList();

}
