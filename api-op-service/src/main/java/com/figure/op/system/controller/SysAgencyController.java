package com.figure.op.system.controller;

import com.figure.op.common.domain.R;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.system.domain.SysDept;
import com.figure.op.system.domain.bo.SysDeptBo;
import com.figure.op.system.domain.vo.SysAgencySimpleVo;
import com.figure.op.system.domain.vo.SysDeptListVo;
import com.figure.op.system.domain.vo.SysDeptVo;
import com.figure.op.system.service.ISysAgencyService;
import com.figure.op.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机构控制器
 *
 * @author ly
 */
@RestController
@RequestMapping("/sysAgency")
@Api(tags = "机构")
public class SysAgencyController {

    @Resource
    private ISysAgencyService sysAgencyService;


    /**
     * 全部列表
     */
    @GetMapping("/all-simple-list")
    @ApiOperation("机构简单列表")
    public R<List<SysAgencySimpleVo>> allSimpleList() {
        return R.ok(sysAgencyService.allSimpleList());
    }

}
