package com.figure.op.duty.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.duty.domain.bo.ReportTemplateBo;
import com.figure.op.duty.domain.bo.ReportTemplateQueryBo;
import com.figure.op.duty.domain.vo.ReportTemplateListVo;
import com.figure.op.duty.domain.vo.ReportTemplatePageVo;
import com.figure.op.duty.domain.vo.ReportTemplateVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IReportTemplateService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<ReportTemplateListVo> queryList();

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    ReportTemplateVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(ReportTemplateBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(ReportTemplateBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

    TableDataInfo<ReportTemplatePageVo> page(ReportTemplateQueryBo queryBo, PageQuery pageQuery);

}
