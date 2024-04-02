package com.figure.core.service.audit.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.audit.AuditProgramSchedule;
import com.figure.core.repository.audit.AuditProgramScheduleMapper;
import com.figure.core.service.audit.IAuditProgramScheduleService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 节目播单表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-04-08
 */
@Service
public class AuditProgramScheduleServiceImpl extends ServiceImpl<AuditProgramScheduleMapper, AuditProgramSchedule> implements IAuditProgramScheduleService {

    @Override
    public IPage<AuditProgramSchedule> selectPage(Page<AuditProgramSchedule> page, Map<String,String> conditions) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return getBaseMapper().selectPage(page, conditions);
    }

}
