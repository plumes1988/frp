package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.signal.SignalTypeInfo;
import com.figure.core.repository.signal.SignalTypeInfoMapper;
import com.figure.core.service.signal.ISignalTypeInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 信号类型表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-05-18
 */
@Service
public class SignalTypeInfoServiceImpl extends ServiceImpl<SignalTypeInfoMapper, SignalTypeInfo> implements ISignalTypeInfoService {

}
