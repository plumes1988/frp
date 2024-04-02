package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.system.domain.SysAgency;
import com.figure.op.system.domain.SysDept;
import com.figure.op.system.domain.bo.SysDeptBo;
import com.figure.op.system.domain.vo.SysAgencySimpleVo;
import com.figure.op.system.domain.vo.SysDeptListVo;
import com.figure.op.system.domain.vo.SysDeptVo;
import com.figure.op.system.mapper.SysAgencyMapper;
import com.figure.op.system.mapper.SysDeptMapper;
import com.figure.op.system.service.ISysAgencyService;
import com.figure.op.system.service.ISysDeptService;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机构服务接口
 *
 * @author fsn
 */
@Service
public class SysAgencyServiceImpl implements ISysAgencyService {

    @Resource
    private SysAgencyMapper sysAgencyMapper;

    /**
     * 全部列表
     */
    @Override
    public List<SysAgencySimpleVo> allSimpleList() {
        List<SysAgency> sysAgencies = sysAgencyMapper.selectList(new QueryWrapper<>());
        return BeanCopyUtils.copyList(sysAgencies, SysAgencySimpleVo.class);
    }

    @Override
    public SysAgency selectById(Integer id) {
        return sysAgencyMapper.selectById(id);
    }
}
