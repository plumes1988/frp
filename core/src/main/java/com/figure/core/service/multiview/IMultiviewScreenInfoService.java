package com.figure.core.service.multiview;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.multiview.MultiviewScreenInfo;
import com.figure.core.model.multiview.MultiviewServiceInfo;

/**
 * <p>
 * 多画面服务输出屏幕信息 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
public interface IMultiviewScreenInfoService extends IService<MultiviewScreenInfo> {
    void initMultiviewScreenInfoServices(MultiviewServiceInfo multiviewServiceInfo);
}
