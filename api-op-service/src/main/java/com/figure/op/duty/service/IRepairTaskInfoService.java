package com.figure.op.duty.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.duty.domain.bo.RepairTaskInfoBo;
import com.figure.op.duty.domain.bo.RepairTaskInfoQueryBo;
import com.figure.op.duty.domain.bo.RepairTaskStatusBo;
import com.figure.op.duty.domain.vo.RepairTaskInfoListVo;
import com.figure.op.duty.domain.vo.RepairTaskInfoPageVo;
import com.figure.op.duty.domain.vo.RepairTaskInfoVo;

import java.util.List;

/**
 * 检修任务服务接口
 * @author fsn
 */
public interface IRepairTaskInfoService {

    /**
     * 全部列表
     * @return 全部列表
     */
    List<RepairTaskInfoListVo> queryList();

    /**
     * 分页列表
     */
    TableDataInfo<RepairTaskInfoPageVo> page(RepairTaskInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    RepairTaskInfoVo queryById(Integer id);

    /**
     * 更新检修任务
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(RepairTaskInfoBo bo);

    /**
     * 更新检修任务状态
     * @param bo 检修任务状态对象
     * @return 结果
     */
    Boolean updateStatus(RepairTaskStatusBo bo);

}
