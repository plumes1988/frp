package com.figure.system.controller.base;


import com.figure.core.base.BaseController;
import com.figure.core.model.base.BaseDataBackupTask;
import com.figure.core.service.base.IBaseDataBackupTaskService;
import com.figure.core.util.page.TableDataInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.figure.system.service.BackupTaskService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/baseDataBackupTask")
@Api(value = "归档任务相关接口" , tags = "归档任务相关接口")
public class BaseDataBackupTaskController extends BaseController {

    @Resource
    private IBaseDataBackupTaskService baseDataBackupTaskService;

    @Resource
    BackupTaskService backupTaskService;

    /**
     * 根据id获取归档任务
     *
     * @param id 归档任务ID
     * @return 归档任务
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取归档任务" , notes = "根据id获取归档任务")
    public BaseDataBackupTask selectBaseDataBackupTaskById(@PathVariable("id") Long id) {
        return baseDataBackupTaskService.getById(id);
    }

    /**
     * 查询归档任务列表
     *
     * @param request 请求
     * @return 归档任务集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询归档任务列表" , notes = "查询归档任务列表")
    public TableDataInfo selectBaseDataBackupTaskList(HttpServletRequest request) throws Exception {

        PageWrapper<BaseDataBackupTask> pageWrapper =  new PageHelper(BaseDataBackupTask.class).getPageWrapper(request);

        IPage<BaseDataBackupTask> pages = baseDataBackupTaskService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存归档任务
     *
     * @param baseDataBackupTask 归档任务
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存归档任务" , notes = "新增保存归档任务")
    public Map<String,Object> insertBaseDataBackupTask(@RequestBody BaseDataBackupTask baseDataBackupTask) {
        initCreateProps(baseDataBackupTask);
        baseDataBackupTask.setDateOfYear(baseDataBackupTask.getDateOfYear_month()+" "+baseDataBackupTask.getDateOfYear_day());
        baseDataBackupTaskService.save(baseDataBackupTask);
        backupTaskService.addTaskJob(baseDataBackupTask.getId());
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",baseDataBackupTask.getId());
        return result;
    }

    /**
     * 修改保存归档任务
     *
     * @param baseDataBackupTask 归档任务
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存归档任务" , notes = "修改保存归档任务")
    public Map<String,Object> updateBaseDataBackupTaskById(@RequestBody BaseDataBackupTask baseDataBackupTask) {
        baseDataBackupTaskService.updateById(baseDataBackupTask);
        backupTaskService.modifyTaskJob(baseDataBackupTask.getId());
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",baseDataBackupTask.getId());
        return result;
    }

    /**
     * 批量删除归档任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除归档任务" , notes = "删除归档任务")
    public boolean removeBaseDataBackupTaskByIds(String ids) {
        List<String> idList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids));

        List<BaseDataBackupTask>  baseDataBackupTasks =  baseDataBackupTaskService.listByIds(idList);
        for(BaseDataBackupTask baseDataBackupTask:baseDataBackupTasks){
            backupTaskService.deleteTaskJob(baseDataBackupTask.getId());
        }

        boolean result = baseDataBackupTaskService.removeByIds(idList);
        return result;
    }

}
