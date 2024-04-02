package com.figure.op.cameramanager.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author liqiang
 * @Date 2023/9/13 8:42
 * @Version 1.5
 */
@Data
public class RuleInfoVo {

    /**
     * 规则Id
     */
    private Integer ruleAreaId;
    /**
     * 规则区域名称
     */
    private String areaName;

}
