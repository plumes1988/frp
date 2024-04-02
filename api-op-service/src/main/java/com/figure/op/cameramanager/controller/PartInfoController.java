package com.figure.op.cameramanager.controller;

import com.figure.op.cameramanager.domain.bo.PartInfoBo;
import com.figure.op.cameramanager.domain.vo.PartInfoVo;
import com.figure.op.cameramanager.service.PartInfoService;
import com.figure.op.common.domain.R;
import com.figure.op.common.validate.EditGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 16:04
 * @Version 1.5
 * 部件管理模块
 */
@RestController
@RequestMapping("/partInfoController")
public class PartInfoController {
    @Resource
    private PartInfoService partInfoService;

    /**
     * 新增
     * @param partInfoBo
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody PartInfoBo partInfoBo){
        return partInfoService.insertByBo(partInfoBo);
    }

    /**
     * 通过被拍摄设备的设备编码查询所有部件
     * @param deviceCode
     * @return
     */
    @GetMapping("/list/{deviceCode}")
    public R<List<PartInfoVo>> list(@PathVariable("deviceCode") String deviceCode){
        return R.ok(partInfoService.list(deviceCode));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PartInfoBo bo) {
        return partInfoService.updateByBo(bo);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{partId}")
    public R<Void> delete(@PathVariable Integer partId) {
        return R.toAjax(partInfoService.deleteByPartCode(partId));
    }
}
