package com.figure.core.service.sys.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.figure.core.model.sys.SysJwtUserinfoStore;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.service.sys.IAuthService;
import com.figure.core.service.sys.ISysJwtUserinfoStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService implements IAuthService {

    @Resource
    IRedisTemplateService redisTemplateService;


    @Autowired
    private ApplicationContext applicationContext;

    public void saveUserInfoToReids(String jwt, SysUserInfo tUserInfo) {
        ISysJwtUserinfoStoreService sysJwtUserinfoStoreService = applicationContext.getBean(ISysJwtUserinfoStoreService.class);
        try {
            redisTemplateService.setRedisCache(jwt,tUserInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        SysJwtUserinfoStore sysJwtUserinfoStore = new SysJwtUserinfoStore();
        sysJwtUserinfoStore.setJwt(jwt);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(tUserInfo);
            sysJwtUserinfoStore.setTUserInfoJson(json);
            sysJwtUserinfoStoreService.save(sysJwtUserinfoStore);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public SysUserInfo getUserInfoFromReids(String jwt) {
        ISysJwtUserinfoStoreService sysJwtUserinfoStoreService = applicationContext.getBean(ISysJwtUserinfoStoreService.class);
        try {
            SysUserInfo sysUserInfo  =  redisTemplateService.getObjectRedisCache(jwt,SysUserInfo.class);
            return sysUserInfo;
        }catch (Exception e){
            e.printStackTrace();
            SysJwtUserinfoStore sysJwtUserinfoStore  = sysJwtUserinfoStoreService.getById(jwt);
            if(sysJwtUserinfoStore!=null){
                String  json =  sysJwtUserinfoStore.getTUserInfoJson();
                ObjectMapper mapper = new ObjectMapper();
                try {
                    SysUserInfo sysUserInfo = mapper.readValue(json, SysUserInfo.class);
                    return sysUserInfo;
                } catch (Exception e01) {
                    e01.printStackTrace();
                }
            }
        }
        return null;
    }
}
