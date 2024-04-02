package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.system.domain.SysDept;
import com.figure.op.system.domain.bo.SysDeptBo;
import com.figure.op.system.domain.vo.SysDeptListVo;
import com.figure.op.system.domain.vo.SysDeptVo;
import com.figure.op.system.mapper.SysDeptMapper;
import com.figure.op.system.service.ISysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Resource
    private SysDeptMapper SysDeptMapper;

    /**
     * 全部列表
     */
    @Override
    public List<SysDeptListVo> queryList(SysDept SysDept) {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<SysDept> SysDeptList = SysDeptMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(SysDeptList, SysDeptListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public SysDeptVo queryById(Integer id) {
        SysDept SysDept = SysDeptMapper.selectById(id);
        return BeanCopyUtils.copy(SysDept, SysDeptVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(SysDeptBo bo) {
        SysDept add = BeanUtil.toBean(bo, SysDept.class);
        boolean flag = SysDeptMapper.insert(add) > 0;
        if (flag) {
            bo.setDeptId(add.getDeptId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(SysDeptBo bo) {
        SysDept update = BeanUtil.toBean(bo, SysDept.class);
        return SysDeptMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return SysDeptMapper.deleteById(id) > 0;
    }
}
