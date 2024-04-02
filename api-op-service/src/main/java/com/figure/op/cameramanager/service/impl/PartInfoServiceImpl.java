package com.figure.op.cameramanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.PartInfo;
import com.figure.op.cameramanager.domain.bo.PartInfoBo;
import com.figure.op.cameramanager.domain.vo.PartInfoVo;
import com.figure.op.cameramanager.mapper.PartInfoMapper;
import com.figure.op.cameramanager.service.PartInfoService;
import com.figure.op.common.domain.R;
import com.figure.op.common.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 16:39
 * @Version 1.5
 */
@Service
public class PartInfoServiceImpl implements PartInfoService {
    @Resource
    private PartInfoMapper partInfoMapper;

    @Override
    public R insertByBo(PartInfoBo partInfoBo) {
        QueryWrapper<PartInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("partCode",partInfoBo.getPartCode());
        PartInfo partInfo = partInfoMapper.selectOne(wrapper);
        if (partInfo != null) {
            return R.fail("部件编号已存在，请重新输入");
        }
        partInfo = BeanUtil.toBean(partInfoBo, PartInfo.class);
        Boolean flag = partInfoMapper.insert(partInfo) > 0;
        if(flag){
            return R.ok("新增部件成功");
        }
        return R.fail("新增部件失败，请重试");
    }

    @Override
    public List<PartInfoVo> list(String deviceCode) {
        QueryWrapper<PartInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("deviceCode",deviceCode);
        List<PartInfo> partInfos = partInfoMapper.selectList(wrapper);
        List<PartInfoVo> result = BeanCopyUtils.copyList(partInfos, PartInfoVo.class);
        return result;
    }

    @Override
    public R updateByBo(PartInfoBo bo) {
        QueryWrapper<PartInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("partCode",bo.getPartCode());
        wrapper.notIn("partId",bo.getPartId());
        PartInfo partInfo = partInfoMapper.selectOne(wrapper);
        if (partInfo != null) {
            return R.fail("部件编号不唯一");
        }
        PartInfo update = BeanUtil.toBean(bo, PartInfo.class);
        Boolean flag = partInfoMapper.updateById(update) > 0;
        if (flag) {
            return R.ok("修改部件信息成功");
        }
        return R.fail("修改部件信息失败");
    }

    @Override
    public Boolean deleteByPartCode(Integer partId) {
        return partInfoMapper.deleteById(partId) > 0;
    }
}
