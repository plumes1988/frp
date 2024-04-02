package com.figure.op.performance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.figure.op.performance.domain.PerformanceDataDo;
import com.figure.op.performance.mapper.PerformanceDataMapper;
import com.figure.op.performance.service.PerformanceDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效模板明细数据Service业务层处理
 *
 * @date 2023-08-25
 */
@Service
public class PerformanceDataServiceImpl implements PerformanceDataService {

    @Resource
    private PerformanceDataMapper performanceDataMapper;

    /**
     * 新增绩效模板明细
     *
     * @param reqDo 绩效模板明细
     * @return 结果
     */
    @Override
    public int create(PerformanceDataDo reqDo) {
        return performanceDataMapper.insert(reqDo);
    }

    /**
     * 根据id查找记录
     *
     * @param id id主键
     * @return 结果
     */
    @Override
    public PerformanceDataDo selectById(Long id) {
        return performanceDataMapper.selectById(id);
    }

    @Override
    public List<PerformanceDataDo> selectInfoByManageId(Long id) {
        return performanceDataMapper.selectInfoByManageId(id);
    }

    @Override
    public void deleteByManageId(Long id) {
        LambdaQueryWrapper<PerformanceDataDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PerformanceDataDo::getManageId, id);
        performanceDataMapper.delete(queryWrapper);
    }

    /**
     * 设置得分
     *
     * @param id    分项明细id
     * @param score 得分
     * @return
     */
    @Override
    public void setScore(Long id, BigDecimal score) {
        PerformanceDataDo setScoreDo = new PerformanceDataDo();
        setScoreDo.setId(id);
        setScoreDo.setScore(score);
        performanceDataMapper.updateById(setScoreDo);
    }

}
