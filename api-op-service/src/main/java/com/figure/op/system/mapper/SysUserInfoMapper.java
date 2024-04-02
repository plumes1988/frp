package com.figure.op.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.device.domain.DeviceTypeInfo;
import com.figure.op.device.domain.bo.DeviceTypeInfoQueryBo;
import com.figure.op.device.domain.vo.DeviceTypeInfoPageVo;
import com.figure.op.system.domain.SysUserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper 接口
 *
 * @author fsn
 */
public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {


}
