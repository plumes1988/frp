package com.figure.core.service.signal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalSourceMonitor;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.producer.RecordDataGetP2SProducer;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 逻辑频道监测规则 服务类
 * </p>
 *
 * @author jobob
 * @since 2022-12-08
 */
public interface ISignalSourceMonitorService extends IService<SignalSourceMonitor> {

    void sendSystemMonitorParameterSet(SignalSourceMonitor signalSourceMonitor, Integer businessCode);

    boolean save(SignalSourceMonitor signalSourceMonitor);

    boolean updateById(SignalSourceMonitor signalSourceMonitor);

    boolean removeByIds(Collection<? extends Serializable> idList);

}
