package com.figure.core.security.models;
import com.figure.core.model.sys.SysUserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUser extends User {

    private SysUserInfo userInfo;

    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SysUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SysUserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
