package com.figure.system.controller.base;


import com.figure.core.base.BaseController;
import com.figure.core.model.base.BaseTableBackup;
import com.figure.core.service.base.IBaseTableBackupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/baseTableBackup")
@Api(value = "归档表相关接口" , tags = "归档表相关接口")
public class BaseTableBackupController extends BaseController {

    @Resource
    private IBaseTableBackupService baseDataBackupTaskService;


    /**
     * 查询归档表列表
     *
     * @param baseDataBackupTask 归档表
     * @return 归档表集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询归档表列表" , notes = "查询归档表列表")
    public List<BaseTableBackup> selectBaseTableBackupList(BaseTableBackup baseDataBackupTask) {
        return baseDataBackupTaskService.list();
    }



}
