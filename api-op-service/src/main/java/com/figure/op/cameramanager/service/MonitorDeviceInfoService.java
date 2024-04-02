package com.figure.op.cameramanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.bo.MonitorDeviceInfoBo;
import com.figure.op.cameramanager.domain.bo.PartInfoBo;
import com.figure.op.cameramanager.domain.vo.MonitorDeviceInfoVo;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 16:54
 * @Version 1.5
 */
public interface MonitorDeviceInfoService {
    R insertByBo(MonitorDeviceInfoBo bo,MultipartFile image);

    Page<MonitorDeviceInfoVo> queryList(PageQuery pageQuery,String deviceName);

    R updateByBo(MonitorDeviceInfoBo bo,MultipartFile image);

    R deleteByDeviceCode(String deviceCode);
}
