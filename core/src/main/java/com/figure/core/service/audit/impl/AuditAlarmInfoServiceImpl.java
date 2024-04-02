package com.figure.core.service.audit.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.audit.AuditAlarmInfo;
import com.figure.core.repository.audit.AuditAlarmInfoMapper;
import com.figure.core.service.audit.IAuditAlarmInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 技审异态管理 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
@Service
public class AuditAlarmInfoServiceImpl extends ServiceImpl<AuditAlarmInfoMapper, AuditAlarmInfo> implements IAuditAlarmInfoService {

}
