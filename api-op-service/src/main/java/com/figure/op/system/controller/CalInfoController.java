package com.figure.op.system.controller;

import com.figure.op.common.domain.R;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.system.domain.bo.PriceQueryBo;
import com.figure.op.system.domain.bo.StaticsQueryBo;
import com.figure.op.system.domain.vo.*;
import com.figure.op.system.service.IBudgetInfoService;
import com.figure.op.system.service.IOutPriceInfoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资金可视化数据统计控制器
 * @author fsn
 */
@RestController
@RequestMapping("/cal")
public class CalInfoController {

    @Resource
    private IBudgetInfoService budgetInfoService;

    @Resource
    private IOutPriceInfoService outPriceInfoService;


    /**
     * 入账、出账柱状图
     */
    @GetMapping("/price")
    public R<List<HashMap<String, Object>>> price(String type, @DateTimeFormat(pattern = "yyyy-MM-dd")Date start, @DateTimeFormat(pattern = "yyyy-MM-dd")Date end) {
        String timeStr = "%Y%m";
        Calendar calendar = Calendar.getInstance();
        switch (type) {
            case "TODAY":
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                start = calendar.getTime();
                calendar.set(Calendar.HOUR, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                end = calendar.getTime();
                timeStr = "%Y/%m/%d";
                break;
            case "MONTH":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                start = calendar.getTime();
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                end = calendar.getTime();
                timeStr = "%Y/%m/%d";
                break;
            case "YEAR":
                calendar.set(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                start = calendar.getTime();
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                end = calendar.getTime();
                timeStr = "%Y/%m";
                break;
            case "CUSTOM":
                // 自定义情况下 若不传时间则查询全部
                if (start != null && end != null){
                    // 结束时间调整为当天23:59:59
                    calendar.setTime(end);
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    end = calendar.getTime();

                    Period between = Period.between(start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                            end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    if (between.getDays() > 30) {
                        timeStr = "%Y/%m";
                    }
                }
                break;
            default:
                throw new ServiceException("查询时间类型异常");
        }
        PriceQueryBo priceQueryBo = new PriceQueryBo();
        priceQueryBo.setTimeStr(timeStr);
        priceQueryBo.setEnd(end);
        priceQueryBo.setStart(start);
        // 入账（按审核时间查询）
        Map<String, BigDecimal> bPrice = budgetInfoService.calPrice(priceQueryBo).stream().collect(Collectors.toMap(PriceVo::getTime, PriceVo::getAmount));
        // 出账（按审核时间查询）
        Map<String, BigDecimal> oPrice = outPriceInfoService.calPrice(priceQueryBo).stream().collect(Collectors.toMap(PriceVo::getTime, PriceVo::getAmount));
        Set<String> bKey = bPrice.keySet();
        Set<String> oKey = oPrice.keySet();
        Set<String> keys = new HashSet<String>();
        bKey.forEach(item -> {
            keys.add(item);
        });
        oKey.forEach(item -> {
            keys.add(item);
        });
        List<HashMap<String, Object>> res = new ArrayList<>();
        for (String key:keys) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("timeStr", key);
            map.put("in", bPrice.get(key));
            map.put("out", oPrice.get(key));
            res.add(map);
        }
        return R.ok(res);
    }

    /**
     * 统计
     */
    @GetMapping("/statics")
    private R<HashMap<String, Object>> statics(StaticsQueryBo bo) {

        // 查询时间处理 所有结束时间调整为当天23:59:59
        Calendar calendar = Calendar.getInstance();
        if (bo.getStaEndTime() != null){
            calendar.setTime(bo.getStaEndTime());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            bo.setStaEndTime(calendar.getTime());
        }
        if (bo.getSourceEndTime() != null){
            calendar.setTime(bo.getSourceEndTime());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            bo.setSourceEndTime(calendar.getTime());
        }
        if (bo.getUseEndTime() != null){
            calendar.setTime(bo.getUseEndTime());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            bo.setUseEndTime(calendar.getTime());
        }

        HashMap<String, Object> res = new HashMap<>();
        // 总入账
        BigDecimal inTotal = budgetInfoService.getTotalByPlanWithoutOrgan(String.valueOf(0), bo.getStaStartTime(), bo.getStaEndTime());
        // 总预算
        BigDecimal planTotal = budgetInfoService.getTotalByPlanWithoutOrgan(String.valueOf(1), bo.getStaStartTime(), bo.getStaEndTime());
        // 总出账
        BigDecimal outTotal = outPriceInfoService.getTotalByPlanWithoutOrgan(bo.getStaStartTime(), bo.getStaEndTime());

        // 资金使用情况
        List<Map<String, Object>> sumGroupByUseInfo = outPriceInfoService.getSumGroupByUseInfo(bo.getUseStartTime(), bo.getUseEndTime());

        if (sumGroupByUseInfo.size() > 0) {
            for (Map<String, Object> item : sumGroupByUseInfo) {
                if (item.get("total") == null || outTotal == null) {
                    item.put("percent", "0");
                } else {
                    BigDecimal total = (BigDecimal)item.get("total");
                    BigDecimal v = total.multiply(BigDecimal.valueOf(100)).divide(outTotal, 2, BigDecimal.ROUND_DOWN);
                    item.put("percent", String.valueOf(v));
                }

            }
        }

        // 资金来源
        List<PercentVo> percentVos = budgetInfoService.getTotalGroupBySource(String.valueOf(0), bo.getSourceStartTime(), bo.getSourceEndTime());

        res.put("ALL_IN", inTotal == null ? 0 : inTotal);
        res.put("ALL_OUT", outTotal == null ? 0 : outTotal);
        if (inTotal == null || planTotal == null) {
            res.put("DAO_WEI", 0);
        } else {
            res.put("DAO_WEI", inTotal.multiply(BigDecimal.valueOf(100)).divide(planTotal, 2, BigDecimal.ROUND_DOWN));
        }
        if (inTotal == null || outTotal == null) {
            res.put("ZHI_XING", 0);
        } else {
            res.put("ZHI_XING", outTotal.multiply(BigDecimal.valueOf(100)).divide(inTotal, 2, BigDecimal.ROUND_DOWN));
        }
        if (inTotal == null) {
            inTotal = new BigDecimal("0");
        }
        if (outTotal == null){
            outTotal = new BigDecimal("0");
        }
        res.put("JIE_YV", inTotal.subtract(outTotal));
        res.put("SOURCE_PERCENT", percentVos);
        res.put("USE_PERCENT", sumGroupByUseInfo);
        return R.ok(res);
    }


}
