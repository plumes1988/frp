package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.service.DictDataService;
import com.figure.op.duty.domain.DutyInfo;
import com.figure.op.duty.domain.ReportTemplate;
import com.figure.op.duty.domain.bo.ReportTemplateBo;
import com.figure.op.duty.domain.bo.ReportTemplateQueryBo;
import com.figure.op.duty.domain.vo.ReportTemplateListVo;
import com.figure.op.duty.domain.vo.ReportTemplatePageVo;
import com.figure.op.duty.domain.vo.ReportTemplateVo;
import com.figure.op.duty.mapper.ReportTemplateMapper;
import com.figure.op.duty.service.IReportTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class ReportTemplateServiceImpl implements IReportTemplateService {

    @Resource
    private ReportTemplateMapper ReportTemplateMapper;


    /**
     * 全部列表
     */
    @Override
    public List<ReportTemplateListVo> queryList() {
        List<ReportTemplate> ReportTemplateList = ReportTemplateMapper.selectList(null);
        return BeanCopyUtils.copyList(ReportTemplateList, ReportTemplateListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public ReportTemplateVo queryById(Integer id) {
        ReportTemplate ReportTemplate = ReportTemplateMapper.selectById(id);
        return BeanCopyUtils.copy(ReportTemplate, ReportTemplateVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(ReportTemplateBo bo) {
        ReportTemplate add = BeanUtil.toBean(bo, ReportTemplate.class);
        boolean flag = ReportTemplateMapper.insert(add) > 0;
        if (flag) {
            bo.setTemplateId(add.getTemplateId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(ReportTemplateBo bo) {
        ReportTemplate update = BeanUtil.toBean(bo, ReportTemplate.class);
        return ReportTemplateMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return ReportTemplateMapper.deleteById(id) > 0;
    }

    @Override
    public TableDataInfo<ReportTemplatePageVo> page(ReportTemplateQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<ReportTemplate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryBo.getReportType()), ReportTemplate::getReportType, queryBo.getReportType());
        Page<ReportTemplate> result = ReportTemplateMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);
        List<ReportTemplatePageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), ReportTemplatePageVo.class);
        Page<ReportTemplatePageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }
}
