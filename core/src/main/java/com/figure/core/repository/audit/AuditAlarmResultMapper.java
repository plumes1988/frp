package com.figure.core.repository.audit;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.core.model.audit.AuditAlarmResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 技审异态结果 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */

public interface AuditAlarmResultMapper extends BaseMapper<AuditAlarmResult> {

    List<AuditAlarmResult> list(Page<?> page, @Param("conditions") Map<String,String> conditions);

    Integer count(@Param("conditions") Map<String,String> conditions);
}
