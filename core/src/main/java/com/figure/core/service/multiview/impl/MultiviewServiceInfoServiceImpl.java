package com.figure.core.service.multiview.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.multiview.MultiviewServiceInfo;
import com.figure.core.repository.multiview.MultiviewServiceInfoMapper;
import com.figure.core.service.multiview.IMultiviewServiceInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 多画面服务 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-06-24
 */
@Service
public class MultiviewServiceInfoServiceImpl extends ServiceImpl<MultiviewServiceInfoMapper, MultiviewServiceInfo> implements IMultiviewServiceInfoService {
}
