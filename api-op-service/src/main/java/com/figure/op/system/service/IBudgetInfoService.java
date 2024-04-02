package com.figure.op.system.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.system.domain.BudgetInfo;
import com.figure.op.system.domain.bo.BudgetInfoBo;
import com.figure.op.system.domain.bo.BudgetInfoQueryBo;
import com.figure.op.system.domain.bo.BudgetInfoStatusBo;
import com.figure.op.system.domain.bo.PriceQueryBo;
import com.figure.op.system.domain.vo.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IBudgetInfoService {

    /**
     * 全部列表
     * @return 全部列表
     */
    List<BudgetInfoListVo> queryList(BudgetInfo BudgetInfo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    BudgetInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(BudgetInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(BudgetInfoBo bo);

    /**
     * 更新状态
     * @param bo 状态对象
     * @return 结果
     */
    Boolean updateStatus(BudgetInfoStatusBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

    TableDataInfo<BudgetInfoPageVo> page(BudgetInfoQueryBo queryBo, PageQuery pageQuery);

    List<PriceVo> calPrice(PriceQueryBo bo);

    BigDecimal getTotalByPlanWithoutOrgan(String isPlan, Date start, Date end);

    BigDecimal getTotalByPlan(String isPlan, String organ, Date start, Date end);


    List<PercentVo> getTotalGroupBySource(String isPlan, Date start, Date end);

    List<PercentVo> getTotalGroupBySource(String isPlan, String organ, Date start, Date end);

    List<OperateTimelineListVo> getOperateTimelineList(Integer budgetId);

}
