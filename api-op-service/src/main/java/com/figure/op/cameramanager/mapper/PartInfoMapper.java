package com.figure.op.cameramanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.op.cameramanager.domain.PartInfo;


/**
 * @Author liqiang
 * @Date 2023/9/8 16:41
 * @Version 1.5
 */
public interface PartInfoMapper extends BaseMapper<PartInfo> {

    int updateDeviceID(String oldDeviceCode, String newDeviceCode);
}
