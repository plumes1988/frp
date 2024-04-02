package com.figure.op.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.system.domain.TechInfo;
import com.figure.op.system.domain.bo.TechInfoQueryBo;
import com.figure.op.system.domain.vo.TechInfoPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper 接口
 *
 * @author fsn
 */
public interface TechInfoMapper extends BaseMapper<TechInfo> {


    Page<TechInfoPageVo> selectVoPage(@Param("page") Page<TechInfo> page, @Param("queryBo") TechInfoQueryBo queryBo);

}
