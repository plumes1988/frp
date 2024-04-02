package com.figure.system.controller.sys;

import com.figure.core.model.sys.SysUserRoleRel;
import com.figure.core.service.sys.ISysUserRoleRelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户角色关键 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-03-18 16:07:45
 */
@RestController
@RequestMapping("/sysUserRoleRel")
@Api(value = "用户角色关键相关接口" , tags = "用户角色关键相关接口")
public class SysUserRoleRelController{

    @Resource
    private ISysUserRoleRelService sysUserRoleRelService;

    /**
     * 根据userid获取用户角色关键
     *
     * @param userId 用户角色关键ID
     * @return 用户角色关键
     */
    @GetMapping("/get/{userId}")
    @ApiOperation(value = "根据userid获取用户角色关键" , notes = "根据userId获取用户角色关键")
    public SysUserRoleRel selectSysUserRoleRelById(@PathVariable("userId") Long userId) {
        return sysUserRoleRelService.getById(userId);
    }

    /**
     * 查询用户角色关键列表
     *
     * @param sysUserRoleRel 用户角色关键
     * @return 用户角色关键集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询用户角色关键列表" , notes = "查询用户角色关键列表")
    public List<SysUserRoleRel> selectSysUserRoleRelList(SysUserRoleRel sysUserRoleRel) {
        return sysUserRoleRelService.list();
    }

    /**
     * 新增保存用户角色关键
     *
     * @param sysUserRoleRel 用户角色关键
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存用户角色关键" , notes = "新增保存用户角色关键")
    public boolean insertSysUserRoleRel(@RequestBody SysUserRoleRel sysUserRoleRel) {
        return sysUserRoleRelService.save(sysUserRoleRel);
    }

    /**
     * 修改保存用户角色关键
     *
     * @param sysUserRoleRel 用户角色关键
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存用户角色关键" , notes = "修改保存用户角色关键")
    public boolean updateSysUserRoleRelById(@RequestBody SysUserRoleRel sysUserRoleRel) {
        return sysUserRoleRelService.updateById(sysUserRoleRel);
    }

    /**
     * 批量删除用户角色关键
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除用户角色关键" , notes = "删除用户角色关键")
    public boolean removeSysUserRoleRelByIds(String ids) {
        return sysUserRoleRelService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }

}