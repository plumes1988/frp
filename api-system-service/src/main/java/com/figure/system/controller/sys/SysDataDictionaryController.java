package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.figure.core.constant.HttpStatusConstant;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.util.JSONUtil;
import com.figure.core.helper.TreeHelper;
import com.figure.core.base.BaseController;
import com.figure.core.model.sys.SysDataDictionary;
import com.figure.core.query.sys.SysDataDictionaryQuery;
import com.figure.core.service.sys.ISysDataDictionaryService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统数据字典表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-03-29
 */
@Slf4j
@RestController
@RequestMapping("/dataDictionary")
@Api(value = "数据字典相关接口", tags = "数据字典相关接口")
public class SysDataDictionaryController extends BaseController {

    @Autowired
    ISysDataDictionaryService sysDataDictionaryService;

    /**
     * 数据字典树形列表
     *
     * @param
     * @return 数据字典树形列表
     */
    @GetMapping({"/getMemuTree"})
    @ResponseBody
    @ApiOperation(value = "数据字典树形列表", notes = "供树形编辑列表、树形下拉框列表使用")
    public List<SysDataDictionary> getMemuTree() {
        List<SysDataDictionary> list = sysDataDictionaryService.list();
        for(SysDataDictionary sysDataDictionary:list){
            sysDataDictionary.setValue(sysDataDictionary.getId().intValue());
            sysDataDictionary.setTitle(sysDataDictionary.getParaName());
        }
        TreeHelper treeHelper = new TreeHelper<SysDataDictionary>();
        List<SysDataDictionary> treeList = treeHelper.listConver2Tree(list);
        log.debug("treeList:---->" + JSONUtil.Object2JsonStr(treeList));
        return treeList;
    }

