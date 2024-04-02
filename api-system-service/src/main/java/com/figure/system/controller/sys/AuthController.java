package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.constant.HttpStatusConstant;
import com.figure.core.helper.CodecHelper;
import com.figure.core.base.BaseController;
import com.figure.core.model.sys.SysApiKey;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.security.MyUserDetailsService;
import com.figure.core.security.models.AuthenticationRequest;
import com.figure.core.security.models.AuthenticationResponse;
import com.figure.core.security.models.MyUser;
import com.figure.core.service.log.ILogLoginService;
import com.figure.core.service.sys.ISysApiKeyService;
import com.figure.core.service.sys.ISysUserInfoService;
import com.figure.core.service.sys.impl.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Api(value = "用户认证相关接口" , tags = "用户认证相关接口")
class AuthController extends BaseController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private com.figure.core.security.util.JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    AuthService authService;

    @Autowired
    ISysApiKeyService sysApiKeyService;

    @Resource
    private ISysUserInfoService sysUserInfoService;

    @Resource
    private ILogLoginService logLoginService;

    private static final int LOGIN_SUCCESS_CODE = 1;

    private static final String LOGIN_SUCCESS = "登录成功";

    private static final int LOGOUT_SUCCESS_CODE = 0;

    private static final String LOGOUT_SUCCESS = "登出成功";

    private static final Map<Integer,String> LOGIN_RESULT = new HashMap(){{
        put(HttpStatusConstant.USER_NOT_FOUND_CODE,HttpStatusConstant.USER_NOT_FOUND);
        put(HttpStatusConstant.INCORRECT_USERNAME_OR_PASSWORD_CODE, HttpStatusConstant.INCORRECT_USERNAME_OR_PASSWORD);
        put(HttpStatusConstant.ACCOUNT_DISABLE_CODE, HttpStatusConstant.ACCOUNT_DISABLE);
        put(LOGIN_SUCCESS_CODE, HttpStatusConstant.ACCOUNT_DISABLE);
        put(LOGOUT_SUCCESS_CODE,LOGOUT_SUCCESS);
    }};

    /**
     * 用户认证获取用户凭证
     *
     * @param authenticationRequest 角色信息
     * @return token
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ApiOperation(value = "用户认证获取用户凭证" , notes = "传参 用户名、密码获取用户凭证")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username =  authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String md5Password = "";
        try {
            md5Password = CodecHelper.encodeMD5(password);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,md5Password)
            );
        }catch(InternalAuthenticationServiceException e){
            e.printStackTrace();
            logLoginService.saveLog(request,null,username,HttpStatusConstant.USER_NOT_FOUND_CODE);
            return ResponseEntity.status(HttpStatusConstant.USER_NOT_FOUND_CODE).body(HttpStatusConstant.USER_NOT_FOUND);
        }catch (BadCredentialsException e) {
            logLoginService.saveLog(request,null,username,HttpStatusConstant.INCORRECT_USERNAME_OR_PASSWORD_CODE);
            return ResponseEntity.status(HttpStatusConstant.INCORRECT_USERNAME_OR_PASSWORD_CODE).body(HttpStatusConstant.INCORRECT_USERNAME_OR_PASSWORD);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsernameAndPassword(username,md5Password);

        if(userDetails!=null){
            MyUser myUser = (MyUser) userDetails;
            if(myUser.getUserInfo().getIsEnable()==0){
                logLoginService.saveLog(request,null,username,HttpStatusConstant.ACCOUNT_DISABLE_CODE);
                return ResponseEntity.status(HttpStatusConstant.ACCOUNT_DISABLE_CODE).body(HttpStatusConstant.ACCOUNT_DISABLE);
            }
        }

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        MyUser myUser = (MyUser) userDetails;

        SysUserInfo sysUserInfo = myUser.getUserInfo();

        authService.saveUserInfoToReids(jwt,sysUserInfo);

        logLoginService.saveLog(request,sysUserInfo.getUserId(),null,LOGIN_SUCCESS_CODE);

        return ResponseEntity.ok(new AuthenticationResponse(jwt,"ok"));
    }



    /**
     * 登出
     *
     * @param request 包含凭证jwt、根据凭证获取用户信息
     * @return 获取当前用户信息
     */
    @RequestMapping("/logout")
    @ApiOperation(value = "用户登出" , notes = "用户登出")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        logLoginService.saveLog(request,Long.parseLong(userId),null,LOGOUT_SUCCESS_CODE);
        return ResponseEntity.ok().build();
    }

    /**
     * 申请token
     *
     * @return token
     */
    @RequestMapping(value = "/applyToken", method = RequestMethod.GET)
    @ApiOperation(value = "申请token" , notes = "通过apiKey申请token")
    public Map<String,Object> applyAuthenticationToken( HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String,Object> result = new HashMap<>();

        String key = request.getParameter("key");
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apiKey",key);
        SysApiKey sysApiKey = sysApiKeyService.getOne(queryWrapper);

        if(sysApiKey==null){
            result.put("status",0);
            result.put("msg","key错误,请确认您输入的key是否正确！");
            return result;
        }

        if(sysApiKey.getStatus()==0){
            result.put("status",0);
            result.put("msg","key已被禁用,请联系管理员！");
            return result;
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDelete",0);

        SysUserInfo sysUserInfo = sysUserInfoService.list().get(0);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(sysUserInfo.getLoginName(),sysUserInfo.getLoginPass())
        );

        final UserDetails userDetails = userDetailsService
                .loadUserByUsernameAndPassword(sysUserInfo.getLoginName(),sysUserInfo.getLoginPass());

        final String jwt = jwtTokenUtil.generateToken(userDetails,sysApiKey.getExpiration());

        MyUser myUser = (MyUser) userDetails;

        sysUserInfo = myUser.getUserInfo();

        authService.saveUserInfoToReids(jwt,sysUserInfo);

        result.put("status",1);
        result.put("jwt",jwt);
        return result;
    }

}
