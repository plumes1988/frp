package com.figure.op.duty.service;

import com.figure.op.duty.domain.bo.RepairEventExecuteBo;
import com.figure.op.duty.domain.bo.RepairEventExecuteQueryBo;
import com.figure.op.duty.domain.vo.RepairEventExecuteListVo;
import com.figure.op.duty.domain.vo.RepairEventExecuteVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IRepairEventExecuteService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<RepairEventExecuteListVo> queryList(RepairEventExecuteQueryBo bo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    RepairEventExecuteVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(RepairEventExecuteBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(RepairEventExecuteBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

}
