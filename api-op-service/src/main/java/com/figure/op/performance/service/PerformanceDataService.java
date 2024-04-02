package com.figure.op.performance.service;

import com.figure.op.performance.domain.PerformanceDataDo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效模板明细Service接口
 *
 * @date 2023-08-25
 */
public interface PerformanceDataService {

    /**
     * 新增绩效模板明细
     *
     * @param reqDo 绩效模板明细
     * @return 结果
     */
    public int create(PerformanceDataDo reqDo);

    /**
     * 根据id查找记录
     *
     * @param id id主键
     * @return 结果
     */
    public PerformanceDataDo selectById(Long id);

    /**
     * 查询绩效模板明细根据关联模板id
     *
     * @param id 绩效模板主键
     * @return 绩效模板明细
     */
    public List<PerformanceDataDo> selectInfoByManageId(Long id);


    /**
     * 设置得分
     * @param id 分项明细id
     * @param score 得分
     * @return
     */
    public void setScore(Long id , BigDecimal score);

    /**
     * 根据数据关联绩效评分数据id删除
     * @param manageId 绩效评分数据id
     */
    public void deleteByManageId(Long manageId );

}
