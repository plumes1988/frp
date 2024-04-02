package com.figure.op.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.device.domain.OpOutProduct;
import com.figure.op.device.domain.bo.OpOutProductQueryBo;
import com.figure.op.device.domain.vo.OpOutProductPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface OpOutProductMapper extends BaseMapper<OpOutProduct> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<OpOutProductPageVo> selectVoPage(@Param("page") Page<OpOutProduct> page, @Param("queryBo") OpOutProductQueryBo queryBo);

}
