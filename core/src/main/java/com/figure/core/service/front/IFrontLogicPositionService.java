package com.figure.core.service.front;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.front.FrontLogicPosition;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采集点管理 IService
 * </p>
 *
 * @author feather
 * @date 2021-05-18 17:13:23
 */
public interface IFrontLogicPositionService extends IService<FrontLogicPosition> {

    void setFrontLogicPositionListCache();

    List<FrontLogicPosition> getFrontLogicPositionListCache();

    Map<Integer, FrontLogicPosition> getPositionByPositionIdMap();

}