package com.figure.core.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.log.LogLogin;
import com.figure.core.repository.log.LogLoginMapper;
import com.figure.core.service.log.ILogLoginService;
import com.figure.core.service.others.ICommonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2024-01-03
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements ILogLoginService {

    @Autowired
    ICommonService commonService;

    @Override
    public void saveLog(HttpServletRequest request, Long userId, String username, Integer result) {
        String ipAddress = request.getRemoteAddr();
        if("0:0:0:0:0:0:0:1".equals(ipAddress)){
            ipAddress="127.0.0.1";
        }
        String userAgent = request.getHeader("User-Agent");
        LogLogin logLogin= new LogLogin();
        logLogin.setTime(new Date());
        logLogin.setResult(result);
        logLogin.setUserId(userId);
        logLogin.setUserName(username);
        if(StringUtils.isEmpty(username)){
            Map<Long,String> userId_userName_map =  commonService.get_userId_userName_map();
            String userName = userId_userName_map.get(userId);
            logLogin.setUserName(userName);
        }
        logLogin.setDevice(userAgent);
        logLogin.setIp(ipAddress);
        baseMapper.insert(logLogin);
    }

    @Override
    public void fillEntityProps(List<LogLogin> records) {
        Map<Long,String> userId_userName_map =  commonService.get_userId_userName_map();
        for (LogLogin logLogin:records){
            String cur_userName =  userId_userName_map.get(logLogin.getUserId());
            if(cur_userName != null){
                logLogin.setUserName(cur_userName);
            }
        }
    }
}
