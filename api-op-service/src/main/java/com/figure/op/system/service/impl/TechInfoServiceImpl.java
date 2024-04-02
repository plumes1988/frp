package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.duty.domain.DutyInfo;
import com.figure.op.duty.domain.bo.DutyInfoQueryBo;
import com.figure.op.duty.domain.vo.DutyInfoPageVo;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.TechInfo;
import com.figure.op.system.domain.bo.TechInfoBo;
import com.figure.op.system.domain.bo.TechInfoQueryBo;
import com.figure.op.system.domain.vo.TechInfoListVo;
import com.figure.op.system.domain.vo.TechInfoPageVo;
import com.figure.op.system.domain.vo.TechInfoVo;
import com.figure.op.system.mapper.SysUserInfoMapper;
import com.figure.op.system.mapper.TechInfoMapper;
import com.figure.op.system.service.ITechInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class TechInfoServiceImpl implements ITechInfoService {

    @Resource
    private TechInfoMapper techInfoMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 全部列表
     */
    @Override
    public List<TechInfoListVo> queryList(TechInfo TechInfo) {
        LambdaQueryWrapper<TechInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<TechInfo> techInfoList = techInfoMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(techInfoList, TechInfoListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public TechInfoVo queryById(Integer id) {
        TechInfo techInfo = techInfoMapper.selectById(id);
        return BeanCopyUtils.copy(techInfo, TechInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(TechInfoBo bo) {
        TechInfo add = BeanUtil.toBean(bo, TechInfo.class);
        boolean flag = techInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setTechInfoId(add.getTechInfoId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(TechInfoBo bo) {
        TechInfo update = BeanUtil.toBean(bo, TechInfo.class);
        return techInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return techInfoMapper.deleteById(id) > 0;
    }

    @Override
    public TableDataInfo<TechInfoPageVo> page(TechInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<TechInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryBo.getTechName()), TechInfo::getTechName, queryBo.getTechName());
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryBo.getTechType()), TechInfo::getTechType, queryBo.getTechType());
        lambdaQueryWrapper.eq(queryBo.getCreateUserId() != null, TechInfo::getCreateUserId, queryBo.getCreateUserId());
        lambdaQueryWrapper.between(queryBo.getStartCreateTime() != null && queryBo.getEndCreateTime() != null, TechInfo::getCreateTime, queryBo.getStartCreateTime(), queryBo.getEndCreateTime());
        lambdaQueryWrapper.orderByDesc(TechInfo::getTechInfoId);
        Page<TechInfo> result = techInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<TechInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), TechInfoPageVo.class);
        if (resultList != null){
            for (TechInfoPageVo techInfoPageVo : resultList) {
                SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(techInfoPageVo.getCreateUserId());
                if (sysUserInfo != null){
                    techInfoPageVo.setCreator(sysUserInfo.getUserName());
                }
            }
        }

        Page<TechInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }
}
