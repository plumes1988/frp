package com.figure.system.controller.notice;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.notice.NoticeUserGroup;
import com.figure.core.service.notice.INoticeUserGroupService;
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
 * 用户组管理 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-08-30
 */
@RestController
@RequestMapping("/noticeUserGroup")
public class NoticeUserGroupController extends BaseController {

    @Resource
    private INoticeUserGroupService noticeUserGroupService;

    /**
     * 根据groupId获取用户组管理
     *
     * @param groupId 用户组ID
     * @return 用户组管理
     */
    @GetMapping("/get/{groupId}")
    @ApiOperation(value = "根据groupId获取用户组管理", notes = "根据groupId获取用户组管理")
    public NoticeUserGroup selectNoticeUserGroupById(@PathVariable("groupId") Long groupId) {
        return noticeUserGroupService.getById(groupId);
    }

    /**
     * 查询用户组管理列表
     *
     * @param request
     * @return 用户组管理集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询用户组管理列表", notes = "查询用户组管理列表")
    public TableDataInfo<NoticeUserGroup> selectNoticeUserGroupList(HttpServletRequest request) throws Exception {
        PageWrapper<NoticeUserGroup> pageWrapper = new PageHelper(NoticeUserGroup.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<NoticeUserGroup> pages = noticeUserGroupService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存用户组管理
     *
     * @param noticeUserGroup 用户组管理
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存用户组管理", notes = "新增保存用户组管理")
    public Map<String, Object> insertNoticeUserGroup(@RequestBody NoticeUserGroup noticeUserGroup) {
        noticeUserGroupService.save(noticeUserGroup);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",noticeUserGroup.getGroupId());
        return result;
    }

    /**
     * 修改保存用户组管理
     *
     * @param noticeUserGroup 用户组管理
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存用户组管理", notes = "修改保存用户组管理")
    public Map<String, Object> updateNoticeUserGroupById(@RequestBody NoticeUserGroup noticeUserGroup) {
        return returnMap(noticeUserGroupService.updateById(noticeUserGroup),noticeUserGroup.getGroupId());
    }

    /**
     * 批量删除用户组管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除用户组管理", notes = "删除用户组管理")
    public Map<String, Object> removeNoticeUserGroupByIds(String ids) {
        return returnMap(noticeUserGroupService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
