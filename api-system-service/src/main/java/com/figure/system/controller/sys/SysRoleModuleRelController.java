package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.model.sys.SysRoleModuleRel;
import com.figure.core.service.sys.ISysRoleModuleRelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色模块关系 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-03-18 16:07:45
 */
@RestController
@RequestMapping("/sysRoleModuleRel")
@Api(value = "角色模块关系相关接口" , tags = "角色模块关系相关接口")
public class SysRoleModuleRelController{

    @Resource
    private ISysRoleModuleRelService sysRoleModuleRelService;

    /**
     * 根据roleid获取角色模块关系
     *
     * @param roleId 角色模块关系ID
     * @return 角色模块关系
     */
    @GetMapping("/get/{roleId}")
    @ApiOperation(value = "根据roleid获取角色模块关系" , notes = "根据roleid获取角色模块关系")
    public List<SysRoleModuleRel> selectSysRoleModuleRelById(@PathVariable("roleId") Long roleId) {
        QueryWrapper<SysRoleModuleRel> queryWrapper =  new QueryWrapper<SysRoleModuleRel>();
        queryWrapper.eq("roleId",roleId);
        return sysRoleModuleRelService.list(queryWrapper);
    }

    /**
     * 查询角色模块关系列表
     *
     * @param sysRoleModuleRel 角色模块关系
     * @return 角色模块关系集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询角色模块关系列表" , notes = "查询角色模块关系列表")
    public List<SysRoleModuleRel> selectSysRoleModuleRelList(SysRoleModuleRel sysRoleModuleRel) {
        return sysRoleModuleRelService.list();
    }

    /**
     * 新增保存角色模块关系
     *
     * @param sysRoleModuleRel 角色模块关系
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存角色模块关系" , notes = "新增保存角色模块关系")
    public boolean insertSysRoleModuleRel(@RequestBody SysRoleModuleRel sysRoleModuleRel) {
        String moduleids = sysRoleModuleRel.getModuleids();
        String[] moduleIds = moduleids.split(",");
        String permissions = sysRoleModuleRel.getPermissions();
        String[] permissionArr = permissions.split(",");
        QueryWrapper<SysRoleModuleRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("roleId",sysRoleModuleRel.getRoleId());
        sysRoleModuleRelService.remove(queryWrapper);
        int index = 0;
        for (String moduleId:moduleIds){
            SysRoleModuleRel rel = new SysRoleModuleRel();
            rel.setRoleId(sysRoleModuleRel.getRoleId());
            rel.setModuleId(Long.parseLong(moduleId));
            rel.setPermission(Integer.parseInt(permissionArr[index]));
            sysRoleModuleRelService.save(rel);
            index++;
        }
        return true;
    }

    /**
     * 修改保存角色模块关系
     *
     * @param sysRoleModuleRel 角色模块关系
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存角色模块关系" , notes = "修改保存角色模块关系")
    public boolean updateSysRoleModuleRelById(@RequestBody SysRoleModuleRel sysRoleModuleRel) {
        return sysRoleModuleRelService.updateById(sysRoleModuleRel);
    }

    /**
     * 批量删除角色模块关系
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除角色模块关系" , notes = "删除角色模块关系")
    public boolean removeSysRoleModuleRelByIds(String ids) {
        return sysRoleModuleRelService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }

}