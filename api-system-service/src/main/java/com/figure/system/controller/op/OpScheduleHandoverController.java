package com.figure.system.controller.op;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.audit.AuditAutoTask;
import com.figure.core.model.op.OpScheduleHandover;
import com.figure.core.service.op.IOpScheduleHandoverService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交接班 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/opScheduleHandover")
public class OpScheduleHandoverController  extends BaseController {

    @Resource
    private IOpScheduleHandoverService opScheduleHandoverService;

    @Autowired
    ICommonService commonService;

    /**
     * 根据id获取交接班
     *
     * @param id 媒介ID
     * @return 交接班s
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取交接班", notes = "根据id获取交接班")
    public OpScheduleHandover selectOpScheduleHandoverById(@PathVariable("id") Long id) {
        return opScheduleHandoverService.getById(id);
    }

    /**sss
     * 查询交接班列表
     *
     * @param request
     * @return 交接班集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询交接班列表", notes = "查询交接班列表")
    public TableDataInfo<OpScheduleHandover> selectOpScheduleHandoverList(HttpServletRequest request) throws Exception {

        PageWrapper pageWrapper =  new PageHelper(OpScheduleHandover.class, PageHelper.ConditionType.MAP).getPageWrapper(request);
        IPage<Map<String,Object>> pages = opScheduleHandoverService.selectPage(pageWrapper.getPage(),pageWrapper.getMap());
        for(Map<String,Object> r : pages.getRecords()){
            r.put("startTime",r.get("startTime").toString());
            r.put("endTime",r.get("endTime").toString());
            r.put("day",r.get("day").toString());
        }
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存交接班
     *
     * @param opSchedulePlan 交接班
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存交接班", notes = "新增保存交接班")
    public Map<String, Object> insertOpScheduleHandover(@RequestBody OpScheduleHandover opSchedulePlan) {
        opScheduleHandoverService.save(opSchedulePlan);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",opSchedulePlan.getId());
        return result;
    }

    /**
     * 修改保存交接班
     *
     * @param opSchedulePlan 交接班
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存交接班", notes = "修改保存交接班")
    public Map<String, Object> updateOpScheduleHandoverById(@RequestBody OpScheduleHandover opSchedulePlan) {
        return returnMap(opScheduleHandoverService.updateById(opSchedulePlan));
    }

    /**
     * 批量删除交接班
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除交接班", notes = "删除交接班")
    public Map<String, Object> removeOpScheduleHandoverByIds(String ids) {
        return returnMap(opScheduleHandoverService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

    /**
     * 接班排班获取交班排班日志
     *
     * @param schedulerId 接班排班ID
     * @return 结果
     */
    @GetMapping("/getTurnoverByHandOverScheduler/{schedulerId}")
    @ApiOperation(value = "删除交接班", notes = "删除交接班")
    public Map<String, Object> getTurnoverByHandOverScheduler(@PathVariable("schedulerId") Long schedulerId) {
        Map<String, Object> r = null;
        List<Map<String,Object>> results = commonService.queryTable("t1.day,t2.startTime","op_schedule_scheduler t1 left join op_schedule_plan t2 on t1.pid = t2.pid","t1.id = "+schedulerId);
        if(results.size()>0){
            String day = results.get(0).get("day").toString();
            String startTime = results.get(0).get("startTime").toString();
            System.out.println("day:"+day+" "+"startTime:"+startTime);
            String dateTime = day+" "+startTime;
            results = commonService.queryTable("t0.id,t0.stateChecks,t0.log,t0.schedulerId,t0.takeOverSchedulerId,t0.mark,t3.userName, t1.day,t2.startTime,t2.endTime",
                    "op_schedule_handover t0 left join op_schedule_scheduler t1 on t0.schedulerId=t1.id left join op_schedule_plan t2 on t1.pid = t2.pid left join sys_user_info t3 on t1.userId = t3.userId",
                    "CONCAT(t1.day,\" \",t2.endTime)<='"+dateTime+"' and (t0.takeOverSchedulerId is null or t0.takeOverSchedulerId="+schedulerId+" ) order by CONCAT(t1.day,\" \",t2.endTime) desc");
            for(Map<String,Object> map:results){
                if(map.get("takeOverSchedulerId")!=null){
                    r = map;
                }
            }
            if(r==null&&results.size()>0){
                r = results.get(0);
            }
            if(r!=null){
               r.put("startTime",r.get("startTime").toString());
               r.put("endTime",r.get("endTime").toString());
               r.put("day",r.get("day").toString());
            }
        }
        return r;
    }

}
