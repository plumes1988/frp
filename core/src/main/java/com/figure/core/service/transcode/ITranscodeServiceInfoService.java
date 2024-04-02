package com.figure.core.service.transcode;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.transcode.TranscodeServiceInfo;
import com.figure.core.rocketmq.struct.consumer.TranscodeServiceCheckS2PConsumer;
import com.figure.core.service.transcode.impl.TranscodeServiceInfoServiceImpl;

import java.util.List;

/**
 * <p>
 * 转码资源管理 IService
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */
public interface ITranscodeServiceInfoService extends IService<TranscodeServiceInfo> {

    /**
     * @Description: 根据前端ID查询可用的转码服务器
     * @Param: Integer frontId
     * @return: List<TranscodeServiceInfo>
     * @Author: feather
     * @Date: 2021/10/13
     */
    List<TranscodeServiceInfo> queryAvailableServiceByFrontId(Integer frontId);

    /**
     * @Description: 根据前端ID更新转码服务器的同步状态
     * @Param: [frontId]
     * @return: boolean
     * @Author: feather
     * @Date: 2021/10/13
     */
    boolean updateBatchSynStatusByFrontId(Integer frontId);

    /**
     * 获取可用的转码服务器
     *
     * @Description:
     * @Param: []
     * @return: java.util.List<com.figure.core.model.transcode.TranscodeServiceInfo>
     * @Author: feather
     * @Date: 2021/10/19
     */
    List<TranscodeServiceInfo> queryAvailableService();

    /**
     * 判断转码服务是否有足够的通道，返回对象
     *
     * @Description:
     * @Param: [transcodeServiceInfo]
     * @return: AvailableTunnelServiceInfo
     * @Author: feather
     * @Date: 2021/10/19
     */
    List<TranscodeServiceInfoServiceImpl.AvailableTunnelServiceInfo> getTunnelByServiceIdsList(List<String> transcodeServiceIdsList);

    /**
     * 根据转码服务对象获取可使用通道数量
     *
     * @Description:
     * @Param: [transcodeServiceInfo]
     * @return: java.lang.Integer
     * @Author: feather
     * @Date: 2021/10/19
     */
    int queryAvailableTunnelNumByObj(TranscodeServiceInfo transcodeServiceInfo);

    /**
     * 查询已使用的通道数量
     *
     * @Description:
     * @Param: [transcodeServiceInfo]
     * @return: int
     * @Author: feather
     * @Date: 2021/10/19
     */
    int queryUsedTunnelNumByObj(TranscodeServiceInfo transcodeServiceInfo);


    String getTranscodeServiceCodeByListAndChannelId(List<TranscodeServiceInfoServiceImpl.AvailableTunnelServiceInfo> availableTunnelServiceInfoList,
                                                     SignalChannelInfo signalChannelInfo);

    List<TranscodeServiceInfo> selectServiceInfoByClusterId(Integer transcodeClusterId);

    void processServiceCheck(TranscodeServiceCheckS2PConsumer consumer);
}