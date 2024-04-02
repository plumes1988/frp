package com.figure.op.duty.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.duty.domain.bo.*;
import com.figure.op.duty.domain.vo.RepairOrderInfoListVo;
import com.figure.op.duty.domain.vo.RepairOrderInfoPageVo;
import com.figure.op.duty.domain.vo.RepairOrderInfoVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IRepairOrderInfoService {

    /**
     * 全部列表
     * @return 全部列表
     */
    List<RepairOrderInfoListVo> queryList();

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    RepairOrderInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(RepairOrderInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(RepairOrderInfoBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

    TableDataInfo<RepairOrderInfoPageVo> page(RepairOrderInfoQueryBo queryBo, PageQuery pageQuery);



    /**
     * 维修工单-更新提交状态
     * @param bo 更新对象
     * @return 结果
     */
    Boolean updateSubmitStatus(RepairOrderInfoSubmitStatusBo bo);

    /**
     * 更新审核状态
     * @param bo 更新对象
     * @return 结果
     */
    Boolean updateReviewStatus(RepairOrderInfoReviewStatusBo bo);

    /**
     * 更新执行状态
     * @param bo 更新对象
     * @return 结果
     */
    Boolean updateActStatus(RepairOrderInfoActStatusBo bo);

}
