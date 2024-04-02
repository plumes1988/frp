package com.figure.op.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.system.domain.BudgetInfo;
import com.figure.op.system.domain.bo.BudgetInfoQueryBo;
import com.figure.op.system.domain.bo.PriceQueryBo;
import com.figure.op.system.domain.vo.BudgetInfoPageVo;
import com.figure.op.system.domain.vo.PercentVo;
import com.figure.op.system.domain.vo.PriceVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户 Mapper 接口
 *
 * @author fsn
 */
public interface BudgetInfoMapper extends BaseMapper<BudgetInfo> {


    Page<BudgetInfoPageVo> selectVoPage(@Param("page") Page<BudgetInfo> page, @Param("queryBo") BudgetInfoQueryBo queryBo);

    List<PriceVo> calPrice(@Param("bo") PriceQueryBo bo);

    // @Select(value = "select sum(price) from budget_info where isDelete = 0 and isPlan =#{isPlan} and createTime between #{start} and #{end}")
    BigDecimal getTotalByPlanWithoutOrgan(String isPlan, Date start, Date end);

    // @Select(value = "select sum(price) from budget_info where isDelete = 0 and isPlan =#{isPlan} and organ = #{organ} and createTime between #{start} and #{end} ")
    BigDecimal getTotalByPlan(String isPlan, String organ, Date start, Date end);

    // @Select(value = "select sum(price) as amount, source from budget_info where isDelete = 0 and isPlan = #{isPlan} and createTime between #{start} and #{end} group by source")
    List<PercentVo> getTotalGroupBySourceWithoutOrgan(String isPlan, Date start, Date end);

    // @Select(value = "select sum(price) as amount, source from budget_info where isDelete = 0 and isPlan = #{isPlan} and organ = #{organ} and createTime between #{start} and #{end} group by source")
    List<PercentVo> getTotalGroupBySource(String isPlan, String organ, Date start, Date end);
}
