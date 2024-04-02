package com.figure.op.cameramanager.netsdk;

import com.sun.jna.Pointer;

/**
 * @Author liqiang
 * @Date 2023/9/11 19:40
 * @Version 1.5
 */
public class FExceptionCallBack_Imp implements HCNetSDK.FExceptionCallBack {
    public void invoke(int dwType, int lUserID, int lHandle, Pointer pUser) {
        System.out.println("异常事件类型:" + dwType);
        return;
    }
}
