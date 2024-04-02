package com.figure.system.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.front.FrontStationInfo;
import com.figure.core.service.front.IFrontIllegalFrequencyService;
import com.figure.core.service.front.IFrontStationInfoService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-05-20
 */
@RestController
@RequestMapping("/frontStationInfo")
public class FrontStationInfoController extends BaseController {

    @Autowired
    IFrontStationInfoService frontStationInfoService;

    @Resource
    private IFrontIllegalFrequencyService frontIllegalFrequencyService;

    /**
     * 查询前端列表
     *
     * @param request http请求
     * @return 前端集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询前端列表" , notes = "查询前端列表")
    public TableDataInfo selectFrontStationInfoList(HttpServletRequest request) throws Exception {
        PageWrapper<FrontStationInfo> pageWrapper =  new PageHelper(FrontStationInfo.class).getPageWrapper(request);
        permissionsFilterByFrontIds(request,pageWrapper.getQueryWrapper());
        IPage<FrontStationInfo> pages = frontStationInfoService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());
        return new TableDataInfo(pages);
    }




    /**
     * 新增保存前端
     *
     * @param frontStationInfo 前端
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存前端" , notes = "新增保存前端")
    public Map<String,Object> insertFrontStationInfo(@RequestBody FrontStationInfo frontStationInfo) {
        frontStationInfoService.save(frontStationInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",frontStationInfo.getFrontId());
        return result;
    }


    /**
     * 修改保存前端
     *
     * @param frontStationInfo 前端
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存前端" , notes = "修改保存前端")
    public Map<String,Object> updateFrontStationInfoById(@RequestBody FrontStationInfo frontStationInfo) {
        if(frontStationInfo.getFrontId() == null || frontStationInfo.getFrontId()<=0){
            frontStationInfo.setFrontId(null);
            return insertFrontStationInfo(frontStationInfo);
        }
        if(frontStationInfo.getWorkMode()==null || frontStationInfo.getWorkMode()!=1){
            frontIllegalFrequencyService.removeById(frontStationInfo.getFrontId());
        }
        frontStationInfoService.updateById(frontStationInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",frontStationInfo.getFrontId());
        return result;
    }

    /**
     * 批量删除前端
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除前端" , notes = "删除前端")
    public Map<String,Object> removeFrontStationInfoByIds(String ids) {
        return returnMap(frontStationInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
