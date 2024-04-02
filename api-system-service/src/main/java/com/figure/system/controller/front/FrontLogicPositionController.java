package com.figure.system.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.front.FrontLogicPosition;
import com.figure.core.service.front.IFrontLogicPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import com.figure.core.util.page.TableDataInfo;

/**
 * <p>
 * 采集点管理 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-19 16:32:59
 */
@RestController
@RequestMapping("/frontLogicPosition")
@Api(value = "采集点管理相关接口" , tags = "采集点管理相关接口")
public class FrontLogicPositionController extends BaseController {

    @Resource
    private IFrontLogicPositionService frontLogicPositionService;

    /**
     * 根据positionId获取采集点
     *
     * @param positionId 采集点ID
     * @return 采集点
     */
    @GetMapping("/get/{positionId}")
    @ApiOperation(value = "根据positionId获取采集点" , notes = "根据positionId获取采集点")
    public FrontLogicPosition selectFrontLogicPositionById(@PathVariable("positionId") Long positionId) {
        return frontLogicPositionService.getById(positionId);
    }

    /**
     * 查询采集点列表
     *
     * @param request
     * @return 采集点集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询采集点列表" , notes = "查询采集点列表")
    public TableDataInfo<FrontLogicPosition> selectFrontLogicPositionList(HttpServletRequest request) throws Exception {
        PageWrapper<FrontLogicPosition> pageWrapper =  new PageHelper(FrontLogicPosition.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        queryWrapper.eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);

        IPage<FrontLogicPosition> pages = frontLogicPositionService.page(pageWrapper.getPage(),queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存采集点
     *
     * @param frontLogicPosition 采集点
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存采集点" , notes = "新增保存采集点")
    public Map<String,Object> insertFrontLogicPosition(@RequestBody FrontLogicPosition frontLogicPosition) {
        initCreateProps(frontLogicPosition);
        return returnMap(frontLogicPositionService.save(frontLogicPosition));
    }

    /**
     * 修改保存采集点
     *
     * @param frontLogicPosition 采集点
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存采集点" , notes = "修改保存采集点")
    public Map<String,Object> updateFrontLogicPositionById(@RequestBody FrontLogicPosition frontLogicPosition) {
        initUpdateProps(frontLogicPosition);
        return returnMap(frontLogicPositionService.updateById(frontLogicPosition));
    }

    /**
     * 批量删除采集点
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除采集点" , notes = "删除采集点")
    public Map<String,Object> removeFrontLogicPositionByIds(String ids) {
        return returnMap(frontLogicPositionService.update(getSoftDeleteUpdateWrapperByIds("positionId",ids,1)));
    }



    /**
     * 同步采集点
     * @return 结果
     */
    @PostMapping("/sync")
    @ApiOperation(value = "同步采集点" , notes = "同步采集点")
    public Map<String,Object> sync() {
        Map<String, Object> result = getRestTemplate().getForObject(monitorService_url+"/net/syncFrontLogicPosition", Map.class);
        return result;
    }

}