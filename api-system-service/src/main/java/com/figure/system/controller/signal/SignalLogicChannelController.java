package com.figure.system.controller.signal;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.signal.SignalLogicChannel;
import com.figure.core.model.signal.SignalSourceMonitor;
import com.figure.core.model.sys.SysLogicChannelChainidRel;
import com.figure.core.model.sys.SysRoleFrontStationRel;
import com.figure.core.model.sys.SysRoleInfo;
import com.figure.core.service.signal.ISignalLogicChannelService;
import com.figure.core.service.signal.ISignalSourceMonitorService;
import com.figure.core.service.sys.ISysLogicChannelChainidRelService;
import com.figure.core.service.sys.ISysRoleFrontStationRelService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 逻辑频道管理 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/signalLogicChannel")
public class SignalLogicChannelController {
    @Autowired
    ISignalLogicChannelService signalLogicChannelService;

    @Resource
    private ISysLogicChannelChainidRelService sysLogicChannelChainidRelService;

    @Autowired
    ISignalSourceMonitorService signalSourceMonitorService;

    /**
     * 查询逻辑频道列表
     *
     * @param request http请求
     * @return 逻辑频道集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑频道列表" , notes = "查询逻辑频道列表")
    public TableDataInfo selectSignalLogicChannelList(HttpServletRequest request) throws Exception {
        PageWrapper<SignalLogicChannel> pageWrapper =  new PageHelper(SignalLogicChannel.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();


        String channelCodes_not_in =  request.getParameter("channelCodes_not_in");

        if(org.apache.commons.lang.StringUtils.isNotEmpty(channelCodes_not_in)){
            String[] channelCodes =  channelCodes_not_in.split(",");
            queryWrapper.notIn("channelCode",channelCodes);
        }
        IPage<SignalLogicChannel> pages = signalLogicChannelService.page(pageWrapper.getPage(),queryWrapper);

        List<SignalSourceMonitor>  signalSourceMonitors = signalSourceMonitorService.list();

        Map<String,SignalSourceMonitor> signalSourceMonitors_map = new HashMap<>();

        for(SignalSourceMonitor signalSourceMonitor:signalSourceMonitors){
            signalSourceMonitors_map.put(signalSourceMonitor.getLogicChannelCode(),signalSourceMonitor);
        }


        for(SignalLogicChannel item: pages.getRecords()){
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("logicChannelCode",item.getChannelCode());
            List<SysLogicChannelChainidRel> rels = sysLogicChannelChainidRelService.list(queryWrapper);
            List<Integer> chainIds = rels.stream().map(SysLogicChannelChainidRel::getChainId).collect(Collectors.toList());
            item.setChainIds(chainIds);
            item.setSignalSourceMonitor(signalSourceMonitors_map.get(item.getChannelCode()));
        }

        return new TableDataInfo(pages);
    }


    /**
     * 新增保存逻辑频道
     *
     * @param signalLogicChannel 逻辑频道
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑频道" , notes = "新增保存逻辑频道")
    public Map<String,Object> insertSignalLogicChannel(@RequestBody SignalLogicChannel signalLogicChannel) {
        signalLogicChannelService.save(signalLogicChannel);
        saveRels(signalLogicChannel);
        Map<String,Object> result = new HashMap<>();
        result.put("status", 1);
        result.put("pk", signalLogicChannel.getChannelCode());
        return result;
    }


    /**
     * 修改保存逻辑频道
     *
     * @param signalLogicChannel 逻辑频道
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑频道" , notes = "修改保存逻辑频道")
    public Map<String,Object> updateSignalLogicChannelById(@RequestBody SignalLogicChannel signalLogicChannel) {
        signalLogicChannelService.updateById(signalLogicChannel);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("logicChannelCode",signalLogicChannel.getChannelCode());
        sysLogicChannelChainidRelService.remove(queryWrapper);
        saveRels(signalLogicChannel);
        Map<String,Object> result = new HashMap<>();
        result.put("status", 1);
        result.put("pk", signalLogicChannel.getChannelCode());
        return result;
    }

    private void saveRels(SignalLogicChannel signalLogicChannel){
        List<Integer> chainIds = signalLogicChannel.getChainIds();
        for (Integer chainId : chainIds){
            SysLogicChannelChainidRel sysLogicChannelChainidRel = new SysLogicChannelChainidRel();
            sysLogicChannelChainidRel.setChainId(chainId);
            sysLogicChannelChainidRel.setLogicChannelCode(signalLogicChannel.getChannelCode());
            sysLogicChannelChainidRelService.save(sysLogicChannelChainidRel);
        }
    }

    /**
     * 批量删除逻辑频道
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑频道" , notes = "删除逻辑频道")
    public boolean removeSignalLogicChannelByIds(String ids) {
        List<String> idList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids));
        List<SignalLogicChannel> list = signalLogicChannelService.listByIds(idList);
        QueryWrapper queryWrapper = new QueryWrapper();
        List signalSourceMonitorIds = new ArrayList();
        for (SignalLogicChannel signalLogicChannel:list){
           String channelCode = signalLogicChannel.getChannelCode();
           queryWrapper.eq("logicChannelCode",channelCode);
           List<SignalSourceMonitor> lst = signalSourceMonitorService.list(queryWrapper);
           if(lst.size()>0){
               signalSourceMonitorIds.add(lst.get(0).getId());
           }
        }
        signalSourceMonitorService.removeByIds(signalSourceMonitorIds);
        return signalLogicChannelService.removeByIds(idList);
    }
}
