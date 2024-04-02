package com.figure.op.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.device.domain.OpInProduct;
import com.figure.op.device.domain.bo.OpInProductQueryBo;
import com.figure.op.device.domain.vo.OpInProductPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface OpInProductMapper extends BaseMapper<OpInProduct> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<OpInProductPageVo> selectVoPage(@Param("page") Page<OpInProduct> page, @Param("queryBo") OpInProductQueryBo queryBo);

}
