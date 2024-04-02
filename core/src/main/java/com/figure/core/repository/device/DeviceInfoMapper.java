package com.figure.core.repository.device;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.device.DeviceInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-06-15
 */

public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

    List<DeviceInfoList> treelist(@Param(Constants.WRAPPER) Wrapper<DeviceInfo> autoPageWrapper);

    List<DeviceInfoList> interbydevice(@Param(Constants.WRAPPER) Wrapper<DeviceInfo> autoPageWrapper);

    List<HashMap<String, Object>> detailList(@Param(Constants.WRAPPER) Wrapper<DeviceInfo> deviceInfoQueryWrapper);

}
