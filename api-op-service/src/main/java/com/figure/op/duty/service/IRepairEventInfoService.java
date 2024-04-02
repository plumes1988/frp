package com.figure.op.duty.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.duty.domain.RepairEventInfo;
import com.figure.op.duty.domain.bo.RepairEventInfoBo;
import com.figure.op.duty.domain.bo.RepairEventInfoQueryBo;
import com.figure.op.duty.domain.vo.DeviceCalVo;
import com.figure.op.duty.domain.vo.RepairEventInfoListVo;
import com.figure.op.duty.domain.vo.RepairEventInfoPageVo;
import com.figure.op.duty.domain.vo.RepairEventInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IRepairEventInfoService {

    /**
     * 全部列表
     * @return 全部列表
     */
    List<RepairEventInfoListVo> queryList(RepairEventInfoQueryBo repairEventInfoQueryBo);

    /**
     * 分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<RepairEventInfoPageVo> page(RepairEventInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    RepairEventInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(RepairEventInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(RepairEventInfoBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);


    /**
     * 检修记录统计
     * @param queryBo 查询对象
     * @return 列表
     */
    List<DeviceCalVo> cal(@Param("queryBo") RepairEventInfoQueryBo queryBo);
}
