package com.figure.system.controller.notice;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.notice.NoticeMessage;
import com.figure.core.model.notice.NoticeStruct;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.service.notice.INoticeMessageService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.core.redis.RedisConstants.NOTICE_STRUCT;

/**
 * <p>
 * 通知消息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-12-08
 */
@Controller
@RestController
@RequestMapping("/noticeMessage")
public class NoticeMessageController extends BaseController {

    @Resource
    private INoticeMessageService noticeMessageService;

    @Resource
    private IRedisTemplateService redisTemplateService;

    /**
     * 根据id获取通知消息
     *
     * @param id 媒介ID
     * @return 通知消息
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取通知消息", notes = "根据id获取通知消息")
    public NoticeMessage selectNoticeMessageById(@PathVariable("id") Long id) {
        return noticeMessageService.getById(id);
    }

    /**
     * 查询通知消息列表
     *
     * @param request
     * @return 通知消息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询通知消息列表", notes = "查询通知消息列表")
    public TableDataInfo<NoticeMessage> selectNoticeMessageList(HttpServletRequest request) throws Exception {
        PageWrapper<NoticeMessage> pageWrapper = new PageHelper(NoticeMessage.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        queryWrapper.orderByDesc("sourceHappendTime");
        IPage<NoticeMessage> pages = noticeMessageService.page(pageWrapper.getPage(), queryWrapper);
        noticeMessageService.fillEntityProps(pages.getRecords());
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存通知消息
     *
     * @param noticeMessage 通知消息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存通知消息", notes = "新增保存通知消息")
    public Map<String, Object> insertNoticeMessage(@RequestBody NoticeMessage noticeMessage) {
        noticeMessageService.save(noticeMessage);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",noticeMessage.getMessageId());
        return result;
    }

    /**
     * 修改保存通知消息
     *
     * @param noticeMessage 通知消息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存通知消息", notes = "修改保存通知消息")
    public Map<String, Object> updateNoticeMessageById(@RequestBody NoticeMessage noticeMessage) {
        return returnMap(noticeMessageService.updateById(noticeMessage));
    }

    /**
     * 批量删除通知消息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除通知消息", notes = "删除通知消息")
    public Map<String, Object> removeNoticeMessageByIds(String ids) {
        return returnMap(noticeMessageService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }


    /**
     * 批量重发通知消息
     *
     * @param ids 需要重发的数据ID
     * @return 结果
     */
    @PostMapping("/send")
    @ApiOperation(value = "重发通知消息", notes = "重发通知消息")
    public Map<String, Object> sendNoticeMessageByIds(String ids) {
        String[] idList = org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids);
        //todo
        List<NoticeMessage> noticeMessages = noticeMessageService.listByIds(Arrays.asList(idList));
        for(NoticeMessage noticeMessage:noticeMessages){
            noticeMessage.setTopic("notice_message");
            SseEmitterManager.sendMessageToAllMatchSseEmitter(noticeMessage);
        }
        return returnMap(true,"重发成功！");
    }


    @GetMapping("/getNoticeStructList")
    public List<NoticeStruct> getNoticeStructList() {
        List<NoticeStruct> noticeStructList = redisTemplateService.getListRedisCache(NOTICE_STRUCT, NoticeStruct.class);
        return noticeStructList;
    }

}
