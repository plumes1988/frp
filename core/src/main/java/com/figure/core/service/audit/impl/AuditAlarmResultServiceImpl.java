package com.figure.core.service.audit.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.audit.AuditAlarmResult;
import com.figure.core.repository.audit.AuditAlarmResultMapper;
import com.figure.core.service.audit.IAuditAlarmResultService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 技审异态结果 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
@Service
public class AuditAlarmResultServiceImpl extends ServiceImpl<AuditAlarmResultMapper, AuditAlarmResult> implements IAuditAlarmResultService {

    @Override
    public IPage<AuditAlarmResult> selectPage(Page<AuditAlarmResult> page, Map<String,String> conditions) {
        page.setSearchCount(false);
        List<AuditAlarmResult> auditAlarmResults= baseMapper.list(page,conditions);
        page.setRecords(auditAlarmResults);
        page.setTotal(baseMapper.count(conditions));
        return page;
    }
}
