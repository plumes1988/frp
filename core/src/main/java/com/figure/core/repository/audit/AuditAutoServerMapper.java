package com.figure.core.repository.audit;
import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.core.model.audit.AuditAutoServer;

/**
 * <p>
 * 自动技审服务 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-04-09
 */

public interface AuditAutoServerMapper extends BaseMapper<AuditAutoServer> {

    List<AuditAutoServer> selectMaxServer();

}
