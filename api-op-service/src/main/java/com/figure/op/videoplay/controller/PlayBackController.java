package com.figure.op.videoplay.controller;

import com.figure.op.common.domain.R;
import com.figure.op.videoplay.domain.vo.HistoryThermometry;
import com.figure.op.videoplay.service.PlayBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author sdt
 * @version 1.0
 * @description:热感摄像回看相关接口
 * @date 2023/9/19 10:05
 */

@RestController
@Api(tags = "热感相机回看")
@RequestMapping("/playback")
public class PlayBackController {

    @Resource
    private PlayBackService playBackService;

    @PostMapping("history_thermometry")
    @ApiOperation("历史温度")
    public R historyThermometry(Integer cameramaId, String startTime, String endTime) {
        List<HistoryThermometry> list = playBackService.historyThermometry(cameramaId, startTime, endTime);
        return R.ok(list);
    }

    @PostMapping("getVideo")
    @ApiOperation("获取回放视频文件m3u8")
    public R getVidedo(Integer cameramaId, String startTime, String endTime, HttpServletResponse response) {
         //获取当前时间
//        throw new RuntimeException("错误");
        return playBackService.getM3u8FIle(cameramaId, startTime, endTime, response);

    }

    @PostMapping("test")
    @ApiOperation("删除")
    public void test(Integer key){
        playBackService.stopAndDeleteDir(key);
    }



}
