package com.figure.core.aop;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.service.sys.IAuthService;
import com.figure.core.util.DateUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class TimeMetaObjectHandler implements MetaObjectHandler {
    @Resource
    IAuthService authService;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, DateUtils.getNowDate());
        this.strictInsertFill(metaObject, "createUserId", Integer.class, getUserIdByRequest());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, DateUtils.getNowDate());
        this.strictInsertFill(metaObject, "updateUserId", Integer.class, getUserIdByRequest());
    }

    private Integer getUserIdByRequest() {
        Integer userId = 1;
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request.getAttribute("jwt") != null) {
                String jwt = request.getAttribute("jwt").toString();
                SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);
                userId = tUserInfo.getUserId().intValue();
            }
        }
        return userId;
    }
}
