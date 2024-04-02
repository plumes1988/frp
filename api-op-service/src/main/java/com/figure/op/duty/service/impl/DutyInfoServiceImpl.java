package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.service.DictDataService;
import com.figure.op.duty.domain.DutyInfo;
import com.figure.op.duty.domain.bo.DutyInfoBo;
import com.figure.op.duty.domain.bo.DutyInfoQueryBo;
import com.figure.op.duty.domain.vo.DutyInfoListVo;
import com.figure.op.duty.domain.vo.DutyInfoPageVo;
import com.figure.op.duty.domain.vo.DutyInfoVo;
import com.figure.op.duty.mapper.DutyInfoMapper;
import com.figure.op.duty.service.IDutyInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class DutyInfoServiceImpl implements IDutyInfoService {

    @Resource
    private DutyInfoMapper dutyInfoMapper;

    @Resource
    private DictDataService dictDataService;



    /**
     * 全部列表
     */
    @Override
    public List<DutyInfoListVo> queryList() {
        List<DutyInfo> dutyInfoList = dutyInfoMapper.selectList(null);
        return BeanCopyUtils.copyList(dutyInfoList, DutyInfoListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public DutyInfoVo queryById(Integer id) {
        DutyInfo dutyInfo = dutyInfoMapper.selectById(id);
        return BeanCopyUtils.copy(dutyInfo, DutyInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(DutyInfoBo bo) {
        DutyInfo add = BeanUtil.toBean(bo, DutyInfo.class);
        boolean flag = dutyInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setDutyId(add.getDutyId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(DutyInfoBo bo) {
        DutyInfo update = BeanUtil.toBean(bo, DutyInfo.class);
        return dutyInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return dutyInfoMapper.deleteById(id) > 0;
    }

    @Override
    public TableDataInfo<DutyInfoPageVo> page(DutyInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<DutyInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(queryBo.getDutyId() != null, DutyInfo::getDutyId, queryBo.getDutyId());
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryBo.getDutyName()), DutyInfo::getDutyName, queryBo.getDutyName());
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryBo.getDutyType()), DutyInfo::getDutyType, queryBo.getDutyType());
        lambdaQueryWrapper.orderByDesc(DutyInfo::getDutyId);
        Page<DutyInfo> result = dutyInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<DutyInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), DutyInfoPageVo.class);
        resultList.forEach(item -> {
            DictDataDO task_type = dictDataService.getDictData("task_type", item.getDutyType());
            if (task_type != null) {
                item.setDutyTypeName(task_type.getLabel());
            }
        });
        Page<DutyInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }
}
