package com.figure.op.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.system.domain.KnowInfo;
import com.figure.op.system.domain.bo.KnowInfoQueryBo;
import com.figure.op.system.domain.vo.KnowInfoPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper 接口
 *
 * @author fsn
 */
public interface KnowInfoMapper extends BaseMapper<KnowInfo> {


    Page<KnowInfoPageVo> selectVoPage(@Param("page") Page<KnowInfo> page, @Param("queryBo") KnowInfoQueryBo queryBo);

}
