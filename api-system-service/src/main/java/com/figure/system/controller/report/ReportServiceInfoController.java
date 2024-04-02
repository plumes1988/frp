package com.figure.system.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.figure.core.base.BaseController;
import com.figure.core.constant.Constants;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.report.ReportServiceInfo;
import com.figure.core.model.report.ReportServiceInfoList;
import com.figure.core.query.report.ReportServiceInfoQuery;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.producer.ServiceParameterUpdateP2SProducer;
import com.figure.core.rocketmq.struct.producer.ServiceRestartSetP2SProducer;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.report.IReportServiceInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 上报服务管理 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
@RestController
@RequestMapping("/reportServiceInfo")
@Api(value = "上报服务管理相关接口" , tags = "上报服务管理相关接口")
public class ReportServiceInfoController extends BaseController {

    @Resource
    private IReportServiceInfoService reportServiceInfoService;

    /**
     * 根据id获取上报服务管理
     *
     * @param id 上报服务管理ID
     * @return 上报服务管理
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取上报服务管理", notes = "根据id获取上报服务管理")
    public ReportServiceInfo selectReportServiceInfoById(@PathVariable("id") Integer id) {
        return reportServiceInfoService.getById(id);
    }

    /**
     * 根据id获取上报服务管理
     *
     * @param id 上报服务管理ID
     * @return 上报服务管理
     */
    @GetMapping("/getDetailById/{id}")
    @ApiOperation(value = "根据id获取上报服务管理", notes = "根据id获取上报服务管理")
    public Map<String, Object> selectReportServiceDetailById(@PathVariable("id") Integer id) {
        return returnMap(true, this.reportServiceInfoService.getDetailById(id));
    }

    /**
     * 查询上报服务管理列表
     *
     * @param reportServiceInfoQuery 上报服务管理
     * @return 上报服务管理集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询上报服务管理列表", notes = "查询上报服务管理列表")
    public TableDataInfo<ReportServiceInfo> selectReportServiceInfoList(ReportServiceInfoQuery reportServiceInfoQuery) {
        return toPageResult(reportServiceInfoService.list(reportServiceInfoQuery.autoPageWrapper()));
    }
    /**
     * 查询上报服务设备管理列表
     *
     * @param reportServiceInfoQuery 上报服务设备管理
     * @return 上报服务设备管理集合
     */
    @GetMapping("/listrel")
    @ApiOperation(value = "查询上报服务设备管理列表", notes = "查询上报服务设备管理列表")
    public TableDataInfo<ReportServiceInfoList> selectReportServiceInfoListRel(ReportServiceInfoQuery reportServiceInfoQuery) {
        return toPageResult(reportServiceInfoService.listRel(reportServiceInfoQuery.autoPageWrapper()));
    }

    @Resource
    private IDeviceInfoService deviceInfoService;

    /**
     * 新增保存上报服务管理
     *
     * @param reportServiceInfo 上报服务管理
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存上报服务管理", notes = "新增保存上报服务管理")
    public Map<String, Object> insertReportServiceInfo(@RequestBody ReportServiceInfo reportServiceInfo) {
        if (StringUtils.isNotNull(reportServiceInfo.getServiceCodes())) {
            for (String serviceCode : reportServiceInfo.getServiceCodes()) {
                LambdaQueryWrapper<DeviceInfo> deviceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
                deviceInfoLambdaQueryWrapper.eq(DeviceInfo::getDeviceCode, serviceCode).eq(DeviceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
                DeviceInfo deviceInfo = this.deviceInfoService.getOne(deviceInfoLambdaQueryWrapper);
                if (deviceInfo != null) {
                    ReportServiceInfo serviceInfo = reportServiceInfo;
                    serviceInfo.setServiceCode(serviceCode);
                    serviceInfo.setServiceName(deviceInfo.getDeviceName());
                    serviceInfo.setDeviceID(deviceInfo.getDeviceId());
                    serviceInfo.setServiceIP(deviceInfo.getControlIP());
                    this.reportServiceInfoService.save(serviceInfo);
                }
            }
            return returnMap(true);
        }
        return returnMap(reportServiceInfoService.save(reportServiceInfo), reportServiceInfo.getId());
    }

     /**
     * 批量新增保存上报服务管理
     *
     * @param reportServiceInfo 上报服务管理
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存上报服务管理" , notes = "批量新增保存上报服务管理")
    public Map<String,Object> saveBatchReportServiceInfo(@RequestBody List<ReportServiceInfo> reportServiceInfo) {
        return returnMap(reportServiceInfoService.saveBatch(reportServiceInfo), reportServiceInfo);
    }
  
    /**
     * 修改保存上报服务管理
     *
     * @param reportServiceInfo 上报服务管理
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存上报服务管理" , notes = "修改保存上报服务管理")
    public Map<String,Object> updateReportServiceInfoById(@RequestBody ReportServiceInfo reportServiceInfo) {
        return returnMap(reportServiceInfoService.updateById(reportServiceInfo));
    }

    /**
     * 批量删除上报服务管理
     *
     * @param reportServiceInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除上报服务管理", notes = "删除上报服务管理")
    public Map<String, Object> removeReportServiceInfoByIds(@RequestBody ReportServiceInfo reportServiceInfo) {
        return returnMap(reportServiceInfoService.removeByIds(StringUtils.StringToIntList(reportServiceInfo.getIds())));
    }

    @Resource
    private RocketMQProducer rocketMQProducer;

    /**
     * 下发重启
     *
     * @param reportServiceInfo 下发频谱参数
     * @return 结果
     */
    @PostMapping("/serviceRestartSet")
    @ApiOperation(value = "下发频谱参数", notes = "下发频谱参数")
    public Map<String, Object> serviceRestartSet(@RequestBody ReportServiceInfo reportServiceInfo) {
        ServiceRestartSetP2SProducer producer = new ServiceRestartSetP2SProducer(reportServiceInfo.getServiceCode());
        String result = rocketMQProducer.send(producer.getRocketmqTopic(), producer.getRocketmqTag(), producer);
        if (result == null) {
            return returnMap(true);
        } else {
            return returnMap(false);
        }
    }

    /**
     * 下发参数更新
     *
     * @param reportServiceInfo 下发参数更新
     * @return 结果
     */
    @PostMapping("/serviceParamUpdate")
    @ApiOperation(value = "下发频谱参数", notes = "下发频谱参数")
    public Map<String, Object> serviceParamUpdate(@RequestBody ReportServiceInfo reportServiceInfo) {
        ServiceParameterUpdateP2SProducer producer = new ServiceParameterUpdateP2SProducer(reportServiceInfo.getServiceCode());
        String result = rocketMQProducer.send(producer.getRocketmqTopic(), producer.getRocketmqTag(), producer);
        if (result == null) {
            return returnMap(true);
        } else {
            return returnMap(false);
        }
    }
}