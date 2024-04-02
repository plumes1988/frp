package com.figure.op.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.device.domain.OpProductInfo;
import com.figure.op.device.domain.bo.OpProductInfoQueryBo;
import com.figure.op.device.domain.vo.OpProductInfoPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface OpProductInfoMapper extends BaseMapper<OpProductInfo> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<OpProductInfoPageVo> selectVoPage(@Param("page") Page<OpProductInfo> page, @Param("queryBo") OpProductInfoQueryBo queryBo);

    /**
     * 查询库存信息（设置悲观锁）
     * @param opProductId 库存ID
     * @return 库存信息
     */
    OpProductInfo selectByIdLock(@Param("opProductId") Integer opProductId);

}
