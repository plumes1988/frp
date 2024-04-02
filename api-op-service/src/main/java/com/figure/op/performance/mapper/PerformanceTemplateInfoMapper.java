package com.figure.op.performance.mapper;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.enums.PerformanceTemplateLevelEnum;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.performance.domain.PerformanceTemplateInfoDo;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 绩效模板明细Mapper接口
 *
 * @author ruoyi
 * @date 2023-08-25
 */
@Mapper
public interface PerformanceTemplateInfoMapper extends BaseMapper<PerformanceTemplateInfoDo> {

    /**
     * 批量删除绩效模板明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    default int deletePerformanceTemplateInfoByIds(Long[] ids) {
        return deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 查询绩效模板明细根据关联模板id
     *
     * @param id 绩效模板主键
     * @return 绩效模板明细
     */
    default List<PerformanceTemplateInfoDo> selectInfoByTemplateId(Long id) {
        LambdaQueryWrapper<PerformanceTemplateInfoDo> performanceTemplateInfoDoLqw = new LambdaQueryWrapper<>();
        return this.selectList(performanceTemplateInfoDoLqw.eq(PerformanceTemplateInfoDo::getTemplateId, id));
    }

//    /**
//     * 查询绩效模板明细分页
//     *
//     * @param reqVo     绩效模板明细
//     * @param pageQuery 分页参数
//     * @return 绩效模板明细
//     */
//    default Page<PerformanceTemplateInfoDo> selectPage(PerformanceTemplateInfoVo reqVo, PageQuery pageQuery) {
//        LambdaQueryWrapper<PerformanceTemplateInfoDo> qw = new LambdaQueryWrapper<>();
//        qw.like(StrUtil.isNotBlank(reqVo.getInfo()), PerformanceTemplateInfoDo::getInfo, reqVo.getInfo())
//                .like(StrUtil.isNotBlank(reqVo.getTitle()), PerformanceTemplateInfoDo::getTitle, reqVo.getTitle())
//                .eq(reqVo.getTemplateId() != null, PerformanceTemplateInfoDo::getTemplateId, reqVo.getTemplateId())
//                .eq(reqVo.getParentId() != null, PerformanceTemplateInfoDo::getParentId, reqVo.getParentId())
//                .in(CollUtil.isNotEmpty(reqVo.getParentIds()), PerformanceTemplateInfoDo::getParentId, reqVo.getParentIds())
//                .eq( PerformanceTemplateInfoDo::getLevel, PerformanceTemplateLevelEnum.SECOND.getLevel())
//                .orderByDesc(PerformanceTemplateInfoDo::getId);
//
//        return selectPage(pageQuery.build(), qw);
//    }
}
