package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.sys.SysSvgIcon;
import com.figure.core.service.sys.ISysSvgIconService;
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
 * svg图标 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-06-06
 */
@RestController
@RequestMapping("/sysSvgIcon")
public class SysSvgIconController extends BaseController {

    @Resource
    private ISysSvgIconService sysSvgIconService;

    /**
     * 根据id获取svg图标
     *
     * @param id 媒介ID
     * @return svg图标
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取svg图标", notes = "根据id获取svg图标")
    public SysSvgIcon selectSysSvgIconById(@PathVariable("id") Long id) {
        return sysSvgIconService.getById(id);
    }

    /**
     * 查询svg图标列表
     *
     * @param request
     * @return svg图标集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询svg图标列表", notes = "查询svg图标列表")
    public TableDataInfo<SysSvgIcon> selectSysSvgIconList(HttpServletRequest request) throws Exception {
        PageWrapper<SysSvgIcon> pageWrapper = new PageHelper(SysSvgIcon.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<SysSvgIcon> pages = sysSvgIconService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存svg图标
     *
     * @param sysSvgIcon svg图标
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存svg图标", notes = "新增保存svg图标")
    public Map<String, Object> insertSysSvgIcon(@RequestBody SysSvgIcon sysSvgIcon) {
        sysSvgIconService.save(sysSvgIcon);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysSvgIcon.getId());
        return result;
    }

    /**
     * 修改保存svg图标
     *
     * @param sysSvgIcon svg图标
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存svg图标", notes = "修改保存svg图标")
    public Map<String, Object> updateSysSvgIconById(@RequestBody SysSvgIcon sysSvgIcon) {
        return returnMap(sysSvgIconService.updateById(sysSvgIcon));
    }

    /**
     * 批量删除svg图标
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除svg图标", notes = "删除svg图标")
    public Map<String, Object> removeSysSvgIconByIds(String ids) {
        return returnMap(sysSvgIconService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
