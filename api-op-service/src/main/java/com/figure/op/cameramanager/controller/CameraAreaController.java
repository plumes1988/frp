package com.figure.op.cameramanager.controller;

import com.figure.op.cameramanager.domain.CameraAreaInfo;
import com.figure.op.cameramanager.domain.bo.CameraAreInfoBo;
import com.figure.op.cameramanager.domain.bo.CameraInfoBo;
import com.figure.op.cameramanager.domain.vo.RuleInfoVo;
import com.figure.op.cameramanager.service.CameraAreService;
import com.figure.op.common.domain.R;
import com.figure.op.common.validate.EditGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/12 11:25
 * @Version 1.5
 */
@RestController
@RequestMapping("/cameraAreController")
public class CameraAreaController {

    @Resource
    private CameraAreService cameraAreService;

    /**
     * 查询关于该摄像机的所有区域名称
     * @return
     */
    @GetMapping("/listAreaName/{cameraId}")
    public R<List<RuleInfoVo>> listAreaName(@PathVariable Integer cameraId){
        List<RuleInfoVo> ruleInfoVos = cameraAreService.listAreaName(cameraId);
        if (ruleInfoVos.size() > 0) {
            return R.ok(ruleInfoVos);
        }
        return R.fail("该摄像头未设置测温");
    }

    /**
     * 查询关于该摄像机的所有区域名称
     * @return
     */
    @GetMapping("/listCameraAreaInfo/{cameraId}")
    public R<List<CameraAreaInfo>> listCameraAreaInfo(@PathVariable Integer cameraId){
        List<CameraAreaInfo> cameraAreaInfos = cameraAreService.listCameraAreaInfo(cameraId);
        if (cameraAreaInfos.size() > 0) {
            return R.ok(cameraAreaInfos);
        }
        return R.fail("该摄像头未绑定区域");
    }


    @PutMapping("/addCameraInfo")
    public R addCameraInfo(@RequestBody CameraAreInfoBo bo){
        return cameraAreService.addCameraInfo(bo);
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CameraAreInfoBo bo) {
        return cameraAreService.updateByBo(bo);
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{cameraAreaId}")
    public R<Void> delete(@PathVariable Integer cameraAreaId) {
        return R.toAjax(cameraAreService.deleteByCameraAreaId(cameraAreaId));
    }


}

