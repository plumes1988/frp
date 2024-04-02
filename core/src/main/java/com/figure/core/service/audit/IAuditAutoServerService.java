package com.figure.core.service.audit;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.audit.AuditAutoServer;

import java.util.List;

/**
 * <p>
 * 自动技审服务 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-04-09
 */
public interface IAuditAutoServerService extends IService<AuditAutoServer> {

    List<AuditAutoServer> selectMaxServer();
}
