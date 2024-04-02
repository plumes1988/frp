package com.figure.core.base;

import com.figure.core.model.sys.SysUserInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AntUser {

    private String name;
    private String avatar;
    private String userid;
    private String email;
    private String signature;
    private String title;
    private String group;
    private List<Map<String,String>> tags;
    private int notifyCount = 1;
    private int unreadCount = 1;
    private String country;
    private String access;
    private Map<String,Map<String,String>> geographic;
    private String address;
    private String phone;
    private String theme;
    private Integer type;
    private String homepage;

    public AntUser(SysUserInfo userInfo){
        this.userid = userInfo.getUserId().toString();
        this.name = userInfo.getUserName();
        this.avatar  = userInfo.getImgUrl();
        this.email = userInfo.getEmail();
        this.phone =  userInfo.getTelephone();
        this.theme = userInfo.getTheme();
        this.type = userInfo.getType();
        this.homepage = userInfo.getHomepage();
    }
}
