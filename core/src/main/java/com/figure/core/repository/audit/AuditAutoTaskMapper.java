package com.figure.core.repository.audit;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.core.model.audit.AuditAutoTask;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 自动技审任务 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */

public interface AuditAutoTaskMapper extends BaseMapper<AuditAutoTask> {

    /**
     * <p>
     * 查询 : 根据conditions状态查询节目播单列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param conditions 添加
     * @return 分页对象
     */
    IPage<AuditAutoTask> selectPage(Page<?> page, @Param("conditions") Map<String,String> conditions);

}