    /**
     * 查询数据字典列表
     *
     * @param request
     * @return 设备集合
     */
    @GetMapping("/pageList")
    @ApiOperation(value = "查询数据字典列表" , notes = "查询数据字典列表")
    public TableDataInfo<SysDataDictionary> selectSysDataDictionaryPageList(HttpServletRequest request) throws Exception {
        PageWrapper<SysDataDictionary> pageWrapper = new PageHelper(SysDataDictionary.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        queryWrapper.eq("isDelete", com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);

        IPage<SysDataDictionary> pages = sysDataDictionaryService.page(pageWrapper.getPage(), queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 查询数据字典列表
     *
     * @param
     * @return 数据字典集合
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查询数据字典列表", notes = "查询数据字典列表")
    public List<SysDataDictionary> selectSysDataDictionaryList(SysDataDictionary sysDataDictionary) {
        QueryWrapper<SysDataDictionary> queryWrapper = new QueryWrapper<>(sysDataDictionary);
        List<SysDataDictionary> list = sysDataDictionaryService.list(queryWrapper);
        for (SysDataDictionary s : list) {
            s.setName(s.getParaName());
        }
        list = list.stream().filter(item-> StringUtils.isNotEmpty(item.getParaType())&StringUtils.isNotEmpty(item.getParaValue())).collect(Collectors.toList());
        TreeHelper treeHelper = new TreeHelper<SysDataDictionary>();
        treeHelper.initRelOfParentAndChildren(list);
        return list;
    }

    /**
     * 查询数据字典数据
     *
     * @param
     * @return 数据字典集合
     */
    @GetMapping("/data")
    @ResponseBody
    @ApiOperation(value = "查询数据字典列表", notes = "查询数据字典列表")
    public TableDataInfo selectSysDataDictionaryData(SysDataDictionaryQuery sysDataDictionaryQuery) {
        return toPageResult(this.sysDataDictionaryService.list(sysDataDictionaryQuery.autoWrapper()));
    }

    /**
     * 根据id数据字典
     *
     * @param id 数据字典ID
     * @return 数据字典
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    @ApiOperation(value = "根据id数据字典", notes = "根据id数据字典")
    public SysDataDictionary selectSysDataDictionaryById(@PathVariable("id") Integer id) {
        return sysDataDictionaryService.getById(id);
    }


    /**
     * 保存或更新数据字典
     *
     * @param sysDataDictionary 根据ID判断是新增还是更新
     * @return 数据字典
     */
    @PostMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存或更新数据字典", notes = "根据ID是否为非空正整数判断是新增、还是更新")
    public Map<String, Object> saveOrUpdateSysDataDictionary(@RequestBody SysDataDictionary sysDataDictionary, HttpServletRequest request) throws IOException {
        Integer parentId = sysDataDictionary.getParentId();
        if (parentId == null || parentId == 0) {
            sysDataDictionary.setParentParaType(null);
            sysDataDictionary.setParentParaValue(null);
        } else {
            SysDataDictionary parent = sysDataDictionaryService.getById(parentId);
            sysDataDictionary.setParentParaType(parent.getParaType());
            sysDataDictionary.setParentParaValue(parent.getParaValue());
        }
        if (sysDataDictionary.getId() == null || sysDataDictionary.getId() < 0) {
            sysDataDictionary.setId(null);
            sysDataDictionaryService.save(sysDataDictionary);
        } else {
            SysDataDictionary preSysDataDictionary = sysDataDictionaryService.getById(sysDataDictionary.getId());
            sysDataDictionaryService.updateById(sysDataDictionary);
            UpdateWrapper<SysDataDictionary> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",sysDataDictionary.getId());
            updateWrapper.set("parentParaType",sysDataDictionary.getParentParaType());
            updateWrapper.set("parentParaValue",sysDataDictionary.getParentParaValue());
            sysDataDictionaryService.update(updateWrapper);

            if (!(preSysDataDictionary.getParaType().equals(sysDataDictionary.getParaType())
                    && preSysDataDictionary.getParaValue().equals(sysDataDictionary.getParaValue()))) {
                SysDataDictionary updateSysDataDictionary = new SysDataDictionary();
                updateSysDataDictionary.setParentParaType(sysDataDictionary.getParaType());
                updateSysDataDictionary.setParentParaValue(sysDataDictionary.getParaValue());
                LambdaQueryWrapper<SysDataDictionary> sysDataDictionaryWrapper = Wrappers.lambdaQuery();
                sysDataDictionaryWrapper.eq(SysDataDictionary::getParentParaType, preSysDataDictionary.getParaType())
                        .eq(SysDataDictionary::getParentParaValue, preSysDataDictionary.getParaValue());
                sysDataDictionaryService.update(updateSysDataDictionary, sysDataDictionaryWrapper);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("status", 1);
        result.put("pk", sysDataDictionary.getId());
        return result;
    }

    /**
     * 单个删除模块
     *
     * @param sysDataDictionary 根据数据ID删除模块
     * @return 结果 ok：成功
     **/
    @PostMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "根据ID单个删除数据字典", notes = "根据ID单个删除数据字典")
    public ResponseEntity removeSysRoleInfoByIds(@RequestBody SysDataDictionary sysDataDictionary, HttpServletRequest request) throws IOException {

        List<Integer> ids =  sysDataDictionary.getIds();
        QueryWrapper<SysDataDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);

        List<SysDataDictionary> list =  sysDataDictionaryService.list(queryWrapper);

        sysDataDictionaryService.remove(queryWrapper);

        for(SysDataDictionary sysDataDictionary_entity : list) {
            removeSysDataDictionary(sysDataDictionary_entity);
        }
        return new ResponseEntity<String>("ok", HttpStatus.valueOf(HttpStatusConstant.OK_CODE));
    }

    private void removeSysDataDictionary(SysDataDictionary sysDataDictionary) {

        String paraType =  sysDataDictionary.getParaType();
        String paraValue = sysDataDictionary.getParaValue();

        QueryWrapper<SysDataDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentParaType",paraType);
        queryWrapper.eq("parentParaValue",paraValue);

        List<SysDataDictionary> list =  sysDataDictionaryService.list(queryWrapper);

        sysDataDictionaryService.remove(queryWrapper);

        for(SysDataDictionary sysDataDictionary_entity : list) {
            removeSysDataDictionary(sysDataDictionary_entity);
        }
    }
}
