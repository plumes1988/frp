package com.figure.core.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.helper.CodecHelper;
import com.figure.core.security.models.MyUser;
import com.figure.core.service.sys.ISysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ISysUserInfoService sysUserInfoService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper query = new QueryWrapper();
        query.eq("loginName",s);
        List<SysUserInfo> sysUserInfos = sysUserInfoService.list(query);
        if(sysUserInfos.size()==0){
            return null;
        }else{
            return new User(sysUserInfos.get(0).getLoginName(), sysUserInfos.get(0).getLoginPass(),
                    new ArrayList<>());
        }
    }

    public UserDetails loadUserByUsernameAndPassword(String loginName,String password) throws UsernameNotFoundException {
        String md5Password = CodecHelper.encodeMD5(password);
        QueryWrapper query = new QueryWrapper();
        query.eq("loginName",loginName);
        query.eq("loginPass",password);
        List<SysUserInfo> sysUserInfos = sysUserInfoService.list(query);
        if(sysUserInfos.size()==0){
            return null;
        }else{
            MyUser myUser = new MyUser(loginName, md5Password,
                    new ArrayList<>());
            myUser.setUserInfo(sysUserInfos.get(0));
            return myUser;
        }
    }





}