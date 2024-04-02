package com.figure.core.service.spectrum;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumRecordMessage;

import java.util.concurrent.ScheduledFuture;

/**
 * <p>
 * 频谱数据 IService
 * </p>
 *
 * @author feather
 * @date 2024-01-25 13:33:41
 */
public interface ISpectrumRecordMessageService extends IService<SpectrumRecordMessage> {

    void insertSpectrumRecordMessage();

    ScheduledFuture getScheduledFuture();
}