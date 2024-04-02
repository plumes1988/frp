package com.figure.core.service.sys;

import com.figure.core.model.sys.SysUserInfo;

public interface IAuthService {

    void saveUserInfoToReids(String jwt, SysUserInfo tUserInfo);

    SysUserInfo getUserInfoFromReids(String jwt);
}
