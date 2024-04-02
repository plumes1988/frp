package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.system.domain.SysFrontStation;
import com.figure.op.system.domain.bo.SysFrontStationBo;
import com.figure.op.system.domain.vo.SysFrontStationListVo;
import com.figure.op.system.domain.vo.SysFrontStationVo;
import com.figure.op.system.mapper.SysFrontStationMapper;
import com.figure.op.system.service.ISysFrontStationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class SysFrontStationServiceImpl implements ISysFrontStationService {

    @Resource
    private SysFrontStationMapper SysFrontStationMapper;

    /**
     * 全部列表
     */
    @Override
    public List<SysFrontStationListVo> queryList(SysFrontStation sysFrontStation) {
        LambdaQueryWrapper<SysFrontStation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(sysFrontStation.getFrontName() != null, SysFrontStation::getFrontName, sysFrontStation.getFrontName());
        List<SysFrontStation> SysFrontStationList = SysFrontStationMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(SysFrontStationList, SysFrontStationListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public SysFrontStationVo queryById(Integer id) {
        SysFrontStation SysFrontStation = SysFrontStationMapper.selectById(id);
        return BeanCopyUtils.copy(SysFrontStation, SysFrontStationVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(SysFrontStationBo bo) {
        SysFrontStation add = BeanUtil.toBean(bo, SysFrontStation.class);
        boolean flag = SysFrontStationMapper.insert(add) > 0;
        if (flag) {
            bo.setFrontId(add.getFrontId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(SysFrontStationBo bo) {
        SysFrontStation update = BeanUtil.toBean(bo, SysFrontStation.class);
        return SysFrontStationMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return SysFrontStationMapper.deleteById(id) > 0;
    }
}
