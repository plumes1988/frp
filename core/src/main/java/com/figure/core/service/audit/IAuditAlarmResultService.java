package com.figure.core.service.audit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.audit.AuditAlarmResult;

import java.util.Map;

/**
 * <p>
 * 技审异态结果 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
public interface IAuditAlarmResultService extends IService<AuditAlarmResult> {

    IPage<AuditAlarmResult> selectPage(Page<AuditAlarmResult> page, Map<String, String> conditions);
}
