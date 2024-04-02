package com.figure.core.service.audit.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.audit.AuditAutoTask;
import com.figure.core.repository.audit.AuditAutoTaskMapper;
import com.figure.core.service.audit.IAuditAutoTaskService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 自动技审任务 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
@Service
public class AuditAutoTaskServiceImpl extends ServiceImpl<AuditAutoTaskMapper, AuditAutoTask> implements IAuditAutoTaskService {

    @Override
    public IPage<AuditAutoTask> selectPage(Page<AuditAutoTask> page, Map<String, String> conditions) {
        return getBaseMapper().selectPage(page, conditions);
    }
}
