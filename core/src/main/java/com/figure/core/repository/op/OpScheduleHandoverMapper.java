package com.figure.core.repository.op;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.core.model.audit.AuditAlarmResult;
import com.figure.core.model.op.OpScheduleHandover;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交接班 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-06-02
 */
public interface OpScheduleHandoverMapper extends BaseMapper<OpScheduleHandover> {

    List<Map<String,Object>> list(Page<?> page, @Param("conditions") Map<String,String> conditions);

    Integer count(@Param("conditions") Map<String,String> conditions);

}
