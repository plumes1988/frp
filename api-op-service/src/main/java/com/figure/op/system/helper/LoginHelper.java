package com.figure.op.system.helper;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 登录鉴权助手
 *
 * @author fsn
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {



    /**
     * 获取用户ID
     */
    public static Integer getUserId() {

        // TODO 获取登录用户 默认1
        Integer loginUserId = 1;

        return loginUserId;
    }


}
