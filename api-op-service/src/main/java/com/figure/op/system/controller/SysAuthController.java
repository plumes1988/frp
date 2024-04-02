package com.figure.op.system.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.figure.op.common.domain.R;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.bo.SysUserInfoBo;
import com.figure.op.system.domain.vo.SysUserInfoListVo;
import com.figure.op.system.domain.vo.SysUserInfoVo;
import com.figure.op.system.domain.vo.SysUserLoginVo;
import com.figure.op.system.service.ISysUserInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 权限控制器
 * @author ly
 */
@RestController
@RequestMapping("/auth")
public class SysAuthController {

    /**
     * 登录接口-mock
     */
    @PostMapping("/authenticate")
    public void list(@RequestBody SysUserLoginVo reqVo, ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        JSONObject jsonRes = new JSONObject();
        jsonRes.put("status", "ok");
        jsonRes.put("jwt", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5MzQwMDAwNCwiaWF0IjoxNjkzMzY0MDA0fQ.WWRt79WEqN2gnPVbHodtuR7CiN-AEnYHCQghasq_m5E");
        try {
            res.getWriter().println(JSONUtil.toJsonStr(jsonRes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
