package com.figure.op.dict.controller;


import cn.hutool.db.PageResult;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.dict.domain.DictTypeDO;
import com.figure.op.dict.domain.vo.type.*;
import com.figure.op.dict.service.DictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


/**
 * 管理后台 - 字典类型
 */
@RestController
@RequestMapping("/dict-type")
@Validated
@Api(tags = "字典类型")
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    /**
     * 创建字典类型
     *
     * @param reqVO
     * @return
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建字典类型")
    public R<Long> createDictType(@Valid @RequestBody DictTypeCreateReqVO reqVO) {
        Long dictTypeId = dictTypeService.createDictType(reqVO);
        return R.ok(dictTypeId);
    }

    /**
     * 修改字典类型
     *
     * @param reqVO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改字典类型")
    public R<Boolean> updateDictType(@Valid @RequestBody DictTypeUpdateReqVO reqVO) {
        dictTypeService.updateDictType(reqVO);
        return R.ok(true);
    }

    /**
     * 删除字典类型
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除字典类型")
    public R<Boolean> deleteDictType(Long id) {
        dictTypeService.deleteDictType(id);
        return R.ok(true);
    }

    /**
     * 获得字典类型的分页列表
     *
     * @param reqVO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "获得字典类型的分页列表")
    public TableDataInfo<DictTypeDO> pageDictTypes(@Valid DictTypePageReqVO reqVO, PageQuery pageQuery) {
        return dictTypeService.getDictTypePage(reqVO, pageQuery);
    }

    /**
     * 查询字典类型详细
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get")
    @ApiOperation(value = "查询字典类型详细")
    public R<DictTypeDO> getDictType(@RequestParam("id") Long id) {
        return R.ok(dictTypeService.getDictType(id));
    }

    /**
     * 获得全部字典类型列表
     *
     * @return
     */
    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典类型列表")
    public R<List<DictTypeSimpleRespVO>> getSimpleDictTypeList() {
        List<DictTypeDO> list = dictTypeService.getDictTypeList();
        return R.ok(BeanCopyUtils.copyList(list, DictTypeSimpleRespVO.class));
    }


}
