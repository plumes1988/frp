package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.duty.domain.RepairOrderInfo;
import com.figure.op.duty.domain.RepairTaskInfo;
import com.figure.op.duty.domain.bo.RepairTaskInfoQueryBo;
import com.figure.op.duty.domain.vo.RepairTaskInfoPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface RepairOrderInfoMapper extends BaseMapper<RepairOrderInfo> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<RepairTaskInfoPageVo> selectVoPage(@Param("page") Page<RepairTaskInfo> page, @Param("queryBo") RepairTaskInfoQueryBo queryBo);

}
