package com.figure.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.sys.SysModuleInfo;
import com.figure.core.model.sys.SysUserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户信息 IService
 * </p>
 * 
 * @author feather
 * @date 2021-03-18 16:07:45
 */

public interface ISysUserInfoService extends IService<SysUserInfo> {
    void fetchRoleInfo(SysUserInfo sysUserInfo);
    void addRoleInfo(SysUserInfo sysUserInfo);
    void updateRoleInfo(SysUserInfo sysUserInfo);
    List<SysModuleInfo> getUserModuleInfos(Integer userId);
    List<Integer> getFrontIds(SysUserInfo sysUserInfo,boolean onlyControllable);
    SysUserInfo getUserInfoByHttpRequest(HttpServletRequest request);
}