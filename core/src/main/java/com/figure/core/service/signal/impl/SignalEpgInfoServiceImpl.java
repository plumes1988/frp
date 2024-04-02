package com.figure.core.service.signal.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.signal.SignalEpgInfoMapper;
import com.figure.core.model.signal.SignalEpgInfo;
import com.figure.core.service.signal.ISignalEpgInfoService;

/**
 * <p>
 * 节目单信息Service业务层处理
 * </p>
 * 
 * @author feather
 * @date 2021-05-25 17:10:38
 */
@Service
public class SignalEpgInfoServiceImpl extends ServiceImpl<SignalEpgInfoMapper, SignalEpgInfo> implements ISignalEpgInfoService {

}