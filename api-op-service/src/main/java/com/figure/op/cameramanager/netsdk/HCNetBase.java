package com.figure.op.cameramanager.netsdk;

import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.domain.vo.RuleInfoVo;
import com.figure.op.cameramanager.util.osSelect;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.system.controller.SysInfoController;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/11 19:34
 * @Version 1.5
 * 海康基础类
 */
@Component
@Data
public class HCNetBase {
    private static final Logger log = LoggerFactory.getLogger(SysInfoController.class);
    public static HCNetSDK hCNetSDK = null;
    public static PlayCtrl playCtrl = null;
    public boolean initFlag = false;

    private static FExceptionCallBack_Imp fExceptionCallBack;

    @Value("${file.url_prefix}")
    private String url_prefix;

    @Value("${file.path}")
    private String path;

    /**
     * 动态库加载
     *
     * @return
     */
    private static boolean createSDKInstance() {
        if (hCNetSDK == null) {
            synchronized (HCNetSDK.class) {
                String strDllPath = "";
                try {
                    if (osSelect.isWindows()) {
                        /** win系统加载库路径*/
                        strDllPath = System.getProperty("user.dir") + "\\lib\\HCNetSDK.dll";
                    } else if (osSelect.isLinux()) {
                        /** Linux系统加载库路径*/
                        strDllPath = System.getProperty("user.dir") + "/lib/libhcnetsdk.so";
                    }
                    hCNetSDK = (HCNetSDK) Native.loadLibrary(strDllPath, HCNetSDK.class);
                } catch (Exception ex) {
                    System.out.println("loadLibrary: " + strDllPath + " Error: " + ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 播放库加载
     *
     * @return
     */
    private static boolean createPlayInstance() {
        if (playCtrl == null) {
            synchronized (PlayCtrl.class) {
                String strPlayPath = "";
                try {
                    if (osSelect.isWindows()) {
                        /**win系统加载库路径*/
                        strPlayPath = System.getProperty("user.dir") + "\\lib\\PlayCtrl.dll";
                    } else if (osSelect.isLinux()) {
                        /**Linux系统加载库路径*/
                        strPlayPath = System.getProperty("user.dir") + "/lib/libPlayCtrl.so";
                    }

                    playCtrl = (PlayCtrl) Native.loadLibrary(strPlayPath, PlayCtrl.class);

                } catch (Exception ex) {
                    System.out.println("loadLibrary: " + strPlayPath + " Error: " + ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 初始化
     */
    public boolean init() throws InterruptedException {
        if (!initFlag) {

            if (hCNetSDK == null && playCtrl == null) {
                if (!createSDKInstance()) {
                    System.out.println("Load SDK fail");
                    return false;
                }
                if (!createPlayInstance()) {
                    System.out.println("Load PlayCtrl fail");
                    return false;
                }
            }
            //linux系统建议调用以下接口加载组件库
            if (osSelect.isLinux()) {
                HCNetSDK.BYTE_ARRAY ptrByteArray1 = new HCNetSDK.BYTE_ARRAY(256);
                HCNetSDK.BYTE_ARRAY ptrByteArray2 = new HCNetSDK.BYTE_ARRAY(256);
                //这里是库的绝对路径，请根据实际情况修改，注意改路径必须有访问权限
                String strPath1 = System.getProperty("user.dir") + "/lib/libcrypto.so";
                String strPath2 = System.getProperty("user.dir") + "/lib/libssl.so.1.1";

                System.arraycopy(strPath1.getBytes(), 0, ptrByteArray1.byValue, 0, strPath1.length());
                ptrByteArray1.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(3, ptrByteArray1.getPointer());

                System.arraycopy(strPath2.getBytes(), 0, ptrByteArray2.byValue, 0, strPath2.length());
                ptrByteArray2.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(4, ptrByteArray2.getPointer());

                String strPathCom = System.getProperty("user.dir") + "/lib/";
                HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
                System.arraycopy(strPathCom.getBytes(), 0, struComPath.sPath, 0, strPathCom.length());
                struComPath.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(2, struComPath.getPointer());
            }

            //SDK初始化，一个程序只需要调用一次
            boolean initSuc = hCNetSDK.NET_DVR_Init();

            //异常消息回调
            if (fExceptionCallBack == null) {
                fExceptionCallBack = new FExceptionCallBack_Imp();
            }
            Pointer pUser = null;
            if (!hCNetSDK.NET_DVR_SetExceptionCallBack_V30(0, 0, fExceptionCallBack, pUser)) {
                return false;
            }
            System.out.println("设置异常消息回调成功");
            //初始化标志
            initFlag = true;
            //启动SDK写日志
            hCNetSDK.NET_DVR_SetLogToFile(3, "./sdkLog", false);
        }
        return true;
    }

    /**
     * 设备登录V40 与V30功能一致
     *
     * @param ip   设备IP
     * @param port SDK端口，默认设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     */
    public Pair<Integer, Integer> login_V40(String ip, short port, String user, String psw) {
        //注册
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息

        String m_sDeviceIP = ip;//设备ip地址
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());

        String m_sUsername = user;//设备用户名
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());

        String m_sPassword = psw;//设备密码
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());

        m_strLoginInfo.wPort = port;
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.byLoginMode = 0;  //0- SDK私有协议，1- ISAPI协议
        m_strLoginInfo.write();
        int userID = -1;
        int lDChannel = -1;
        userID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (userID == -1) {
            System.out.println("登录失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return null;
        }
        System.out.println(ip + ":设备登录成功！");
        //相机一般只有一个通道号，热成像相机有2个通道号，通道号为1或1,2
        //byStartDChan为IP通道起始通道号, 预览回放NVR的IP通道时需要根据起始通道号进行取值
        if ((int) m_strDeviceInfo.struDeviceV30.byStartDChan == 1 && (int) m_strDeviceInfo.struDeviceV30.byStartDChan == 33) {
            //byStartDChan为IP通道起始通道号, 预览回放NVR的IP通道时需要根据起始通道号进行取值,NVR起始通道号一般是33或者1开始
            lDChannel = (int) m_strDeviceInfo.struDeviceV30.byStartDChan;
            System.out.println("预览起始通道号：" + lDChannel);
        }
        return new Pair<>(userID, lDChannel);
    }


    public List<RuleInfoVo> getRuleInfoVo(Integer lUserID) {
        boolean bRet = false;
        HCNetSDK.BYTE_ARRAY pstatus = new HCNetSDK.BYTE_ARRAY(16 * 1024);
        Pointer pStatusBuffer = pstatus.getPointer();
        HCNetSDK.NET_DVR_STD_CONFIG m_lpConfigParam = new HCNetSDK.NET_DVR_STD_CONFIG();
        HCNetSDK.NET_DVR_THERMOMETRY_COND m_lpThCond = new HCNetSDK.NET_DVR_THERMOMETRY_COND();
        HCNetSDK.NET_DVR_THERMOMETRY_PRESETINFO m_lpThPresetInfp = new HCNetSDK.NET_DVR_THERMOMETRY_PRESETINFO();
        m_lpThCond.dwSize = m_lpThCond.size();
        m_lpThCond.dwChannel = 1;
        m_lpThCond.wPresetNo = 1;
        m_lpThCond.write();
        m_lpConfigParam.lpCondBuffer = m_lpThCond.getPointer();
        m_lpConfigParam.dwCondSize = m_lpThCond.dwSize;
        m_lpConfigParam.lpInBuffer = null;
        m_lpConfigParam.dwInSize = 0;
        m_lpConfigParam.lpOutBuffer = m_lpThPresetInfp.getPointer();
        m_lpConfigParam.dwOutSize = m_lpThPresetInfp.size();
        m_lpConfigParam.lpStatusBuffer = pStatusBuffer;
        m_lpConfigParam.dwStatusSize = 16 * 1024;
        m_lpConfigParam.byDataType = 0;
        m_lpConfigParam.write();
        m_lpThPresetInfp.write();
        bRet = hCNetSDK.NET_DVR_GetSTDConfig(lUserID, 3624, m_lpConfigParam);
        if (bRet == false) {
            log.error("获取区域配置信息出错");
        }
        m_lpThPresetInfp.read();
        HCNetSDK.NET_DVR_THERMOMETRY_PRESETINFO_PARAM[] struPresetInfo = m_lpThPresetInfp.struPresetInfo;
        ArrayList<RuleInfoVo> ruleInfoVos = new ArrayList<>();
        for (int i = 0; i < struPresetInfo.length; i++) {
            RuleInfoVo ruleInfoVo = new RuleInfoVo();
            if (struPresetInfo[i].byEnabled == 0) {
                return ruleInfoVos;
            }
            ruleInfoVo.setRuleAreaId((int) struPresetInfo[i].byRuleID);
            ruleInfoVo.setAreaName(new String(struPresetInfo[i].szRuleName).trim());
            ruleInfoVos.add(ruleInfoVo);
        }
        return ruleInfoVos;
    }

    /**
     * 注销用户
     */
    public boolean logout(int usreId) {
        return hCNetSDK.NET_DVR_Logout(usreId);
    }

    /**
     * 抓取参考图片
     *
     * @param lUserID
     * @param iChannelNum
     * @param picsize
     */
    public String captureReferenceJPEGPicture(int lUserID, int iChannelNum, short picsize, Integer id, String src,String username) {
        HCNetSDK.NET_DVR_JPEGPARA lpJpegPara = new HCNetSDK.NET_DVR_JPEGPARA();
        lpJpegPara.wPicQuality = 1;
        lpJpegPara.wPicSize = picsize;
        String imageSrc = path + src;
        File file = new File(imageSrc);
        if (!file.exists()) {
            file.mkdirs();
        }
        imageSrc += File.separator + username + "Reference.jpg";
        file = new File(imageSrc);
        if (file.exists()) {
            file.delete();
        }
        if (!hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, iChannelNum, lpJpegPara, imageSrc)) {
            new GlobalException("设备抓图失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        }

        // 上传并返回新文件名称
        String url = url_prefix + src + File.separator + username + "Reference.jpg";
        return url;
    }


    /**
     * 抓取图片
     *
     * @param lUserID
     * @param iChannelNum
     * @param picsize
     */
    public String captureJPEGPicture(int lUserID, int iChannelNum, short picsize, Integer id, String src) {
        HCNetSDK.NET_DVR_JPEGPARA lpJpegPara = new HCNetSDK.NET_DVR_JPEGPARA();
        lpJpegPara.wPicQuality = 1;
        lpJpegPara.wPicSize = picsize;
        String imageSrc = path + src;
        File file = new File(imageSrc);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String newName = sf.format(new Date());
        imageSrc += File.separator + newName + ".jpg";
        if (!hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, iChannelNum, lpJpegPara, imageSrc)) {
            new GlobalException("设备抓图失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        }

        // 上传并返回新文件名称
        String url = url_prefix + src + File.separator + newName + ".jpg";
        return url;
    }

    /**
     * 抓取图片
     *
     * @param lUserID
     * @param iChannelNum
     * @param picsize
     */
    public String cronCaptureJPEGPicture(int lUserID, int iChannelNum, short picsize, Integer id, String src,String  imageName) {
        HCNetSDK.NET_DVR_JPEGPARA lpJpegPara = new HCNetSDK.NET_DVR_JPEGPARA();
        lpJpegPara.wPicQuality = 1;
        lpJpegPara.wPicSize = picsize;
        String imageSrc = path + src;
        File file = new File(imageSrc);
        if (!file.exists()) {
            file.mkdirs();
        }
        imageSrc += File.separator + imageName + "Analysis.jpg";
        file = new File(imageSrc);
        if (file.exists()) {
            file.delete();
        }
        if (!hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, iChannelNum, lpJpegPara, imageSrc)) {
            new GlobalException("设备抓图失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        }

        // 上传并返回新文件名称
        String url = url_prefix + src + File.separator + imageName + "Analysis.jpg";
        return url;
    }

}
