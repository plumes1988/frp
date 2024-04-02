package com.figure.core.service.audit;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.audit.AuditProgramSchedule;

import java.util.Map;

/**
 * <p>
 * 节目播单表 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-04-08
 */
public interface IAuditProgramScheduleService extends IService<AuditProgramSchedule> {

    IPage<AuditProgramSchedule> selectPage(Page<AuditProgramSchedule> page, Map<String, String> conditions);

}
