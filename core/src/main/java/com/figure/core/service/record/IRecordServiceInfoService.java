package com.figure.core.service.record;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.record.RecordServiceInfo;
import com.figure.core.model.record.RecordServiceInfoList;
import com.figure.core.model.record.RecordTaskInfo;
import com.figure.core.rocketmq.struct.consumer.RecordServiceCheckS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.RecordServiceSetS2PConsumer;
import com.figure.core.service.record.impl.RecordServiceInfoServiceImpl;

import java.util.List;

/**
 * <p>
 * 收录服务 IService
 * </p>
 *
 * @author feather
 * @date 2021-10-19 10:36:18
 */
public interface IRecordServiceInfoService extends IService<RecordServiceInfo> {

    List<RecordServiceInfoServiceImpl.AvailableTunnelServiceInfo> getAvailableTunnelServiceInfoList(List<RecordTaskInfo> recordTaskInfoList);

    int queryUsedTunnelNumByObj(RecordServiceInfo recordServiceInfo);

    RecordServiceInfo getAvailableRecordServiceInfoByClusterId(Integer recordClusterId);

    /**
     * 根据serviceCode获取录制节点信息
     * @Description:
     * @Param: [serviceCode]
     * @return: com.figure.core.model.record.RecordServiceInfo
     * @Author: feather
     * @Date: 27/6/2023
     */
    RecordServiceInfo getRecordServiceInfoByServiceCode(String serviceCode);

    /**
     * 平台返回前端的录制任务查询
     *
     * @Description:
     * @Param: [consumer]
     * @return: void
     * @Author: feather
     * @Date: 13/4/2023
     */
    void processRecordServiceCheck(RecordServiceCheckS2PConsumer consumer);

    /**
     * 发送录制服务节点信息
     * @Description:
     * @Param: [recordServiceInfo]
     * @return: void
     * @Author: feather
     * @Date: 27/6/2023
     */
    void sendRecordServiceSet(RecordServiceInfo recordServiceInfo);

    /**
     * 接受处理录制服务节点消息
     * @Description:
     * @Param: [consumer]
     * @return: void
     * @Author: feather
     * @Date: 27/6/2023
     */
    void processRecordServiceSet(RecordServiceSetS2PConsumer consumer);

    /**
     * 根据集群id获取相关联的服务节点信息
     * @Description:
     * @Param: [recordClusterId]
     * @return: java.util.List<com.figure.core.model.record.RecordServiceInfoList>
     * @Author: feather
     * @Date: 27/6/2023
     */
    List<RecordServiceInfoList> selectServiceInfoByClusterId(Integer recordClusterId);

    List<RecordServiceInfo> selectServiceInfoNotWithCluster();
}