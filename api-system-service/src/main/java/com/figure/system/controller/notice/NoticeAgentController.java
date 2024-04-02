package com.figure.system.controller.notice;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.notice.NoticeAgent;
import com.figure.core.service.notice.INoticeAgentService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 通知媒介管理 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-08-31
 */
@RestController
@RequestMapping("/noticeAgent")
public class NoticeAgentController extends BaseController {

    @Resource
    private INoticeAgentService noticeAgentService;

    /**
     * 根据agentId获取媒介管理
     *
     * @param agentId 媒介ID
     * @return 媒介管理
     */
    @GetMapping("/get/{agentId}")
    @ApiOperation(value = "根据agentId获取媒介管理", notes = "根据agentId获取媒介管理")
    public NoticeAgent selectNoticeAgentById(@PathVariable("agentId") Long agentId) {
        return noticeAgentService.getById(agentId);
    }

    /**
     * 查询媒介管理列表
     *
     * @param request
     * @return 媒介管理集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询媒介管理列表", notes = "查询媒介管理列表")
    public TableDataInfo<NoticeAgent> selectNoticeAgentList(HttpServletRequest request) throws Exception {
        PageWrapper<NoticeAgent> pageWrapper = new PageHelper(NoticeAgent.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<NoticeAgent> pages = noticeAgentService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存媒介管理
     *
     * @param noticeAgent 媒介管理
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存媒介管理", notes = "新增保存媒介管理")
    public Map<String, Object> insertNoticeAgent(@RequestBody NoticeAgent noticeAgent) {
        noticeAgentService.save(noticeAgent);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",noticeAgent.getAgentId());
        return result;
    }

    /**
     * 修改保存媒介管理
     *
     * @param noticeAgent 媒介管理
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存媒介管理", notes = "修改保存媒介管理")
    public Map<String, Object> updateNoticeAgentById(@RequestBody NoticeAgent noticeAgent) {
        return returnMap(noticeAgentService.updateById(noticeAgent));
    }

    /**
     * 批量删除媒介管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除媒介管理", notes = "删除媒介管理")
    public Map<String, Object> removeNoticeAgentByIds(String ids) {
        return returnMap(noticeAgentService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}

