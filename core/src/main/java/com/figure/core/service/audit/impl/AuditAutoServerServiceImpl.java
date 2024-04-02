package com.figure.core.service.audit.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.audit.AuditAutoServer;
import com.figure.core.repository.audit.AuditAutoServerMapper;
import com.figure.core.service.audit.IAuditAutoServerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 自动技审服务 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-04-09
 */
@Service
public class AuditAutoServerServiceImpl extends ServiceImpl<AuditAutoServerMapper, AuditAutoServer> implements IAuditAutoServerService {

    @Override
    public List<AuditAutoServer> selectMaxServer() {
        return getBaseMapper().selectMaxServer();
    }
}
