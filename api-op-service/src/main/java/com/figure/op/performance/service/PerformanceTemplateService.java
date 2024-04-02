package com.figure.op.performance.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.performance.domain.PerformanceTemplateDo;
import com.figure.op.performance.domain.vo.temp.PerformanceTemplateSimpleVo;
import com.figure.op.performance.domain.bo.PerformanceTemplateBo;
import com.figure.op.performance.domain.vo.temp.PerformanceTemplateVo;

import java.util.List;

/**
 * 绩效模板Service接口
 *
 * @date 2023-08-25
 */
public interface PerformanceTemplateService {
    /**
     * 查询绩效模板
     *
     * @param id 绩效模板主键
     * @return 绩效模板
     */
    public PerformanceTemplateDo selectById(Long id);

    /**
     * 获得绩效模板的分页列表
     * @param reqVO 查询条件请求对象
     * @return 分页列表
     */
    public TableDataInfo<PerformanceTemplateVo> getPerformanceTemplatePage(PerformanceTemplateBo reqVO, PageQuery pageQuery);

    /**
     * 查询绩效模板列表
     *
     * @return 绩效简化模板
     */
    public List<PerformanceTemplateSimpleVo> selectSimpleList();

    /**
     * 查询绩效模板列表
     *
     * @param performanceTemplate 绩效模板
     * @return 绩效模板集合
     */
    public List<PerformanceTemplateDo> selectPerformanceTemplateList(PerformanceTemplateDo performanceTemplate);

    /**
     * 新增绩效模板
     *
     * @param reqVo 绩效模板
     * @return 结果
     */
    public Long insert(PerformanceTemplateBo reqVo);

    /**
     * 修改绩效模板
     *
     * @param reqVo 绩效模板
     * @return 结果
     */
    public void update(PerformanceTemplateBo reqVo);

    /**
     * 批量删除绩效模板
     *
     * @param ids 需要删除的绩效模板主键集合
     * @return 结果
     */
    public int deletePerformanceTemplateByIds(Long[] ids);

    /**
     * 删除绩效模板信息
     *
     * @param id 绩效模板主键
     * @return 结果
     */
    public int deletePerformanceTemplateById(Long id);
}
