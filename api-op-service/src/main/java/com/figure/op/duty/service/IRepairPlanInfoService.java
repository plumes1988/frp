package com.figure.op.duty.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.duty.domain.bo.RepairPlanInfoBo;
import com.figure.op.duty.domain.bo.RepairPlanInfoCancelBo;
import com.figure.op.duty.domain.bo.RepairPlanInfoQueryBo;
import com.figure.op.duty.domain.vo.RepairPlanInfoListVo;
import com.figure.op.duty.domain.vo.RepairPlanInfoPageVo;
import com.figure.op.duty.domain.vo.RepairPlanInfoVo;

import java.util.List;

/**
 * 检修计划服务接口
 * @author fsn
 */
public interface IRepairPlanInfoService {

    /**
     * 全部列表
     * @return 全部列表
     */
    List<RepairPlanInfoListVo> queryList();

    /**
     * 分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<RepairPlanInfoPageVo> page(RepairPlanInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    RepairPlanInfoVo queryById(Integer id);

    /**
     * 新增检修计划
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(RepairPlanInfoBo bo);


    /**
     * 作废
     * @param bo 作废对象
     * @return 结果
     */
    Boolean cancel(RepairPlanInfoCancelBo bo);


}
