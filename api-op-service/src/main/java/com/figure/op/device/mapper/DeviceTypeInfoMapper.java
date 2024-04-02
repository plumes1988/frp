package com.figure.op.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.device.domain.DeviceTypeInfo;
import com.figure.op.device.domain.bo.DeviceTypeInfoQueryBo;
import com.figure.op.device.domain.vo.DeviceTypeInfoPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface DeviceTypeInfoMapper extends BaseMapper<DeviceTypeInfo> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<DeviceTypeInfoPageVo> selectVoPage(@Param("page") Page<DeviceTypeInfo> page, @Param("queryBo") DeviceTypeInfoQueryBo queryBo);

}
