package com.figure.op.cameramanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.CameraTypeInfo;
import com.figure.op.cameramanager.domain.bo.CameraTypeInfoBo;
import com.figure.op.cameramanager.domain.bo.PartInfoBo;
import com.figure.op.cameramanager.service.CameraTypeInfoService;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.validate.EditGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author liqiang
 * @Date 2023/9/9 21:35
 * @Version 1.5
 */
@RestController
@RequestMapping("/cameraTypeInfoController")
public class CameraTypeInfoController {

    @Resource
    private CameraTypeInfoService cameraTypeInfoService;

    /**
     * 新增
     * @param bo
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody CameraTypeInfoBo bo){
        return cameraTypeInfoService.insertByBo(bo);
    }

    /**
     * 查询所有部件
     * @param pageQuery 分页
     * @return
     */
    @GetMapping("/list")
    public R<Page<CameraTypeInfo>> list(PageQuery pageQuery){
        return R.ok(cameraTypeInfoService.list(pageQuery));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CameraTypeInfoBo bo) {
        return R.toAjax(cameraTypeInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{cameraTypeCode}")
    public R delete(@PathVariable String cameraTypeCode) {
        return cameraTypeInfoService.deleteByCameraTypeCode(cameraTypeCode);
    }


}
