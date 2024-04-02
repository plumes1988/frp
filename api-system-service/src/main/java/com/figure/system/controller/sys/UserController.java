package com.figure.system.controller.sys;

import com.figure.core.helper.TreeHelper;
import com.figure.core.base.AntUser;
import com.figure.core.helper.MenuHelper;
import com.figure.core.model.sys.SysModuleInfo;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.service.sys.ISysUserInfoService;
import com.figure.core.service.sys.impl.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户登录后获取用户基础信息基" , tags = "基础数据包括:当前用户信息、菜单信息")
public class UserController {

    @Autowired
    ISysUserInfoService sysUserInfoService;

    @Autowired
    AuthService authService;


    /**
     * 获取当前登录用户基础信息
     *
     * @param request 包含凭证jwt、根据凭证获取用户信息
     * @return 获取当前用户信息
     */
    @RequestMapping("/currentUser")
    @ResponseBody
    @ApiOperation(value = "获取当前用户信息" , notes = "根据凭证获取当前用户信息")
    public AntUser currentUser(HttpServletRequest request) {
        SysUserInfo tUserInfo = sysUserInfoService.getUserInfoByHttpRequest(request);
        return new AntUser(tUserInfo);
    }

    /**
     * 查询当前登录用户权限下的菜单列表
     *
     * @param request 包含凭证jwt、根据凭证获取用户拥有权限的菜单树形列表
     * @return 树形菜单列表
     */
    @RequestMapping("/getUserMenu")
    @ResponseBody
    @ApiOperation(value = "获取当前用户菜单" , notes = "根据凭证获取当前用户的菜单列表")
    public List<Map<String,Object>> getUserMenu(HttpServletRequest request, HttpServletResponse response) {

        String jwt = request.getAttribute("jwt").toString();
        SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);


        List<SysModuleInfo> userModuleInfos =  sysUserInfoService.getUserModuleInfos(tUserInfo.getUserId().intValue());

        TreeHelper treeHelper = new TreeHelper<SysModuleInfo>();
        List<SysModuleInfo> treeList =  treeHelper.listConver2Tree(userModuleInfos);
        List<Map<String, Object>> menus = new ArrayList<>();
        menus = MenuHelper.coverToAntMenu(treeList,menus);

        return menus;
    }

    /**
     * 查询当前登录用户权限下的菜单列表
     *
     * @param request 包含凭证jwt、根据凭证获取用户拥有权限的菜单列表
     * @return 菜单列表
     */
    @RequestMapping("/getUserMenuList")
    @ResponseBody
    @ApiOperation(value = "获取当前用户菜单" , notes = "根据凭证获取当前用户的菜单列表")
    public List<SysModuleInfo> getUserMenuList(HttpServletRequest request, HttpServletResponse response) {

        String jwt = request.getAttribute("jwt").toString();
        SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);


        List<SysModuleInfo> userModuleInfos =  sysUserInfoService.getUserModuleInfos(tUserInfo.getUserId().intValue());

        removeNodesWithoutParent(userModuleInfos);

        return userModuleInfos;
    }


    private static void removeNodesWithoutParent(List<SysModuleInfo> nodeList) {
        List<SysModuleInfo> nodesToRemove = new ArrayList<>();

        for (SysModuleInfo node : nodeList) {
            if (node.getParentId() != 0 && !containsParent(node.getParentId(), nodeList)) {
                nodesToRemove.add(node);
            }
        }

        for (SysModuleInfo node : nodesToRemove) {
            nodeList.remove(node);
        }

        if (!nodesToRemove.isEmpty()) {
            removeNodesWithoutParent(nodeList);
        }
    }

    private static boolean containsParent(int parentId, List<SysModuleInfo> nodeList) {
        for (SysModuleInfo node : nodeList) {
            if (node.getId() == parentId) {
                return true;
            }
        }
        return false;
    }


}
