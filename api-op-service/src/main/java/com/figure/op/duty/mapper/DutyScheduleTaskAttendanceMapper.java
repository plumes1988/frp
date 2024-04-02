package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.duty.domain.DutyScheduleTaskAttendance;
import com.figure.op.duty.domain.bo.DutyScheduleTaskAttendanceQueryBo;
import com.figure.op.duty.domain.vo.DutyScheduleTaskAttendanceListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author fsn
 */
public interface DutyScheduleTaskAttendanceMapper extends BaseMapper<DutyScheduleTaskAttendance> {

    /**
     * 查询分页列表
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<DutyScheduleTaskAttendanceListVo> selectVoPage(@Param("page") Page<DutyScheduleTaskAttendance> page, @Param("queryBo") DutyScheduleTaskAttendanceQueryBo queryBo);

    /**
     * 查询全部列表
     * @param queryBo 查询对象
     * @return 分页列表
     */
    List<DutyScheduleTaskAttendanceListVo> selectVoList(@Param("queryBo") DutyScheduleTaskAttendanceQueryBo queryBo);

}
