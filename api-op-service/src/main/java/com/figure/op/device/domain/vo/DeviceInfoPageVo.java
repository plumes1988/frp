package com.figure.op.device.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 分页视图对象
 * @author fsn
 */
@Data
public class DeviceInfoPageVo {

    private Integer deviceId;

    private String deviceName;

    private String deviceCode;

    private String deviceType;


}
