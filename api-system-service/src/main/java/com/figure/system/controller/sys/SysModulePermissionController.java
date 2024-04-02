package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.sys.SysModuleInfo;
import com.figure.core.model.sys.SysModulePermission;
import com.figure.core.service.sys.ISysModuleInfoService;
import com.figure.core.service.sys.ISysModulePermissionService;
import com.figure.core.util.page.TableDataInfo;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.core.constant.Constants.DATATABLE_ISENABLE_ENABLED;
import static com.figure.core.model.sys.SysModulePermission.*;

/**
 * <p>
 * 模块权限项 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-12-13
 */
@RestController
@RequestMapping("/sysModulePermission")
public class SysModulePermissionController extends BaseController {

    @Resource
    private ISysModulePermissionService sysModulePermissionService;

    @Autowired
    ISysModuleInfoService sysModuleInfoService;

    /**
     * 根据id获取模块权限项
     *
     * @param id 模块权限项 ID
     * @return 模块权限项
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取模块权限项", notes = "根据id获取模块权限项")
    public SysModulePermission selectSysModulePermissionById(@PathVariable("id") Long id) {
        return sysModulePermissionService.getById(id);
    }

    /**
     * 查询模块权限项列表
     *
     * @param request
     * @return 模块权限项集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询模块权限项列表", notes = "查询模块权限项列表")
    public TableDataInfo<SysModulePermission> selectSysModulePermissionList(HttpServletRequest request) throws Exception {
        PageWrapper<SysModulePermission> pageWrapper = new PageHelper(SysModulePermission.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<SysModulePermission> pages = sysModulePermissionService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存模块权限项
     *
     * @param sysModulePermission 模块权限项
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存模块权限项", notes = "新增保存模块权限项")
    public Map<String, Object> insertSysModulePermission(@RequestBody SysModulePermission sysModulePermission) {
        sysModulePermissionService.save(sysModulePermission);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysModulePermission.getId());
        return result;
    }

    /**
     * 修改保存模块权限项
     *
     * @param sysModulePermission 模块权限项
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存模块权限项", notes = "修改保存模块权限项")
    public Map<String, Object> updateSysModulePermissionById(@RequestBody SysModulePermission sysModulePermission) {
        return returnMap(sysModulePermissionService.updateById(sysModulePermission));
    }

    /**
     * 批量删除第三方调用key
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除第三方调用key", notes = "删除第三方调用key")
    public Map<String, Object> removeSysModulePermissionByIds(String ids) {
        return returnMap(sysModulePermissionService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

    /**
     * 排序模块权限项
     *
     * @param sysModulePermissions_str 排序模块权限项
     * @return 结果
     */
    @PostMapping("/sort")
    @ApiOperation(value = "排序模块权限项", notes = "排序模块权限项")
    public Map<String, Object> sortSysModulePermissions(String sysModulePermissions_str) {
        SysModulePermission[] sysModulePermissions = new Gson().fromJson(sysModulePermissions_str, SysModulePermission[].class);
        if(sysModulePermissions.length>0){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("moduleId",sysModulePermissions[0].getModuleId());
            sysModulePermissionService.remove(queryWrapper);
            sysModulePermissionService.saveBatch(Arrays.asList(sysModulePermissions));
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        return result;
    }


    /**
     * 一键维护基础权限
     *
     * @return 结果
     */
    @PostMapping("/updateBaseSysModulePermissions")
    @ApiOperation(value = "一键维护基础权限", notes = "一键维护基础权限")
    public Map<String, Object> sortSysModulePermissions() {
        QueryWrapper<SysModuleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isEnable",DATATABLE_ISENABLE_ENABLED).eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);
        queryWrapper.isNotNull("moduleURL");
        List<SysModuleInfo> sysModuleInfos = sysModuleInfoService.list(queryWrapper);
        for (SysModuleInfo sysModuleInfo: sysModuleInfos) {
            for (int i = 0; i <BASE_PERMISSION_CODES.length; i++) {
                QueryWrapper<SysModulePermission> queryWrapper01 = new QueryWrapper<>();
                queryWrapper01.eq("moduleId",sysModuleInfo.getId());
                queryWrapper01.eq("permissionCode",BASE_PERMISSION_CODES[i]);
                SysModulePermission sysModulePermission = new SysModulePermission();
                sysModulePermission.setPermissionCode(BASE_PERMISSION_CODES[i]);
                sysModulePermission.setModuleId(sysModuleInfo.getId());
                sysModulePermission.setPermissionName(BASE_PERMISSION_NAMES[i]);
                sysModulePermission.setMark(BASE_PERMISSION_DESES[i]);
                sysModulePermissionService.saveOrUpdate(sysModulePermission,queryWrapper01);
            }
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        return result;
    }

}
