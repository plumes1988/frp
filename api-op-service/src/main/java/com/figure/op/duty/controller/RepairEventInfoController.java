package com.figure.op.duty.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.device.domain.DeviceInfo;
import com.figure.op.device.mapper.DeviceInfoMapper;
import com.figure.op.duty.domain.bo.RepairEventExecuteBo;
import com.figure.op.duty.domain.bo.RepairEventExecuteQueryBo;
import com.figure.op.duty.domain.bo.RepairEventInfoBo;
import com.figure.op.duty.domain.bo.RepairEventInfoQueryBo;
import com.figure.op.duty.domain.vo.*;
import com.figure.op.duty.service.IRepairEventExecuteService;
import com.figure.op.duty.service.IRepairEventInfoService;
import com.figure.op.duty.service.IRepairTaskInfoService;
import com.figure.op.system.domain.vo.SysFrontStationVo;
import com.figure.op.system.domain.vo.SysUserInfoVo;
import com.figure.op.system.service.ISysFrontStationService;
import com.figure.op.system.service.ISysUserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 检修事件控制器
 * @author fsn
 */
@RestController
@RequestMapping("/repairEventInfo")
public class RepairEventInfoController {

    @Resource
    private IRepairEventInfoService repairEventInfoService;

    @Resource
    private IRepairEventExecuteService repairEventExecuteService;

    @Resource
    private ISysFrontStationService sysFrontStationService;


    @Resource
    private IRepairTaskInfoService taskInfoService;

    @Resource
    private IRepairEventExecuteService eventExecuteService;

    @Resource
    private ISysUserInfoService sysUserInfoService;

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<RepairEventInfoListVo>> allList(RepairEventInfoQueryBo bo) {
        List<RepairEventInfoListVo> repairEventInfoListVos = repairEventInfoService.queryList(bo);
        if (bo.getRepairTaskId() != null && !StringUtils.isEmpty(String.valueOf(bo.getRepairTaskId()))) {
            repairEventInfoListVos.forEach(item -> {
                RepairEventExecuteQueryBo bo1 = new RepairEventExecuteQueryBo();
                bo1.setRepairEventId(item.getRepairEventId().toString());
                bo1.setEventExecuteDate(bo.getEventExecuteDate());
                item.setExecuteListVos(eventExecuteService.queryList(bo1));
            });
        }
        return R.ok(repairEventInfoListVos);
    }

    /**
     * 检修事件分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<RepairEventInfoPageVo> page(RepairEventInfoQueryBo queryBo, PageQuery pageQuery) {
        return repairEventInfoService.page(queryBo, pageQuery);
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<RepairEventInfoVo> info(@PathVariable Integer id) {
        RepairEventInfoVo repairEventInfoVo = repairEventInfoService.queryById(id);

        if (repairEventInfoVo == null) {
            return R.fail("暂无此数据");
        }
        RepairTaskInfoVo repairTaskInfoVo = taskInfoService.queryById(repairEventInfoVo.getRepairTaskId());
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(Integer.valueOf(repairEventInfoVo.getDeviceId()));
        SysFrontStationVo sysFrontStationVo = sysFrontStationService.queryById(repairEventInfoVo.getStationId());
        if (repairTaskInfoVo != null) {
            repairEventInfoVo.setTaskName(repairTaskInfoVo.getTaskName());
        }
        if (deviceInfo != null) {
            repairEventInfoVo.setDeviceName(deviceInfo.getDeviceName());
            repairEventInfoVo.setDevice(deviceInfo.getDeviceName());
        }
        if (sysFrontStationVo != null) {
            repairEventInfoVo.setFrontName(sysFrontStationVo.getFrontName());
        }

        SysUserInfoVo sysUserInfoVo = sysUserInfoService.queryById(repairEventInfoVo.getWorkerId());
        if (sysUserInfoVo != null) {
            repairEventInfoVo.setWorker(sysUserInfoVo.getUserName());
        }

        RepairEventExecuteQueryBo repairEventExecuteQueryBo = new RepairEventExecuteQueryBo();
        repairEventExecuteQueryBo.setRepairEventId(String.valueOf(repairEventInfoVo.getRepairEventId()));
        List<RepairEventExecuteListVo> executeListVos = repairEventExecuteService.queryList(repairEventExecuteQueryBo);
        if (executeListVos.size() > 0) {
            RepairEventExecuteListVo repairEventExecuteListVo = executeListVos.get(0);
            RepairEventExecuteVo repairEventExecuteVo = new RepairEventExecuteVo();
            BeanUtils.copyProperties(repairEventExecuteListVo, repairEventExecuteVo);
            repairEventInfoVo.setRepairEventExecuteVo(repairEventExecuteVo);
        }

        return R.ok(repairEventInfoVo);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody RepairEventInfoBo bo) {
        return R.toAjax(repairEventInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody RepairEventInfoBo bo) {
        return R.toAjax(repairEventInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(repairEventInfoService.deleteById(id));
    }

    /**
     * 更新检修任务执行状态 0待执行 1未执行  2执行成功 3执行失败 4执行中
     */
    @PutMapping("/updateEventStatus")
    @Transactional(rollbackFor = Exception.class)
    public R<Void> updateEventStatus(@RequestBody RepairEventExecuteBo bo) {
        RepairEventInfoVo repairEventInfoVo = repairEventInfoService.queryById(bo.getRepairEventId());
        if (!StringUtils.isEmpty(bo.getExecuteStatus())) {
            repairEventInfoVo.setActStatus(bo.getExecuteStatus());
        }
        RepairEventInfoBo repairEventInfoBo = new RepairEventInfoBo();
        repairEventInfoBo.setExecuteUpdateTime(new Date());
        BeanUtils.copyProperties(repairEventInfoVo,repairEventInfoBo);
        return R.toAjax(repairEventExecuteService.insertByBo(bo) && repairEventInfoService.updateByBo(repairEventInfoBo));
    }

    // 确认状态 0 未确认 1 已确认
    @PutMapping("/confirm")
    @Transactional(rollbackFor = Exception.class)
    public R<Void> confirm(@RequestBody RepairEventInfoBo bo) {
        RepairEventInfoVo repairEventInfoVo = repairEventInfoService.queryById(bo.getRepairEventId());
        if (!StringUtils.isEmpty(bo.getConfirmStatus())) {
            repairEventInfoVo.setConfirmStatus(bo.getConfirmStatus());
        }
        if (!StringUtils.isEmpty(bo.getConfirmer())) {
            repairEventInfoVo.setConfirmer(bo.getConfirmer());
        }
        if (bo.getConfirmerId() != null && !StringUtils.isEmpty(bo.getConfirmerId().toString())) {
            repairEventInfoVo.setConfirmerId(bo.getConfirmerId());
        }
        RepairEventInfoBo repairEventInfoBo = new RepairEventInfoBo();
        BeanUtils.copyProperties(repairEventInfoVo,repairEventInfoBo);
        return R.toAjax(repairEventInfoService.updateByBo(repairEventInfoBo));
    }

    /**
     * 检修记录统计
     * @param bo 查询对象
     * @return 统计列表
     */
    @GetMapping("/cal")
    public R<List<DeviceCalVo>> cal(RepairEventInfoQueryBo bo) {
        return R.ok(repairEventInfoService.cal(bo));
    }
}
