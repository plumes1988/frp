package com.figure.core.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.log.LogLogin;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2024-01-03
 */
public interface ILogLoginService extends IService<LogLogin> {

    void saveLog(HttpServletRequest request, Long userId, String username, Integer result);

    void fillEntityProps(List<LogLogin> records);
}
