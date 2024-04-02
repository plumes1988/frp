package com.figure.core.service.op.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.audit.AuditAlarmResult;
import com.figure.core.model.op.OpScheduleHandover;
import com.figure.core.repository.op.OpScheduleHandoverMapper;
import com.figure.core.service.op.IOpScheduleHandoverService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交接班 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-06-02
 */
@Service
public class OpScheduleHandoverServiceImpl extends ServiceImpl<OpScheduleHandoverMapper, OpScheduleHandover> implements IOpScheduleHandoverService {

    @Override
    public IPage<Map<String,Object>> selectPage(Page<Map<String,Object>> page, Map<String, String> conditions) {
        page.setSearchCount(false);
        List<Map<String,Object>> opScheduleHandovers= baseMapper.list(page,conditions);
        page.setRecords(opScheduleHandovers);
        page.setTotal(baseMapper.count(conditions));
        return page;
    }
}
