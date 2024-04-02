package com.figure.op.system.service;

import com.figure.op.performance.domain.PerformanceTemplateDo;
import com.figure.op.system.domain.SysAgency;
import com.figure.op.system.domain.vo.SysAgencySimpleVo;
import com.figure.op.system.domain.vo.SysDeptListVo;

import java.util.List;

/**
 * 机构服务接口
 *
 * @author ly
 */
public interface ISysAgencyService {

    /**
     * 全部列表
     *
     * @return 全部列表
     */
    List<SysAgencySimpleVo> allSimpleList();

    /**
     * 根据id搜索
     *
     * @param id 绩效模板主键
     * @return 绩效模板
     */
    public SysAgency selectById(Integer id);
}
