package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.duty.domain.RepairEventInfo;
import com.figure.op.duty.domain.bo.RepairEventInfoQueryBo;
import com.figure.op.duty.domain.vo.DeviceCalVo;
import com.figure.op.duty.domain.vo.RepairEventInfoListVo;
import com.figure.op.duty.domain.vo.RepairEventInfoPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface RepairEventInfoMapper extends BaseMapper<RepairEventInfo> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<RepairEventInfoPageVo> selectVoPage(@Param("page") Page<RepairEventInfo> page, @Param("queryBo") RepairEventInfoQueryBo queryBo);

    List<RepairEventInfoListVo> selectVoList(@Param("queryBo") RepairEventInfoQueryBo queryBo);

    List<DeviceCalVo> cal(@Param("queryBo") RepairEventInfoQueryBo queryBo);

}
