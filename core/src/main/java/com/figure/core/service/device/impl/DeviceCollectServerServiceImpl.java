package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceCollectServer;
import com.figure.core.repository.device.DeviceCollectServerMapper;
import com.figure.core.service.device.IDeviceCollectServerService;
import com.figure.core.service.others.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.figure.core.constant.Constants.ONLINE;

/**
 * <p>
 * 网管采集服务管理表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-08-24
 */
@Service
public class DeviceCollectServerServiceImpl extends ServiceImpl<DeviceCollectServerMapper, DeviceCollectServer> implements IDeviceCollectServerService {

    @Autowired
    ICommonService commonService;

    @Override
    public void fillEntityProps(List<DeviceCollectServer> records) {

        Map<String, Integer>  collectService_online_status_map = commonService.get_collectService_online_status_map();

        for (DeviceCollectServer deviceCollectServer:records){
            Integer onlineStatus =  collectService_online_status_map.get(deviceCollectServer.getServerCode());
            deviceCollectServer.setIsOnline(ONLINE);
            if(onlineStatus!=null){
                deviceCollectServer.setIsOnline(onlineStatus);
            }
        }

    }

    @Override
    public Map<String, Integer> getOffLine() {
       return commonService.get_collectService_online_status_map();
    }
}
