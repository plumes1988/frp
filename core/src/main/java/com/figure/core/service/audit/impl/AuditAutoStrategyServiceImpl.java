package com.figure.core.service.audit.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.audit.AuditAutoStrategy;
import com.figure.core.repository.audit.AuditAutoStrategyMapper;
import com.figure.core.service.audit.IAuditAutoStrategyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 自动技审策略 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
@Service
public class AuditAutoStrategyServiceImpl extends ServiceImpl<AuditAutoStrategyMapper, AuditAutoStrategy> implements IAuditAutoStrategyService {

}
