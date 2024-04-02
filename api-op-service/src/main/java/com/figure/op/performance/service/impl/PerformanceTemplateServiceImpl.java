package com.figure.op.performance.service.impl;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.performance.domain.PerformanceManageDO;
import com.figure.op.performance.domain.PerformanceTemplateDo;
import com.figure.op.performance.domain.PerformanceTemplateInfoDo;
import com.figure.op.performance.domain.vo.temp.PerformanceTemplateSimpleVo;
import com.figure.op.performance.domain.bo.PerformanceTemplateBo;
import com.figure.op.performance.domain.vo.temp.PerformanceTemplateVo;
import com.figure.op.performance.mapper.PerformanceTemplateMapper;
import com.figure.op.performance.service.PerformanceManageService;
import com.figure.op.performance.service.PerformanceTemplateInfoService;
import com.figure.op.performance.service.PerformanceTemplateService;
import com.figure.op.system.domain.OutPriceInfo;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.vo.OutPriceInfoPageVo;
import com.figure.op.system.mapper.SysUserInfoMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 绩效模板Service业务层处理
 *
 * @date 2023-08-25
 */
@Service
public class PerformanceTemplateServiceImpl implements PerformanceTemplateService {

    @Resource
    private PerformanceTemplateMapper performanceTemplateMapper;

    @Resource
    private PerformanceTemplateInfoService templateInfoService;

    @Resource
    @Lazy
    private PerformanceManageService manageService;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 获得绩效模板的分页列表
     *
     * @param queryBo 查询条件请求对象
     * @return 分页列表
     */
    @Override
    public TableDataInfo<PerformanceTemplateVo> getPerformanceTemplatePage(PerformanceTemplateBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<PerformanceTemplateDo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(queryBo.getName() != null && !"".equals(queryBo.getName()), PerformanceTemplateDo::getName, queryBo.getName());
        lambdaQueryWrapper.orderByDesc(PerformanceTemplateDo::getId);
        Page<PerformanceTemplateDo> result = performanceTemplateMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<PerformanceTemplateVo> resultList = BeanCopyUtils.copyList(result.getRecords(), PerformanceTemplateVo.class);

        if (resultList != null){
            for (PerformanceTemplateVo performanceTemplateVo : resultList) {
                SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(performanceTemplateVo.getCreateUserId());
                if (sysUserInfo != null){
                    performanceTemplateVo.setCreateUserName(sysUserInfo.getUserName());
                }
            }
        }

        Page<PerformanceTemplateVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }


    @Override
    public List<PerformanceTemplateSimpleVo> selectSimpleList() {
        List<PerformanceTemplateDo> performanceTemplateDos = performanceTemplateMapper.selectList(new QueryWrapper<>());
        return BeanCopyUtils.copyList(performanceTemplateDos,PerformanceTemplateSimpleVo.class);
    }

    /**
     * 查询绩效模板
     *
     * @param id 绩效模板主键
     * @return 绩效模板
     */
    @Override
    public PerformanceTemplateDo selectById(Long id) {
        return performanceTemplateMapper.selectById(id);
    }

    /**
     * 查询绩效模板列表
     *
     * @param performanceTemplate 绩效模板
     * @return 绩效模板
     */
    @Override
    public List<PerformanceTemplateDo> selectPerformanceTemplateList(PerformanceTemplateDo performanceTemplate) {
        return performanceTemplateMapper.selectPerformanceTemplateList(performanceTemplate);
    }

    /**
     * 新增绩效模板
     *
     * @param reqVo 绩效模板
     * @return 结果
     */
    @Override
    public Long insert(PerformanceTemplateBo reqVo) {
        PerformanceTemplateDo insertDo = new PerformanceTemplateDo();
        BeanUtil.copyProperties(reqVo, insertDo);
        performanceTemplateMapper.insert(insertDo);
        return insertDo.getId();
    }

    /**
     * 修改绩效模板
     *
     * @param reqVo 绩效模板
     * @return 结果
     */
    @Override
    public void update(PerformanceTemplateBo reqVo) {
        PerformanceTemplateDo updateDo = new PerformanceTemplateDo();
        BeanUtil.copyProperties(reqVo, updateDo);
        performanceTemplateMapper.updateById(updateDo);
    }

    /**
     * 批量删除绩效模板
     *
     * @param ids 需要删除的绩效模板主键
     * @return 结果
     */
    @Override
    public int deletePerformanceTemplateByIds(Long[] ids) {
        return performanceTemplateMapper.deletePerformanceTemplateByIds(ids);
    }

    /**
     * 删除绩效模板信息
     *
     * @param id 绩效模板主键
     * @return 结果
     */
    @Override
    public int deletePerformanceTemplateById(Long id) {
        // 检测是否有关联模板明细
        List<PerformanceTemplateInfoDo> performanceTemplateInfoDos = templateInfoService.selectInfoByTemplateId(id);
        if (CollUtil.isNotEmpty(performanceTemplateInfoDos)) {
            throw new ServiceException("存在关联模板明细请先删除");
        }
        // 检测是否有关联绩效评分
        List<PerformanceManageDO> performanceManageDOS = manageService.selectInfoByTemplateId(id);
        if (CollUtil.isNotEmpty(performanceManageDOS)) {
            throw new ServiceException("存在关联绩效评分数据请先删除");
        }
        return performanceTemplateMapper.deleteById(id);
    }
}
