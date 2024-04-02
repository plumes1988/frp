package com.figure.op.dict.controller;


import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.domain.vo.data.*;
import com.figure.op.dict.service.DictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import com.figure.op.common.domain.R;

/**
 * 管理后台 - 字典数据
 */
@RestController
@RequestMapping("/dict-data")
@Validated
@Api(tags = "字典数据")
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    /**
     * 新增字典数据
     *
     * @param reqVO 请求对象
     * @return 主键id
     */
    @PostMapping("/create")
    @ApiOperation(value = "新增字典数据")
    public R<Long> createDictData(@Valid @RequestBody DictDataCreateReqVO reqVO) {
        Long dictDataId = dictDataService.createDictData(reqVO);
        return R.ok(dictDataId);
    }

    /**
     * 修改字典数据
     *
     * @param reqVO 请求对象
     * @return 操作结果 true=成功 false=失败
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改字典数据")
    public R<Boolean> updateDictData(@Valid @RequestBody DictDataUpdateReqVO reqVO) {
        dictDataService.updateDictData(reqVO);
        return R.ok(true);
    }

    /**
     * 删除字典数据
     *
     * @param id 主键id
     * @return 操作结果 true=成功 false=失败
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除字典数据")
    public R<Boolean> deleteDictData(Long id) {
        dictDataService.deleteDictData(id);
        return R.ok(true);
    }

    /**
     * 获得全部字典数据列表
     *
     * @return 数据列表
     */
    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典数据列表")
    public R<List<DictDataSimpleRespVO>> getSimpleDictDataList(@RequestParam(name = "type", required = false) String type) {
        List<DictDataDO> list = dictDataService.getDictDataList(type);
        return R.ok(BeanCopyUtils.copyList(list, DictDataSimpleRespVO.class));
    }

    /**
     * 获得字典类型的分页列表
     *
     * @param reqVO 查询条件请求对象
     * @return 分页列表
     */
    @GetMapping("/page")
    @ApiOperation(value = "获得字典类型的分页列表")
    public TableDataInfo<DictDataDO> getDictTypePage(@Valid DictDataPageReqVO reqVO, PageQuery pageQuery) {
        return dictDataService.getDictDataPage(reqVO, pageQuery);
    }

    /**
     * 查询字典数据详细
     *
     * @param id 主键id
     * @return 字典数据详细
     */
    @GetMapping(value = "/get")
    @ApiOperation(value = "查询字典数据详细")
    public R<DictDataDO> getDictData(@RequestParam("id") Long id) {
        return R.ok(dictDataService.getDictData(id));
    }


}
