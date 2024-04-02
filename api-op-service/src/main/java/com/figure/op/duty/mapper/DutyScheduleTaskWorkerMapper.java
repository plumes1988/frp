package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.op.duty.domain.DutyScheduleTaskWorker;
import com.figure.op.duty.domain.vo.DutyScheduleTaskWorkerListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author fsn
 */
public interface DutyScheduleTaskWorkerMapper extends BaseMapper<DutyScheduleTaskWorker> {

    /**
     * 查询值班人员列表
     * @param scheduleTaskId 排班任务ID
     * @param scheduleTaskIds 排班任务ID集合
     * @return 值班人员列表
     */
    List<DutyScheduleTaskWorkerListVo> selectVoList(@Param("scheduleTaskId") Integer scheduleTaskId, @Param("scheduleTaskIds") String scheduleTaskIds);

}
