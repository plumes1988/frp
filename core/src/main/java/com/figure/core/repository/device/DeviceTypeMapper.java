package com.figure.core.repository.device;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.core.model.device.DeviceType;
import org.apache.ibatis.annotations.Delete;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2022-05-26
 */

public interface DeviceTypeMapper extends BaseMapper<DeviceType> {

    @Delete("delete t1 from device_type t1 left JOIN device_type t2  on  t1.parentTypeCode = t2.typeCode where t2.typeId is NULL and t1.parentTypeCode is not NULL")
    void clearData();
}
