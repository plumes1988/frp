package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.device.domain.OpProductInfo;
import com.figure.op.duty.domain.RepairTaskInfo;
import com.figure.op.duty.domain.bo.RepairTaskInfoQueryBo;
import com.figure.op.duty.domain.vo.RepairTaskInfoPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface RepairTaskInfoMapper extends BaseMapper<RepairTaskInfo> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<RepairTaskInfoPageVo> selectVoPage(@Param("page") Page<RepairTaskInfo> page, @Param("queryBo") RepairTaskInfoQueryBo queryBo);


    /**
     * 批量插入
     * @param list 实体对象集合
     * @return 插入成功的记录数
     */
    int batchInsert(List<RepairTaskInfo> list);


    /**
     * 查询检修任务（设置悲观锁）
     * @param repairTaskId 检修任务ID
     * @return 检修任务
     */
    RepairTaskInfo selectByIdLock(@Param("repairTaskId") Integer repairTaskId);

}
