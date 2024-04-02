package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.CodecHelper;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.service.sys.ISysUserInfoService;
import com.figure.core.service.sys.impl.AuthService;
import com.figure.core.util.page.TableDataInfo;
import com.google.common.eventbus.EventBus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static com.figure.core.constant.ConstantsForSysPara.INITIAL_PASSWORD;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-03-18 16:07:45
 */
@RestController
@RequestMapping("/sysUserInfo")
@Api(value = "用户信息相关接口" , tags = "用户信息相关接口")
public class SysUserInfoController extends BaseController {

    @Resource
    private ISysUserInfoService sysUserInfoService;

    @Autowired
    AuthService authService;

    @Autowired
    ISysParaService sysParaService;

    /**
     * 查询用户信息列表
     *
     * @param sysUserInfo 用户信息
     * @return 用户信息集合
     */
    @GetMapping("/query")
    @ApiOperation(value = "查询用户信息列表" , notes = "查询用户信息列表")
    public TableDataInfo<SysUserInfo> selectSysUserInfoList(SysUserInfo sysUserInfo, HttpServletRequest request) throws Exception {
        PageWrapper<SysUserInfo> pageWrapper =  new PageHelper(SysUserInfo.class).getPageWrapper(request);
        QueryWrapper<SysUserInfo> queryWrapper =  pageWrapper.getQueryWrapper();
        IPage<SysUserInfo> pages = sysUserInfoService.page(pageWrapper.getPage(),queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 根据userid获取用户信息
     *
     * @param userId 用户信息ID
     * @return 用户信息
     */
    @GetMapping("/get/{userId}")
    @ApiOperation(value = "根据userid获取用户信息" , notes = "根据userid获取用户信息")
    public SysUserInfo selectSysUserInfoById(@PathVariable("userId") Long userId) {
        SysUserInfo sysUserInfo = sysUserInfoService.getById(userId);
        sysUserInfoService.fetchRoleInfo(sysUserInfo);
        return sysUserInfoService.getById(userId);
    }

    /**
     * 查询用户信息列表
     *
     * @param sysUserInfo 用户信息
     * @return 用户信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询用户信息列表" , notes = "查询用户信息列表")
    public List<SysUserInfo> selectSysUserInfoList(SysUserInfo sysUserInfo) {
        List<SysUserInfo>  list = sysUserInfoService.list();
        for(SysUserInfo sui:list){
            sysUserInfoService.fetchRoleInfo(sui);
        }
        return list;
    }

    /**
     * 新增保存用户信息
     *
     * @param sysUserInfo 用户信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存用户信息" , notes = "新增保存用户信息")
    public boolean insertSysUserInfo(@RequestBody SysUserInfo sysUserInfo) {

        String initialPassword = sysParaService.getParamByName(INITIAL_PASSWORD);

        sysUserInfo.setLoginPass(CodecHelper.encodeMD5(initialPassword));

        boolean result = sysUserInfoService.save(sysUserInfo);

        sysUserInfoService.addRoleInfo(sysUserInfo);

        return result;
    }


    /**
     * 修改密码
     *
     * @param sysUserInfo 用户信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存用户信息" , notes = "修改保存用户信息")
    public boolean updateSysUserInfoById(@RequestBody SysUserInfo sysUserInfo) {
        if(sysUserInfo.getIgnoreRole()==0){
            sysUserInfoService.updateRoleInfo(sysUserInfo);
        }
        return sysUserInfoService.updateById(sysUserInfo);
    }


    /**
     *  修改密码
     *
     * @param sysUserInfo 用户信息
     * @return 结果
     */
    @PostMapping("/modifyPassword")
    @ApiOperation(value = "修改用户密码" , notes = "修改用户密码")
    public boolean modifyPassword(@RequestBody SysUserInfo sysUserInfo) {
        sysUserInfo.setLoginPass(CodecHelper.encodeMD5(sysUserInfo.getLoginPass()));
        return sysUserInfoService.updateById(sysUserInfo);
    }

    /**
     * 重置用户密码
     *
     * @param sysUserInfo 用户信息
     * @return 结果
     */
    @PostMapping("/restPassword")
    @ApiOperation(value = "重置用户密码" , notes = "重置用户密码")
    public boolean restPassword(@RequestBody SysUserInfo sysUserInfo) {

        String initialPassword = sysParaService.getParamByName(INITIAL_PASSWORD);

        sysUserInfo.setLoginPass(CodecHelper.encodeMD5(initialPassword));

        return sysUserInfoService.updateById(sysUserInfo);
    }


    /**
     * 设置用户主题
     *
     * @param sysUserInfo 用户信息
     * @return 结果
     */
    @PostMapping("/themeSetting")
    @ApiOperation(value = "修改用户主题" , notes = "修改用户主题")
    public boolean themeSetting(@RequestBody SysUserInfo sysUserInfo,HttpServletRequest request) {
        String jwt = request.getAttribute("jwt").toString();
        SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);
        tUserInfo.setTheme(sysUserInfo.getTheme());
        authService.saveUserInfoToReids(jwt,tUserInfo);
        return sysUserInfoService.updateById(sysUserInfo);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除用户信息" , notes = "删除用户信息")
    public boolean removeSysUserInfoByIds(String ids) {
        return sysUserInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }

    /**
     * 获取可见前端ID、可控前端ID
     *
     * @param type "view"|"edit"
     * @return 结果
     */
    @GetMapping("/getFrontIds/{type}")
    @ApiOperation(value = "删除用户信息" , notes = "删除用户信息")
    public List<Integer> getFrontIds(@PathVariable("type") String type,HttpServletRequest request) {
        List<Integer> frontIds = getPermissionsFrontIds(request,"edit".equals(type));
        return frontIds;
    }


}