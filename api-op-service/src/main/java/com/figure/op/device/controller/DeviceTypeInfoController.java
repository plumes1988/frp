package com.figure.op.device.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.device.domain.DeviceTypeInfo;
import com.figure.op.device.domain.bo.DeviceTypeInfoBo;
import com.figure.op.device.domain.bo.DeviceTypeInfoQueryBo;
import com.figure.op.device.domain.vo.DeviceTypeInfoListVo;
import com.figure.op.device.domain.vo.DeviceTypeInfoPageVo;
import com.figure.op.device.domain.vo.DeviceTypeInfoVo;
import com.figure.op.device.service.IDeviceTypeInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/deviceTypeInfo")
public class DeviceTypeInfoController {

    @Resource
    private IDeviceTypeInfoService DeviceTypeInfoService;

    /**
     * 分页列表1
     */
    @GetMapping("/page")
    public TableDataInfo<DeviceTypeInfoPageVo> page(DeviceTypeInfoQueryBo queryBo, PageQuery pageQuery) {
        return DeviceTypeInfoService.page(queryBo, pageQuery);
    }

    /**
     * 分页列表2
     */
    @GetMapping("/pageList2")
    public TableDataInfo<DeviceTypeInfoPageVo> list2(DeviceTypeInfoQueryBo queryBo, PageQuery pageQuery) {
        return DeviceTypeInfoService.queryPageList2(queryBo, pageQuery);
    }

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<DeviceTypeInfoListVo>> list(DeviceTypeInfo deviceTypeInfo) {
        return R.ok(DeviceTypeInfoService.queryList(deviceTypeInfo));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<DeviceTypeInfoVo> info(@PathVariable Integer id) {
        return R.ok(DeviceTypeInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DeviceTypeInfoBo bo) {
        return R.toAjax(DeviceTypeInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DeviceTypeInfoBo bo) {
        return R.toAjax(DeviceTypeInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(DeviceTypeInfoService.deleteById(id));
    }

}
