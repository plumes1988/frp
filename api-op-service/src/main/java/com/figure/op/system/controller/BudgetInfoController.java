package com.figure.op.system.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.enums.CommonStatusEnum;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.service.DictDataService;
import com.figure.op.system.domain.BudgetInfo;
import com.figure.op.system.domain.bo.BudgetInfoBo;
import com.figure.op.system.domain.bo.BudgetInfoQueryBo;
import com.figure.op.system.domain.bo.BudgetInfoStatusBo;
import com.figure.op.system.domain.bo.ReportBo;
import com.figure.op.system.domain.vo.*;
import com.figure.op.system.service.IBudgetInfoService;
import com.figure.op.system.service.IOutPriceInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资金预算管理控制器
 * @author fsn
 */
@RestController
@RequestMapping("/budgetInfo")
public class BudgetInfoController {

    @Resource
    private IBudgetInfoService budgetInfoService;

    @Resource
    private IOutPriceInfoService outPriceInfoService;

    @Value("${file.path}")
    private String path;

    @Value("${file.url_prefix}")
    private String urlPrefix;

    @Resource
    private DictDataService dictDataService;

    /**
     * 分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<BudgetInfoPageVo> page(BudgetInfoQueryBo queryBo, PageQuery pageQuery) {
        return budgetInfoService.page(queryBo, pageQuery);
    }

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<BudgetInfoListVo>> list(BudgetInfo BudgetInfo) {
        return R.ok(budgetInfoService.queryList(BudgetInfo));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<BudgetInfoVo> info(@PathVariable Integer id) {
        return R.ok(budgetInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BudgetInfoBo bo) {
        return R.toAjax(budgetInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BudgetInfoBo bo) {
        return R.toAjax(budgetInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(budgetInfoService.deleteById(id));
    }


    /**
     * 审核状态变更  0 待提交 1 待审核 2 审核通过 3审核退回
     */
    @PutMapping("/updateStatus")
    public R<Void> updateStatus(@RequestBody BudgetInfoStatusBo bo) {
        return R.toAjax(budgetInfoService.updateStatus(bo));
    }

    /**
     * 操作历史时间线
     */
    @GetMapping("/operateTimelineList/{budgetId}")
    public R<List<OperateTimelineListVo>> operateTimelineList(@PathVariable Integer budgetId) {
        return R.ok(budgetInfoService.getOperateTimelineList(budgetId));
    }

