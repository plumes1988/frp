package com.figure.core.service.front;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.front.FrontStationInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-05-20
 */
public interface IFrontStationInfoService extends IService<FrontStationInfo> {
    List<FrontStationInfo> queryAvailableFrontStationInfo();

    void setFrontStationInfoListCache();

    List<FrontStationInfo> getFrontStationInfoListCache();

    Map<Integer, FrontStationInfo> getFrontStationByFrontIdMap();
}
