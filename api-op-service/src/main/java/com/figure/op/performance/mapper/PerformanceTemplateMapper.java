package com.figure.op.performance.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.performance.domain.PerformanceTemplateDo;
import com.figure.op.performance.domain.bo.PerformanceTemplateBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 绩效模板Mapper接口
 *
 * @date 2023-08-25
 */
@Mapper
public interface PerformanceTemplateMapper extends BaseMapper<PerformanceTemplateDo> {
    /**
     * 查询绩效模板
     *
     * @param id 绩效模板主键
     * @return 绩效模板
     */
    public PerformanceTemplateDo selectPerformanceTemplateById(Long id);

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
     * @param performanceTemplate 绩效模板
     * @return 结果
     */
    public int insertPerformanceTemplate(PerformanceTemplateDo performanceTemplate);

    /**
     * 修改绩效模板
     *
     * @param performanceTemplate 绩效模板
     * @return 结果
     */
    public int updatePerformanceTemplate(PerformanceTemplateDo performanceTemplate);

    /**
     * 删除绩效模板
     *
     * @param id 绩效模板主键
     * @return 结果
     */
    public int deletePerformanceTemplateById(Long id);

    /**
     * 批量删除绩效模板
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerformanceTemplateByIds(Long[] ids);


}
