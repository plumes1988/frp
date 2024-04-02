package com.figure.system.controller.notice;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.notice.NoticeMessageTemplete;
import com.figure.core.service.notice.INoticeMessageTempleteService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 通知模板 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/noticeMessageTemplete")
public class NoticeMessageTempleteController extends BaseController {

    @Resource
    private INoticeMessageTempleteService noticeMessageTempleteService;

    /**
     * 根据templeteId获取通知模板
     *
     * @param templeteId 媒介ID
     * @return 通知模板
     */
    @GetMapping("/get/{templeteId}")
    @ApiOperation(value = "根据templeteId获取通知模板", notes = "根据templeteId获取通知模板")
    public NoticeMessageTemplete selectNoticeMessageTempleteById(@PathVariable("templeteId") Long templeteId) {
        return noticeMessageTempleteService.getById(templeteId);
    }

    /**
     * 查询通知模板列表
     *
     * @param request
     * @return 通知模板集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询通知模板列表", notes = "查询通知模板列表")
    public TableDataInfo<NoticeMessageTemplete> selectNoticeMessageTempleteList(HttpServletRequest request) throws Exception {
        PageWrapper<NoticeMessageTemplete> pageWrapper = new PageHelper(NoticeMessageTemplete.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<NoticeMessageTemplete> pages = noticeMessageTempleteService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存通知模板
     *
     * @param noticeMessageTemplete 通知模板
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存通知模板", notes = "新增保存通知模板")
    public Map<String, Object> insertNoticeMessageTemplete(@RequestBody NoticeMessageTemplete noticeMessageTemplete) {
        noticeMessageTempleteService.save(noticeMessageTemplete);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",noticeMessageTemplete.getTempleteId());
        return result;
    }

    /**
     * 修改保存通知模板
     *
     * @param noticeMessageTemplete 通知模板
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存通知模板", notes = "修改保存通知模板")
    public Map<String, Object> updateNoticeMessageTempleteById(@RequestBody NoticeMessageTemplete noticeMessageTemplete) {
        return returnMap(noticeMessageTempleteService.updateById(noticeMessageTemplete));
    }

    /**
     * 批量删除通知模板
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除通知模板", notes = "删除通知模板")
    public Map<String, Object> removeNoticeMessageTempleteByIds(String ids) {
        return returnMap(noticeMessageTempleteService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
