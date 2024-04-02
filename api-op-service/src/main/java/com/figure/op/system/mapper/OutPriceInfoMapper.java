package com.figure.op.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.system.domain.OutPriceInfo;
import com.figure.op.system.domain.bo.OutPriceInfoQueryBo;
import com.figure.op.system.domain.bo.PriceQueryBo;
import com.figure.op.system.domain.vo.OutPriceInfoPageVo;
import com.figure.op.system.domain.vo.PriceVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户 Mapper 接口
 *
 * @author fsn
 */
public interface OutPriceInfoMapper extends BaseMapper<OutPriceInfo> {


    Page<OutPriceInfoPageVo> selectVoPage(@Param("page") Page<OutPriceInfo> page, @Param("queryBo") OutPriceInfoQueryBo queryBo);

    List<PriceVo> calPrice(@Param("bo")PriceQueryBo bo);

    // @Select(value = "select sum(price) from out_price_info where isDelete = 0 and createTime between #{start} and #{end}")
    BigDecimal getTotalByPlanWithoutOrgan(Date start, Date end);

    // @Select(value = "select sum(price) from out_price_info  where isDelete = 0 and organ = #{organ} and createTime between #{start} and #{end}")
    BigDecimal getTotalByPlan(String organ, Date start, Date end);

    // @Select(value = "select useInfo, sum(price) as total from out_price_info where isDelete = 0 and createTime between #{start} and #{end} group by useInfo")
    @MapKey(value = "useInfo")
    List<Map<String, Object>> getSumGroupByUse(Date start, Date end);

    // @Select(value = "select source, sum(price) as total from out_price_info where isDelete = 0 group by source")
    @MapKey(value = "source")
    List<Map<String, Object>> getSumGroupBySourceWithoutOrgan();

    // @Select(value = "select source, sum(price) as total from out_price_info where isDelete = 0 and organ = #{organ} and createTime between #{start} and #{end} group by source")
    @MapKey(value = "source")
    List<Map<String, Object>> getSumGroupBySource(String organ, Date start, Date end);
}
