package com.figure.op.cameramanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.bo.CameraInfoBo;
import com.figure.op.cameramanager.domain.bo.MonitorDeviceInfoBo;
import com.figure.op.cameramanager.domain.vo.CameraInfoVo;
import com.figure.op.cameramanager.domain.vo.CameraMonitorAreaVo;
import com.figure.op.cameramanager.service.CameraInfoService;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author liqiang
 * @Date 2023/9/9 23:31
 * @Version 1.5
 */
@RestController
@RequestMapping("/cameraInfoController")
public class CameraInfoController {

    @Resource
    private CameraInfoService cameraInfoService;

    /**
     * 新增
     * @param bo
     * @return
     */
    @PostMapping("/add")
    public R add(@Validated(AddGroup.class)@RequestBody CameraInfoBo bo){
        return cameraInfoService.insertByBo(bo);
    }
    

    /**
     * 查询所有摄像机
     * @param pageQuery 分页
     * @return
     */
    @GetMapping("/list")
    public R<Page<CameraInfoVo>> list(PageQuery pageQuery,String cameraName){
        return R.ok(cameraInfoService.list(pageQuery, cameraName));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@RequestBody @Valid CameraInfoBo bo) {
        return cameraInfoService.updateByBo(bo);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{cameraId}")
    public R<Void> delete(@PathVariable Integer cameraId) {
        return R.toAjax(cameraInfoService.deleteByCameraId(cameraId));
    }

    /**
     * 得到参考画面
     */
    @GetMapping("/getReferencePicture/{cameraId}")
    public R getReferencePicture(@PathVariable Integer cameraId){
        return cameraInfoService.getReferencePicture(cameraId);
    }


    /**
     * 截取参考图像画面
     */
    @GetMapping("/setReferenceImage/{cameraId}")
    public R setReferenceImage(@PathVariable Integer cameraId){
        return cameraInfoService.setReferenceImage(cameraId);
    }

    /**
     * 得到图像分析结果画面
     */
    @GetMapping("/analysisOutImage/{cameraId}")
    public R getAnalysisOutImage(@PathVariable Integer cameraId){
        return cameraInfoService.getAnalysisOutImage(cameraId);
    }



}
