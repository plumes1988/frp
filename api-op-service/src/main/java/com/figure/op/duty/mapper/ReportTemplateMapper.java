package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.duty.domain.ReportTemplate;
import com.figure.op.duty.domain.bo.ReportTemplateQueryBo;
import com.figure.op.duty.domain.vo.ReportTemplatePageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface ReportTemplateMapper extends BaseMapper<ReportTemplate> {

    /**
     * 查询分页列表2
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<ReportTemplatePageVo> selectVoPage(@Param("page") Page<ReportTemplate> page, @Param("queryBo") ReportTemplateQueryBo queryBo);

}
