package com.figure.op.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.system.domain.TechDoc;
import com.figure.op.system.domain.bo.TechDocQueryBo;
import com.figure.op.system.domain.vo.TechDocPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper 接口
 *
 * @author fsn
 */
public interface TechDocMapper extends BaseMapper<TechDoc> {


    Page<TechDocPageVo> selectVoPage(@Param("page") Page<TechDoc> page, @Param("queryBo") TechDocQueryBo queryBo);

}