    @GetMapping("/report")
    public R<String> report(ReportBo reportBo, HttpServletResponse response) throws Exception {
        InputStream inputStream = new ClassPathResource("static/report.xls").getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
        List<List<Object>> read = reader.read();

        BigDecimal planTotal = budgetInfoService.getTotalByPlan("1", reportBo.getActDepart(), reportBo.getStartTime(), reportBo.getEndTime());
        BigDecimal outTotal = outPriceInfoService.getTotalByPlan(reportBo.getActDepart(), reportBo.getStartTime(), reportBo.getEndTime());
        List<Map<String, Object>> sumGroupBySource = outPriceInfoService.getSumGroupBySource(reportBo.getActDepart(), reportBo.getStartTime(), reportBo.getEndTime());
        List<PercentVo> totalGroupBySource = budgetInfoService.getTotalGroupBySource("1",reportBo.getActDepart(), reportBo.getStartTime(), reportBo.getEndTime());

//        if (sumGroupBySource == null || totalGroupBySource == null) {
//            return R.fail("暂无数据，无法导出报表");
//        }
        Set<String> collect = totalGroupBySource.stream().map(PercentVo::getSource).collect(Collectors.toSet());
        Set<String> source = sumGroupBySource.stream().map(item -> item.get("source").toString()).collect(Collectors.toSet());
        if (collect.isEmpty()) {
            collect = new HashSet<>();
        }
        if (!source.isEmpty()) {
            collect.addAll(source);
        }

        HashMap<String, String> budget = new HashMap<>();
        totalGroupBySource.forEach(item -> budget.put(item.getSource(), item.getAmount().toString()));

        HashMap<String, String> out = new HashMap<>();
        sumGroupBySource.forEach(item -> out.put(item.get("source").toString(), item.get("total").toString()));

        List<Map> res = new ArrayList<>();
        for (String item : collect) {
            HashMap<String, String> hashMap = new HashMap<>();
            BigDecimal budgetT = new BigDecimal("0");
            BigDecimal outT = new BigDecimal("0");
            BigDecimal percent = new BigDecimal("0");
            if (budget.get(item) != null) {
                budgetT = new BigDecimal(budget.get(item));
            }
            if (out.get(item) != null) {
                outT = new BigDecimal(out.get(item));
            }
            if (budget.get(item) != null && out.get(item) != null) {
                percent = outT.multiply(BigDecimal.valueOf(100)).divide(budgetT, 2, BigDecimal.ROUND_DOWN);
            }
            hashMap.put("source", item);
            hashMap.put("budget", budgetT.toPlainString());
            hashMap.put("out", outT.toPlainString());
            hashMap.put("percent", percent.toPlainString() + "%");
            res.add(hashMap);
        }

        for (int i = 0; i < read.size(); i++) {
            List<Object> row = read.get(i);
            if (i == 2) {
                row.set(1, reportBo.getProjectName());
            }
            if (i == 3) {
                row.set(1, reportBo.getCenterDepart());
            }
            if (i == 4) {
                row.set(1, reportBo.getPartDepart());
                row.set(4, reportBo.getActDepart());
            }
            if (i == 6) {
                row.set(2, planTotal);
                row.set(3, outTotal);
                if (outTotal != null){
                    BigDecimal bigDecimal = outTotal.multiply(BigDecimal.valueOf(100)).divide(planTotal, 2, BigDecimal.ROUND_DOWN);
                    row.set(4, bigDecimal.toString() + "%");
                }
            }
            if (i >= 7) {
                int index = i - 7;
                if (index < res.size()) {
                    Map map = res.get(index);
                    String sourceValue = map.get("source").toString();
                    DictDataDO dictDataDO = dictDataService.getDictData("money_source", sourceValue);
                    if (dictDataDO != null){
                        row.set(1, dictDataDO.getLabel());
                    }
                    row.set(2, map.get("budget"));
                    row.set(3, map.get("out"));
                    row.set(4, map.get("percent"));
                }
            }
        }

        InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream("static/report_temp.xls");
        if (inputStream2 == null) {
            return R.fail("找不到指定资源");
        }

        // 将 InputStream 转换为临时文件
        File fileT = File.createTempFile("temp", ".xls");
        Files.copy(inputStream2, fileT.toPath(), StandardCopyOption.REPLACE_EXISTING);

//        File fileT = new ClassPathResource("static/report_temp.xls").getFile();

        ExcelWriter writer = ExcelUtil.getWriter(fileT, "附件2");
        writer.write(read);
        writer.close();

        FileInputStream fileInputStream = new FileInputStream(fileT);
        InputStream fis = new BufferedInputStream(fileInputStream);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();


        String filePath = "/report/资金统计报表-" + getRandomStr() + ".xls";
        File file1 = FileUtil.writeBytes(buffer, path + filePath);
        if (file1.length() < 0) {
            return R.fail("导出报表异常");
        }

        // 从数据字典中 获取<文件上传路径>前缀第一个正常的字典数据 赋值给url_prefix 若无则使用配置文件中的默认值
        List<DictDataDO> dictDataList = dictDataService.getDictDataList("file_upload_url_prefix");
        for (DictDataDO dictDataDO : dictDataList) {
            if (dictDataDO.getStatus().equals(CommonStatusEnum.ENABLE.getStatus())){
                urlPrefix = dictDataDO.getValue();
            }
            break;
        }
        return R.ok("导出报表成功", urlPrefix + filePath);
    }

    private String getRandomStr(){
        // 获取当前的年月日时分秒
        LocalDateTime now = LocalDateTime.now();
        // 将LocalDateTime转换为Date
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        // 格式化日期时间
        String formattedDateTime = DateUtil.format(date, "yyyyMMddHHmmss");
        // 生成四位随机数
        int randomNumber = RandomUtil.randomInt(1000, 9999);

        // 将日期时间和随机数拼接成一个字符串
        return formattedDateTime + randomNumber;
    }

}
