package com.figure.system.controller.base;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.base.BaseDataBackup;
import com.figure.core.service.base.IBaseDataBackupService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/baseDataBackup")
@Api(value = "归档数据相关接口" , tags = "归档数据相关接口")
public class BaseDataBackupController extends BaseController {
    @Resource
    private IBaseDataBackupService baseDataBackupService;

    /**
     * 查询归档数据列表
     *
     * @param baseDataBackup 归档数据
     * @return 归档数据集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询归档数据列表" , notes = "查询归档数据列表")
    public TableDataInfo selectBaseDataBackupList(BaseDataBackup baseDataBackup, HttpServletRequest request) throws Exception {

        PageWrapper<BaseDataBackup> pageWrapper =  new PageHelper(BaseDataBackup.class).getPageWrapper(request);

        IPage<BaseDataBackup> pages = baseDataBackupService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());

        initUserInfo(pages.getRecords(),"excuteUserId");


        return new TableDataInfo(pages);
    }

    /**
     * 批量删除归档数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除归档数据" , notes = "删除归档数据")
    public boolean removeBaseDataBackupByIds(String ids) {
        return baseDataBackupService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }
}
