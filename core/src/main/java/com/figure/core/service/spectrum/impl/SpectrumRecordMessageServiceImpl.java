package com.figure.core.service.spectrum.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.model.spectrum.SpectrumRecordTracedata;
import com.figure.core.model.sys.SysPara;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.spectrum.SpectrumRecordMessageMapper;
import com.figure.core.service.spectrum.ISpectrumRecordMessageService;
import com.figure.core.service.spectrum.ISpectrumRecordTracedataService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.service.tdengine.TDSpectrumRecordMessageService;
import com.figure.core.service.tdengine.TDSpectrumRecordTraceDataService;
import com.figure.core.util.StringUtils;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * <p>
 * 频谱数据Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2024-01-25 13:33:41
 */
@Service
public class SpectrumRecordMessageServiceImpl extends ServiceImpl<SpectrumRecordMessageMapper, SpectrumRecordMessage> implements ISpectrumRecordMessageService {

    @Resource
    private TaskScheduler taskScheduler;

    @Resource
    private ISysParaService sysParaService;

    @Resource
    private ISpectrumRecordTracedataService spectrumRecordTracedataService;

    @Resource
    private TDSpectrumRecordMessageService tdSpectrumRecordMessageService;

    @Resource
    private TDSpectrumRecordTraceDataService tdSpectrumRecordTraceDataService;

    @Resource
    private IRedisTemplateService redisTemplateService;

    private ScheduledFuture scheduledFuture = null;

    @Override
    public ScheduledFuture getScheduledFuture(){
        return scheduledFuture;
    }

    @Override
    @PostConstruct
    public void insertSpectrumRecordMessage() {
        int spectrumRecordInterval = 5;

        LambdaQueryWrapper<SysPara> sysParaLambdaQueryWrapper = Wrappers.lambdaQuery();
        sysParaLambdaQueryWrapper.eq(SysPara::getParaName, "spectrumRecordInterval");
        List<SysPara> spectrumPara = this.sysParaService.list(sysParaLambdaQueryWrapper);
        if (spectrumPara.size() == 1) {
            if (StringUtils.isNumeric(spectrumPara.get(0).getParaValue())) {
                spectrumRecordInterval = Integer.valueOf(spectrumPara.get(0).getParaValue());
            }
        }
//      redisTemplateService.setRedisCache(RedisConstants.CURR_SPECTRUM_DATA_MAP,new HashMap<String,SpectrumRecordMessage>());
        Runnable task = () -> {
            try {
                Map<String, SpectrumRecordMessage> spectrumRecordMessageMap = redisTemplateService
                        .getMapRedisByKeyCache(RedisConstants.CURR_SPECTRUM_DATA_MAP, String.class, SpectrumRecordMessage.class);
                if (spectrumRecordMessageMap != null) {
                    for (String key : spectrumRecordMessageMap.keySet()) {
                        SpectrumRecordMessage recordMessage = spectrumRecordMessageMap.get(key);
                        if (DateHelper.diffMillis(new Date(), recordMessage.getRecordTime()) < 10000) {
                            this.tdSpectrumRecordMessageService.saveEntity(recordMessage);
                            SpectrumRecordTracedata recordTracedata = new SpectrumRecordTracedata(recordMessage);
                            recordTracedata.setCreateTime(recordMessage.getCreateTime());
    //                        this.spectrumRecordTracedataService.save(recordTracedata);
                            this.tdSpectrumRecordTraceDataService.saveEntity(recordTracedata);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }finally {

            }

        };
       scheduledFuture = taskScheduler.scheduleAtFixedRate(task, new Date(), spectrumRecordInterval * 1000);
    }
}