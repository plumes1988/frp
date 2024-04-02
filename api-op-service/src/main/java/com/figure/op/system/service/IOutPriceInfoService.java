package com.figure.op.system.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.system.domain.OutPriceInfo;
import com.figure.op.system.domain.bo.*;
import com.figure.op.system.domain.vo.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IOutPriceInfoService {

    /**
     * 全部列表
     * @return 全部列表
     */
    List<OutPriceInfoListVo> queryList(OutPriceInfo OutPriceInfo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    OutPriceInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(OutPriceInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(OutPriceInfoBo bo);

    /**
     * 更新状态
     * @param bo 状态对象
     * @return 结果
     */
    Boolean updateStatus(OutPriceInfoStatusBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

    TableDataInfo<OutPriceInfoPageVo> page(OutPriceInfoQueryBo queryBo, PageQuery pageQuery);

    List<PriceVo> calPrice(PriceQueryBo bo);

    BigDecimal getTotalByPlanWithoutOrgan(Date start, Date end);

    BigDecimal getTotalByPlan(String organ, Date start, Date end);

    List<Map<String, Object>> getSumGroupByUseInfo(Date start, Date end);

    List<Map<String, Object>> getSumGroupBySource(Date start, Date end);

    List<Map<String, Object>> getSumGroupBySource(String organ, Date start, Date end);

    List<OperateTimelineListVo> getOperateTimelineList(Integer budgetId);

}
