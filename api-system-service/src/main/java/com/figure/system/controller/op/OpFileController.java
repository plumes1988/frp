package com.figure.system.controller.op;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.model.op.OpFile;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.service.op.IOpFileService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;
import com.figure.core.base.BaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/opFile")
public class OpFileController extends BaseController{

    @Resource
    private IOpFileService opFileService;


    /**
     * 根据id获取opFile
     *
     * @param id opFileID
     * @return opFile
     */
    @GetMapping("/get/{fileId}")
    @ApiOperation(value = "根据fileId获取opFile", notes = "根据fileId获取opFile")
    public OpFile selectOpFileById(@PathVariable("fileId") Long id) {
        return opFileService.getById(id);
    }

    /**
     * 查询opFile列表
     *
     * @param request
     * @return opFile集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询opFile列表", notes = "查询opFile列表")
    public TableDataInfo<OpFile> selectOpFileList(HttpServletRequest request) throws Exception {
        PageWrapper<OpFile> pageWrapper = new PageHelper(OpFile.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<OpFile> pages = opFileService.page(pageWrapper.getPage(), queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存opFile
     *
     * @param opFile opFile
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存opFile", notes = "新增保存opFile")
    public Map<String, Object> insertOpFile(@RequestBody OpFile opFile) {
        initCreateProps(opFile);
        opFileService.save(opFile);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",opFile.getFileId());
        return result;
    }

    /**
     * 修改保存opFile
     *
     * @param opFile opFile
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存opFile", notes = "修改保存opFile")
    public Map<String, Object> updateOpFileById(@RequestBody OpFile opFile) {
        QueryWrapper queryWrapper  = new QueryWrapper();
        queryWrapper.eq("fileId",opFile.getFileId());
        return returnMap(opFileService.update(opFile,queryWrapper));
    }

    /**
     * 批量删除opFile
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除opFile", notes = "删除opFile")
    public Map<String, Object> removeOpFileByIds(String ids) {
        return returnMap(opFileService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }
}
