package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.signal.SignalSourceMonitor;
import com.figure.core.repository.signal.SignalSourceMonitorMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.producer.SystemMonitorParameterSetP2SProducer;
import com.figure.core.service.signal.ISignalSourceMonitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 逻辑频道监测规则 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-12-08
 */
@Service
public class SignalSourceMonitorServiceImpl extends ServiceImpl<SignalSourceMonitorMapper, SignalSourceMonitor> implements ISignalSourceMonitorService {

    @Resource
    RocketMQProducer rocketMQProducer;

    @Override
    public void sendSystemMonitorParameterSet(SignalSourceMonitor signalSourceMonitor,Integer businessCode) {
        SystemMonitorParameterSetP2SProducer producer = new SystemMonitorParameterSetP2SProducer(signalSourceMonitor);
        producer.getMessageHead().setBusinessCode(businessCode);
        this.rocketMQProducer.send(RocketMQConstants.SYSTEM_MONITOR_PARAMETER_SET, signalSourceMonitor.getLogicChannelCode(), producer);
    }

    @Override
    public boolean save(SignalSourceMonitor signalSourceMonitor) {
        boolean flag = super.save(signalSourceMonitor);
        if(flag){
            sendSystemMonitorParameterSet(signalSourceMonitor, RocketMQConstants.BUSINESSCODE_ADD);
        }
        return flag;
    }

    @Override
    public boolean updateById(SignalSourceMonitor signalSourceMonitor) {
        boolean flag = super.updateById(signalSourceMonitor);
        if(flag){
            sendSystemMonitorParameterSet(signalSourceMonitor, RocketMQConstants.BUSINESSCODE_EDIT);
        }
        return flag;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        List<SignalSourceMonitor> list = listByIds(idList);
        boolean flag = super.removeByIds(idList);
        if(flag){
            for (SignalSourceMonitor signalSourceMonitor:list){
                sendSystemMonitorParameterSet(signalSourceMonitor, RocketMQConstants.BUSINESSCODE_DEL);
            }
        }
        return flag;
    }
}
