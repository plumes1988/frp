package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.op.duty.domain.DutyScheduleInfo;
import com.figure.op.duty.domain.DutyScheduleTask;
import com.figure.op.duty.domain.bo.DutyScheduleTaskQueryBo;
import com.figure.op.duty.domain.vo.DutyScheduleTaskListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 排班任务 Mapper 接口
 *
 * @author fsn
 */
public interface DutyScheduleTaskMapper extends BaseMapper<DutyScheduleTask> {


     /**
     * 查询排班任务列表
     * @param queryBo 查询对象
     * @return 列表
     */
    List<DutyScheduleTaskListVo> selectVoList(@Param("queryBo") DutyScheduleTaskQueryBo queryBo);

}
