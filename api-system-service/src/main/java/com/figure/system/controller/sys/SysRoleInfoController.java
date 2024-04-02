package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.figure.core.base.BaseController;
import com.figure.core.model.sys.*;
import com.figure.core.service.sys.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-03-18 16:07:45
 */
@RestController
@RequestMapping("/sysRoleInfo")
@Api(value = "角色信息相关接口" , tags = "角色信息相关接口")
public class SysRoleInfoController extends BaseController {

    @Resource
    private ISysRoleInfoService sysRoleInfoService;

    @Resource
    private ISysRoleFrontStationRelService sysRoleFrontStationRelService;

    @Resource
    private ISysRoleLogicChannelRelService sysRoleLogicChannelRelService;

    @Resource
    private ISysRoleDeviceRelService sysRoleDeviceRelService;

    @Resource
    private ISysRolePermissionRelService sysRolePermissionRelService;

    /**
     * 根据roleid获取角色信息
     *
     * @param roleId 角色信息ID
     * @return 角色信息
     */
    @GetMapping("/get/{roleid}")
    @ApiOperation(value = "根据roleid获取角色信息" , notes = "根据roleid获取角色信息")
    public SysRoleInfo selectSysRoleInfoById(@PathVariable("roleid") Long roleId) {
        return sysRoleInfoService.getById(roleId);
    }

    /**
     * 查询角色信息列表
     *
     * @param sysRoleInfo 角色信息
     * @return 角色信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询角色信息列表" , notes = "查询角色信息列表")
    public List<SysRoleInfo> selectSysRoleInfoList(SysRoleInfo sysRoleInfo) {
        //1.直接获取响内容
        List<SysRoleInfo> list= sysRoleInfoService.list();
        for(SysRoleInfo item: list){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("roleId",item.getRoleId());
            List<SysRoleFrontStationRel> rels = sysRoleFrontStationRelService.list(queryWrapper);
            List<Integer> frontIds = rels.stream().map(SysRoleFrontStationRel::getFrontId).collect(Collectors.toList());
            item.setFrontIds(frontIds);

            List<SysRoleLogicChannelRel> rels_01 = sysRoleLogicChannelRelService.list(queryWrapper);
            List<String> logicChannelCodes = rels_01.stream().map(SysRoleLogicChannelRel::getLogicChannelCode).collect(Collectors.toList());
            item.setLogicChannelCodes(logicChannelCodes);

            queryWrapper.eq("controllable",1);
            rels = sysRoleFrontStationRelService.list(queryWrapper);
            frontIds = rels.stream().map(SysRoleFrontStationRel::getFrontId).collect(Collectors.toList());
            item.setControllableFrontIds(frontIds);
        }
        return list;
    }

    /**
     * 新增保存角色信息
     *
     * @param sysRoleInfo 角色信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存角色信息" , notes = "新增保存角色信息")
    public Map<String,Object> insertSysRoleInfo(@RequestBody SysRoleInfo sysRoleInfo) {
        sysRoleInfoService.save(sysRoleInfo);
        saveFrontStationRels(sysRoleInfo);
        saveLogicChannelRels(sysRoleInfo);
        saveDeviceRels(sysRoleInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysRoleInfo.getRoleId());
        return result;
    }

    /**
     * 修改保存角色信息
     *
     * @param sysRoleInfo 角色信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存角色信息" , notes = "修改保存角色信息")
    public Map<String,Object> updateSysRoleInfoById(@RequestBody SysRoleInfo sysRoleInfo) {
        sysRoleInfoService.updateById(sysRoleInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysRoleInfo.getRoleId());
        return result;
    }

    /**
     * 保存角色关联前端站点
     *
     * @param sysRoleInfo 角色关联前端站点
     * @return 结果
     */
    @PostMapping("/saveFrontStationRels")
    @ApiOperation(value = "保存角色关联前端站点信息" , notes = "保存角色关联前端站点信息")
    public Map<String,Object> saveFrontStationRels(@RequestBody SysRoleInfo sysRoleInfo){
        Integer controllable = sysRoleInfo.getControllable();
        List<Integer> frontIds = sysRoleInfo.getFrontIds();
        if(controllable==0){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("roleId",sysRoleInfo.getRoleId());
            sysRoleFrontStationRelService.remove(queryWrapper);
            for (Integer frontId : frontIds){
                SysRoleFrontStationRel sysRoleFrontStationRel = new SysRoleFrontStationRel();
                sysRoleFrontStationRel.setRoleId(sysRoleInfo.getRoleId().intValue());
                sysRoleFrontStationRel.setFrontId(frontId);
                sysRoleFrontStationRel.setControllable(controllable);
                sysRoleFrontStationRelService.save(sysRoleFrontStationRel);
            }
        }else{
            if(frontIds.size()==0){
                frontIds.add(-1);
            }
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("roleId",sysRoleInfo.getRoleId());
            updateWrapper.notIn("frontId",frontIds);
            updateWrapper.set("controllable",0);
            sysRoleFrontStationRelService.update(updateWrapper);

            updateWrapper = new UpdateWrapper();
            updateWrapper.eq("roleId",sysRoleInfo.getRoleId());
            updateWrapper.in("frontId",frontIds);
            updateWrapper.set("controllable",1);
            sysRoleFrontStationRelService.update(updateWrapper);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysRoleInfo.getRoleId());
        return result;
    }

