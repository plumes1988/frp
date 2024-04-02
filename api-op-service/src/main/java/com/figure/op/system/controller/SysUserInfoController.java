package com.figure.op.system.controller;

import com.figure.op.common.domain.R;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.bo.SysUserInfoBo;
import com.figure.op.system.domain.vo.SysUserInfoListVo;
import com.figure.op.system.domain.vo.SysUserInfoVo;
import com.figure.op.system.service.ISysUserInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户控制器
 * @author fsn
 */
@RestController
@RequestMapping("/sysUserInfo")
public class SysUserInfoController {

    @Resource
    private ISysUserInfoService sysUserInfoService;


    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<SysUserInfoListVo>> list(SysUserInfo SysUserInfo) {
        return R.ok(sysUserInfoService.queryList(SysUserInfo));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<SysUserInfoVo> info(@PathVariable Integer id) {
        return R.ok(sysUserInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysUserInfoBo bo) {
        return R.toAjax(sysUserInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysUserInfoBo bo) {
        return R.toAjax(sysUserInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(sysUserInfoService.deleteById(id));
    }

}
