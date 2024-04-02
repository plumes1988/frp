package com.figure.op.cameramanager.controller;

import com.figure.op.cameramanager.domain.vo.FrontStationInfoVo;
import com.figure.op.cameramanager.service.FrontStationInfoService;
import com.figure.op.common.domain.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 20:45
 * @Version 1.5
 * 站点信息
 */

@RestController
@RequestMapping("/frontStationController")
public class FrontStationInfoController {

    @Resource
    private FrontStationInfoService frontStationService;

    /**
     * 拿到站点id,name
     */
    @GetMapping("/queryFrontStation")
    public R<List<FrontStationInfoVo>> queryFrontStation() {
        return R.ok(frontStationService.queryFrontStation());
    }
}
