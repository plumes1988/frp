package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.duty.domain.DutyScheduleInfo;
import com.figure.op.duty.domain.bo.DutyScheduleInfoBo;
import com.figure.op.duty.domain.bo.DutyScheduleInfoQueryBo;
import com.figure.op.duty.domain.bo.DutyScheduleInfoStatisticsQueryBo;
import com.figure.op.duty.domain.vo.DutyScheduleInfoListVo;
import com.figure.op.duty.domain.vo.DutyScheduleInfoPageVo;
import com.figure.op.duty.domain.vo.DutyScheduleStatisticsPageVo;
import com.figure.op.system.domain.SysUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 排班计划 Mapper 接口
 *
 * @author fsn
 */
public interface DutyScheduleInfoMapper extends BaseMapper<DutyScheduleInfo> {

    /**
     * 查询排班计划分页列表
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<DutyScheduleInfoPageVo> selectVoPage(@Param("page") Page<DutyScheduleInfo> page, @Param("queryBo") DutyScheduleInfoQueryBo queryBo);

//    /**
//     * 查询排班计划列表
//     * @param queryBo 查询对象
//     * @return 列表
//     */
//    List<DutyScheduleInfoListVo> selectVoList(@Param("queryBo") DutyScheduleInfoQueryBo queryBo);

    Page<DutyScheduleStatisticsPageVo> selectStatisticsPage(@Param("page") Page<SysUserInfo> page, @Param("queryBo") DutyScheduleInfoStatisticsQueryBo queryBo);


    List<DutyScheduleInfoListVo> selectSameTimeList(@Param("bo") DutyScheduleInfoBo bo);

}
