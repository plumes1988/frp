package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.signal.SignalMatrixInfo;
import com.figure.core.repository.signal.SignalMatrixInfoMapper;
import com.figure.core.service.signal.ISignalMatrixInfoService;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 矩阵信息列表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-12-06
 */
@Service
public class SignalMatrixInfoServiceImpl extends ServiceImpl<SignalMatrixInfoMapper, SignalMatrixInfo> implements ISignalMatrixInfoService {

}
