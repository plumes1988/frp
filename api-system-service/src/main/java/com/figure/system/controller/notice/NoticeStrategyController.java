package com.figure.system.controller.notice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.notice.NoticeStrategy;
import com.figure.core.service.notice.INoticeStrategyService;
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
 * 通知策略管理 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-08-31
 */
@RestController
@RequestMapping("/noticeStrategy")
public class NoticeStrategyController extends BaseController {

    @Resource
    private INoticeStrategyService noticeStrategyService;

    /**
     * 根据strategyId获取通知策略管理
     *
     * @param strategyId 通知策略ID
     * @return 通知策略管理
     */
    @GetMapping("/get/{strategyId}")
    @ApiOperation(value = "根据strategyId获取通知策略管理", notes = "根据strategyId获取通知策略管理")
    public NoticeStrategy selectNoticeStrategyById(@PathVariable("strategyId") Long strategyId) {
        return noticeStrategyService.getById(strategyId);
    }

    /**
     * 查询通知策略管理列表
     *
     * @param request
     * @return 通知策略管理集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询通知策略管理列表", notes = "查询通知策略管理列表")
    public TableDataInfo<NoticeStrategy> selectNoticeStrategyList(HttpServletRequest request) throws Exception {
        PageWrapper<NoticeStrategy> pageWrapper = new PageHelper(NoticeStrategy.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<NoticeStrategy> pages = noticeStrategyService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存通知策略管理
     *
     * @param noticeStrategy 通知策略管理
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存通知策略管理", notes = "新增保存通知策略管理")
    public Map<String, Object> insertNoticeStrategy(@RequestBody NoticeStrategy noticeStrategy) {
        noticeStrategyService.save(noticeStrategy);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",noticeStrategy.getStrategyId());
        return result;
    }

    /**
     * 修改保存通知策略管理
     *
     * @param noticeStrategy 通知策略管理
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存通知策略管理", notes = "修改保存通知策略管理")
    public Map<String, Object> updateNoticeStrategyById(@RequestBody NoticeStrategy noticeStrategy) {
        return returnMap(noticeStrategyService.updateById(noticeStrategy),noticeStrategy.getStrategyId());
    }

    /**
     * 批量删除通知策略管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除通知策略管理", notes = "删除通知策略管理")
    public Map<String, Object> removeNoticeStrategyByIds(String ids) {
        return returnMap(noticeStrategyService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
