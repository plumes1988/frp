package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.duty.domain.RepairEventExecute;
import com.figure.op.duty.domain.RepairOrderInfo;
import com.figure.op.duty.domain.bo.RepairEventExecuteBo;
import com.figure.op.duty.domain.bo.RepairEventExecuteQueryBo;
import com.figure.op.duty.domain.vo.RepairEventExecuteListVo;
import com.figure.op.duty.domain.vo.RepairEventExecutePageVo;
import com.figure.op.duty.domain.vo.RepairEventExecuteVo;
import com.figure.op.duty.mapper.RepairEventExecuteMapper;
import com.figure.op.duty.service.IRepairEventExecuteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class RepairEventExecuteServiceImpl implements IRepairEventExecuteService {

    @Resource
    private RepairEventExecuteMapper RepairEventExecuteMapper;


    /**
     * 全部列表
     */
    @Override
    public List<RepairEventExecuteListVo> queryList(RepairEventExecuteQueryBo bo) {
        LambdaQueryWrapper<RepairEventExecute> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(bo.getRepairEventId() != null, RepairEventExecute::getRepairEventId, bo.getRepairEventId());
        Calendar calendar = Calendar.getInstance();
        if (bo.getEventExecuteDate() != null){
            calendar.setTime(bo.getEventExecuteDate());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        }
        Date eventExecuteDateEndTime = calendar.getTime();
        lambdaQueryWrapper.between(bo.getEventExecuteDate() != null, RepairEventExecute::getCreateTime, bo.getEventExecuteDate(), eventExecuteDateEndTime);
        lambdaQueryWrapper.orderByDesc(RepairEventExecute::getExecuteId);
        List<RepairEventExecute> repairEventExecuteList = RepairEventExecuteMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(repairEventExecuteList, RepairEventExecuteListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public RepairEventExecuteVo queryById(Integer id) {
        RepairEventExecute RepairEventExecute = RepairEventExecuteMapper.selectById(id);
        return BeanCopyUtils.copy(RepairEventExecute, RepairEventExecuteVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(RepairEventExecuteBo bo) {
        RepairEventExecute add = BeanUtil.toBean(bo, RepairEventExecute.class);
        boolean flag = RepairEventExecuteMapper.insert(add) > 0;
        if (flag) {
            bo.setExecuteId(add.getExecuteId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(RepairEventExecuteBo bo) {
        RepairEventExecute update = BeanUtil.toBean(bo, RepairEventExecute.class);
        return RepairEventExecuteMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return RepairEventExecuteMapper.deleteById(id) > 0;
    }

}
