package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.duty.domain.RepairPlanInfo;
import com.figure.op.duty.domain.bo.RepairPlanInfoQueryBo;
import com.figure.op.duty.domain.vo.RepairPlanInfoPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface RepairPlanInfoMapper extends BaseMapper<RepairPlanInfo> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<RepairPlanInfoPageVo> selectVoPage(@Param("page") Page<RepairPlanInfo> page, @Param("queryBo") RepairPlanInfoQueryBo queryBo);

}
