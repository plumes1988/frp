package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.SpectrumCollectServer;
import com.figure.core.service.device.ISpectrumCollectServerService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 频谱采集服务管理表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-08-24
 */
@RestController
@RequestMapping("/spectrumCollectServer")
public class SpectrumCollectServerController extends BaseController {

    @Resource
    private ISpectrumCollectServerService spectrumCollectServerService;

    /**
     * 根据serverId获取网管采集服务
     *
     * @param serverId 网管采集服务ID
     * @return 网管采集服务
     */
    @GetMapping("/get/{serverId}")
    @ApiOperation(value = "根据serverId获取网管采集服务", notes = "根据serverId获取网管采集服务")
    public SpectrumCollectServer selectSpectrumCollectServerById(@PathVariable("serverId") Long serverId) {
        return spectrumCollectServerService.getById(serverId);
    }

    /**
     * 查询网管采集服务列表
     *
     * @param request
     * @return 网管采集服务集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询网管采集服务列表", notes = "查询网管采集服务列表")
    public TableDataInfo<SpectrumCollectServer> selectSpectrumCollectServerList(HttpServletRequest request) throws Exception {
        PageWrapper<SpectrumCollectServer> pageWrapper = new PageHelper(SpectrumCollectServer.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<SpectrumCollectServer> pages = spectrumCollectServerService.page(pageWrapper.getPage(), queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存网管采集服务
     *
     * @param spectrumCollectServer 网管采集服务
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存网管采集服务", notes = "新增保存网管采集服务")
    public Map<String, Object> insertSpectrumCollectServer(@RequestBody SpectrumCollectServer spectrumCollectServer) {
        spectrumCollectServerService.save(spectrumCollectServer);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",spectrumCollectServer.getId());
        return result;
    }

    /**
     * 修改保存网管采集服务
     *
     * @param spectrumCollectServer 网管采集服务
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存网管采集服务", notes = "修改保存网管采集服务")
    public Map<String, Object> updateSpectrumCollectServerById(@RequestBody SpectrumCollectServer spectrumCollectServer) {
        return returnMap(spectrumCollectServerService.updateById(spectrumCollectServer));
    }

    /**
     * 批量删除网管采集服务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除网管采集服务", notes = "删除网管采集服务")
    public Map<String, Object> removeSpectrumCollectServerByIds(String ids) {
        return returnMap(spectrumCollectServerService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
