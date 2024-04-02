package com.figure.system.controller.notice;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.notice.NoticeAlarmTriggerRule;
import com.figure.core.service.notice.INoticeAlarmTriggerRuleService;
import com.figure.core.util.page.TableDataInfo;
import com.figure.system.eventBus.event.UpdateRedisCacheEvent;
import com.figure.system.eventBus.service.EventBusService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.figure.core.redis.RedisConstants.NOTICE_STRUCT;

/**
 * <p>
 * 报警触发通知规则 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/noticeAlarmTriggerRule")
public class NoticeAlarmTriggerRuleController extends BaseController {

    @Resource
    private INoticeAlarmTriggerRuleService noticeAlarmTriggerRuleService;

    @Resource
    protected EventBusService eventBusService;

    /**
     * 根据ruleId获取报警触发通知规则
     *
     * @param ruleId 媒介ID
     * @return 报警触发通知规则
     */
    @GetMapping("/get/{ruleId}")
    @ApiOperation(value = "根据ruleId获取报警触发通知规则", notes = "根据ruleId获取报警触发通知规则")
    public NoticeAlarmTriggerRule selectNoticeAlarmTriggerRuleById(@PathVariable("ruleId") Long ruleId) {
        return noticeAlarmTriggerRuleService.getById(ruleId);
    }

    /**
     * 查询报警触发通知规则列表
     *
     * @param request
     * @return 报警触发通知规则集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询报警触发通知规则列表", notes = "查询报警触发通知规则列表")
    public TableDataInfo<NoticeAlarmTriggerRule> selectNoticeAlarmTriggerRuleList(HttpServletRequest request) throws Exception {
        PageWrapper<NoticeAlarmTriggerRule> pageWrapper = new PageHelper(NoticeAlarmTriggerRule.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<NoticeAlarmTriggerRule> pages = noticeAlarmTriggerRuleService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存报警触发通知规则
     *
     * @param noticeAlarmTriggerRule 报警触发通知规则
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存报警触发通知规则", notes = "新增保存报警触发通知规则")
    public Map<String, Object> insertNoticeAlarmTriggerRule(@RequestBody NoticeAlarmTriggerRule noticeAlarmTriggerRule) {
        String settings =  JSON.toJSONString(noticeAlarmTriggerRule.getSettings_());
        noticeAlarmTriggerRule.setSettings(settings);
        noticeAlarmTriggerRuleService.save(noticeAlarmTriggerRule);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",noticeAlarmTriggerRule.getRuleId());
        eventBusService.postEvent(new UpdateRedisCacheEvent(NOTICE_STRUCT));
        return result;
    }

    /**
     * 修改保存报警触发通知规则
     *
     * @param noticeAlarmTriggerRule 报警触发通知规则
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存报警触发通知规则", notes = "修改保存报警触发通知规则")
    public Map<String, Object> updateNoticeAlarmTriggerRuleById(@RequestBody NoticeAlarmTriggerRule noticeAlarmTriggerRule) {
        String settings =  JSON.toJSONString(noticeAlarmTriggerRule.getSettings_());
        noticeAlarmTriggerRule.setSettings(settings);
        boolean result = noticeAlarmTriggerRuleService.updateById(noticeAlarmTriggerRule);
        eventBusService.postEvent(new UpdateRedisCacheEvent(NOTICE_STRUCT));
        return returnMap(result,noticeAlarmTriggerRule.getRuleId());
    }

    /**
     * 批量删除报警触发通知规则
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除报警触发通知规则", notes = "删除报警触发通知规则")
    public Map<String, Object> removeNoticeAlarmTriggerRuleByIds(String ids) {
        boolean result = noticeAlarmTriggerRuleService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids)));
        eventBusService.postEvent(new UpdateRedisCacheEvent(NOTICE_STRUCT));
        return returnMap(result);
    }

}