    /**
     * 保存角色关联播出系统
     *
     * @param sysRoleInfo 角色关联播出系统
     * @return 结果
     */
    @PostMapping("/saveLogicChannelRels")
    @ApiOperation(value = "保存角色关联播出系统信息" , notes = "保存角色关联播出系统信息")
    public Map<String,Object> saveLogicChannelRels(@RequestBody SysRoleInfo sysRoleInfo){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("roleId",sysRoleInfo.getRoleId());
        sysRoleLogicChannelRelService.remove(queryWrapper);
        List<String> logicChannelCodes = sysRoleInfo.getLogicChannelCodes();
        for (String logicChannelCode : logicChannelCodes){
            SysRoleLogicChannelRel sysRoleLogicChannelRel = new SysRoleLogicChannelRel();
            sysRoleLogicChannelRel.setRoleId(sysRoleInfo.getRoleId().intValue());
            sysRoleLogicChannelRel.setLogicChannelCode(logicChannelCode);
            sysRoleLogicChannelRelService.save(sysRoleLogicChannelRel);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysRoleInfo.getRoleId());
        return result;
    }


    /**
     * 保存角色关联设备
     *
     * @param sysRoleInfo 角色关联设备
     * @return 结果
     */
    @PostMapping("/saveDeviceRels")
    @ApiOperation(value = "保存角色关联设备信息" , notes = "保存角色关联设备信息")
    public Map<String,Object> saveDeviceRels(@RequestBody SysRoleInfo sysRoleInfo){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("roleId",sysRoleInfo.getRoleId());
        sysRoleDeviceRelService.remove(queryWrapper);
        List<Integer> deviceIds = sysRoleInfo.getDeviceIds();
        for (Integer deviceId : deviceIds){
            SysRoleDeviceRel sysRoleDeviceRel = new SysRoleDeviceRel();
            sysRoleDeviceRel.setRoleId(sysRoleInfo.getRoleId().intValue());
            sysRoleDeviceRel.setDeviceId(deviceId);
            sysRoleDeviceRelService.save(sysRoleDeviceRel);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysRoleInfo.getRoleId());
        return result;
    }



    /**
     * 保存角色权限
     *
     * @param sysRoleInfo 角色权限
     * @return 结果
     */
    @PostMapping("/savePermissionRels")
    @ApiOperation(value = "保存角色权限信息" , notes = "保存角色权限信息")
    public Map<String,Object> savePermissionRels(@RequestBody SysRoleInfo sysRoleInfo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("roleId",sysRoleInfo.getRoleId());
        sysRolePermissionRelService.remove(queryWrapper);
        List<Integer> permissionIds = sysRoleInfo.getPermissionIds();
        for (Integer permissionId : permissionIds){
            SysRolePermissionRel sysRolePermissionRel = new SysRolePermissionRel();
            sysRolePermissionRel.setRoleId(sysRoleInfo.getRoleId().intValue());
            sysRolePermissionRel.setPermissionId(permissionId);
            sysRolePermissionRelService.save(sysRolePermissionRel);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysRoleInfo.getRoleId());
        return result;
    }

    /**
     * 批量删除角色信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除角色信息" , notes = "删除角色信息")
    public boolean removeSysRoleInfoByIds(String ids) {
        return sysRoleInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }

}