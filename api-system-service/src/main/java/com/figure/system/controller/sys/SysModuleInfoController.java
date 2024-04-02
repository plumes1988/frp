package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.constant.Constants;
import com.figure.core.constant.HttpStatusConstant;
import com.figure.core.service.others.ICommonService;
import com.figure.core.util.JSONUtil;
import com.figure.core.helper.TreeHelper;
import com.figure.core.model.sys.SysModuleInfo;
import com.figure.core.service.sys.ISysModuleInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.core.constant.Constants.DATATABLE_ISENABLE_ENABLED;

/**
 * <p>
 * 模块信息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-03-16
 */
@Slf4j
@Controller
@RequestMapping("/moduleInfo")
@Api(value = "模块信息相关接口" , tags = "模块信息相关接口")
public class SysModuleInfoController {

    @Autowired
    ISysModuleInfoService sysModuleInfoService;


    @Autowired
    ICommonService commonService;

    /**
     * 获取模块信息树形列表
     * @param
     * @return 模块信息树形列表
     */
    @GetMapping({"/getMemuTree"})
    @ResponseBody
    @ApiOperation(value = "获取模块信息树形列表" , notes = "供树形编辑列表、树形下拉框列表使用")
    public List<SysModuleInfo> getMemuTree() {
        QueryWrapper<SysModuleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isEnable",DATATABLE_ISENABLE_ENABLED).eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);
        queryWrapper.orderByAsc("sort");
        List<SysModuleInfo> list =  sysModuleInfoService.list(queryWrapper);
        TreeHelper treeHelper = new TreeHelper<SysModuleInfo>();
        for (SysModuleInfo sysModuleInfo:list){
            sysModuleInfo.setModuleURL(sysModuleInfo.getModuleURL()==null?"":sysModuleInfo.getModuleURL());
        }
        List<SysModuleInfo> treeList =  treeHelper.listConver2Tree(list);
        log.debug("treeList:---->"+JSONUtil.Object2JsonStr(treeList));
        return  treeList;
    }

    /**
     * 查询模块信息列表
     *
     * @param
     * @return 模块信息集合
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查询模块信息列表" , notes = "查询模块信息列表")
    public List<SysModuleInfo> selectSysModuleInfoList() {
        QueryWrapper<SysModuleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);
        queryWrapper.orderByAsc("sort");
        List<SysModuleInfo> list =  sysModuleInfoService.list(queryWrapper);
        for (SysModuleInfo sysModuleInfo:list){
            sysModuleInfo.setName(sysModuleInfo.getModuleName());
        }
        return list;
    }

    /**
     * 根据id获取模块信息
     *
     * @param id 模块信息ID
     * @return 模块信息
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    @ApiOperation(value = "根据id获取模块信息" , notes = "根据id获取模块信息")
    public SysModuleInfo selectSysModuleInfoById(@PathVariable("id") Integer id) {
        return sysModuleInfoService.getById(id);
    }


    /**
     * 保存或更新模块信息
     * @param sysModuleInfo 根据ID判断是新增还是更新
     * @return 模块信息
     */
    @PostMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存或更新模块信息" , notes = "根据ID是否为非空正整数判断是新增、还是更新")
    public Map<String,Object> saveOrUpdateSysModuleInfo(@RequestBody SysModuleInfo sysModuleInfo, HttpServletRequest request) throws IOException {
        if (sysModuleInfo.getId()==null || sysModuleInfo.getId()<0){
            sysModuleInfo.setId(null);
            List<Map<String, Object>> list =  commonService.querySql("select max(sort) as maxSort from sys_module_info where parentId="+sysModuleInfo.getParentId());
            if( list.size()>0 && list.get(0)!=null && list.get(0).get("maxSort")!=null ){
                Integer maxSort = (Integer) list.get(0).get("maxSort");
                sysModuleInfo.setSort(maxSort);
            }
            sysModuleInfoService.save(sysModuleInfo);
        }else{
            sysModuleInfoService.updateById(sysModuleInfo);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysModuleInfo.getId());
        return result;
    }

    /**
     * 单个删除模块
     *
     * @param sysModuleInfo 根据数据ID删除模块
     * @return 结果 ok：成功
     *
     **/
    @PostMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "根据ID单个删除模块信息" , notes = "根据ID单个删除模块信息")
    public ResponseEntity removeSysRoleInfoByIds(@RequestBody SysModuleInfo sysModuleInfo, HttpServletRequest request) throws IOException {
        sysModuleInfo = sysModuleInfoService.getById(sysModuleInfo.getId());
        sysModuleInfo.setIsDelete(Constants.DATATABLE_ISDELETE_DELETED);
        sysModuleInfoService.updateById(sysModuleInfo);
        return new ResponseEntity<String>("ok", HttpStatus.valueOf(HttpStatusConstant.OK_CODE));
    }


    /**
     * 菜单排序
     *
     * @param sysModuleInfos 排序后的菜单列表
     * @return 结果 ok：成功
     *
     **/
    @PostMapping("/orderMenu")
    @ResponseBody
    @ApiOperation(value = "菜单排序" , notes = "菜单排序")
    public ResponseEntity orderMenu(@RequestBody List<SysModuleInfo> sysModuleInfos) throws IOException {
        int order = 1;
        for(SysModuleInfo sysModuleInfo:sysModuleInfos){
            SysModuleInfo smi = sysModuleInfoService.getById(sysModuleInfo.getId());
            smi.setParentId(sysModuleInfo.getParentId());
            smi.setSort(order++);
            sysModuleInfoService.updateById(smi);
        }
        return new ResponseEntity<String>("ok", HttpStatus.valueOf(HttpStatusConstant.OK_CODE));
    }

}