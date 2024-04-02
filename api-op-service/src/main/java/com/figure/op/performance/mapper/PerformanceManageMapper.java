package com.figure.op.performance.mapper;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.performance.domain.PerformanceManageDO;
import com.figure.op.performance.domain.PerformanceTemplateInfoDo;
import com.figure.op.performance.domain.vo.manage.PerformanceManageBaseVO;
import com.figure.op.performance.domain.vo.manage.PerformanceManagePageReqVO;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 绩效评分管理 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PerformanceManageMapper extends BaseMapper<PerformanceManageDO> {




    /**
     * 查询绩效评分管理分页
     *
     * @param reqVo     绩效模板明细
     * @param pageQuery 分页参数
     * @return 绩效模板明细
     */
    default TableDataInfo<PerformanceManagePageReqVO> selectPage(PerformanceManagePageReqVO reqVo, PageQuery pageQuery) {
        LambdaQueryWrapper<PerformanceManageDO> qw = new LambdaQueryWrapper<>();
        qw.eq(reqVo.getAgencyId() != null, PerformanceManageDO::getAgencyId, reqVo.getAgencyId())
                .eq(reqVo.getTemplateId() != null, PerformanceManageDO::getTemplateId, reqVo.getTemplateId())
                .orderByDesc(PerformanceManageDO::getId);

        Page<PerformanceManageDO> performanceTemplateInfoDoPage = selectPage(pageQuery.build(), qw);
        Page<PerformanceManagePageReqVO> dictDataDOPageRes = new Page<>(performanceTemplateInfoDoPage.getCurrent(), performanceTemplateInfoDoPage.getSize(), performanceTemplateInfoDoPage.getTotal());

        // 转换vo
        List<PerformanceManagePageReqVO> voList = BeanCopyUtils.copyList(performanceTemplateInfoDoPage.getRecords(), PerformanceManagePageReqVO.class);
        dictDataDOPageRes.setRecords(voList);

        return TableDataInfo.build(dictDataDOPageRes);
    }
}
