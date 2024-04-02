package com.figure.core.service.audit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.audit.AuditAutoTask;

import java.util.Map;

/**
 * <p>
 * 自动技审任务 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
public interface IAuditAutoTaskService extends IService<AuditAutoTask> {

    IPage<AuditAutoTask> selectPage(Page<AuditAutoTask> page, Map<String, String> map);
}
