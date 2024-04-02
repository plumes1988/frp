package com.figure.core.service.spectrum;

import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisDataS2PConsumer;

public interface ISpectrumAnalysisDataService {

    void acceptData(SpectrumAnalysisDataS2PConsumer consumer);

}
