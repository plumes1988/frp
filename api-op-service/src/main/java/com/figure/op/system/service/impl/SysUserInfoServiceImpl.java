package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.bo.SysUserInfoBo;
import com.figure.op.system.domain.vo.SysUserInfoListVo;
import com.figure.op.system.domain.vo.SysUserInfoVo;
import com.figure.op.system.mapper.SysUserInfoMapper;
import com.figure.op.system.service.ISysUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class SysUserInfoServiceImpl implements ISysUserInfoService {

    @Resource
    private SysUserInfoMapper SysUserInfoMapper;

    /**
     * 全部列表
     */
    @Override
    public List<SysUserInfoListVo> queryList(SysUserInfo SysUserInfo) {
        LambdaQueryWrapper<SysUserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<SysUserInfo> SysUserInfoList = SysUserInfoMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(SysUserInfoList, SysUserInfoListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public SysUserInfoVo queryById(Integer id) {
        SysUserInfo SysUserInfo = SysUserInfoMapper.selectById(id);
        return BeanCopyUtils.copy(SysUserInfo, SysUserInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(SysUserInfoBo bo) {
        SysUserInfo add = BeanUtil.toBean(bo, SysUserInfo.class);
        boolean flag = SysUserInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(SysUserInfoBo bo) {
        SysUserInfo update = BeanUtil.toBean(bo, SysUserInfo.class);
        return SysUserInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return SysUserInfoMapper.deleteById(id) > 0;
    }
}
