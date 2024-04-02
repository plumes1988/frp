package com.figure.op.performance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.enums.PerformanceTemplateLevelEnum;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.performance.domain.PerformanceDataDo;
import com.figure.op.performance.domain.PerformanceManageDO;
import com.figure.op.performance.domain.PerformanceTemplateDo;
import com.figure.op.performance.domain.PerformanceTemplateInfoDo;
import com.figure.op.performance.domain.bo.PerformanceManageScoreFirstBo;
import com.figure.op.performance.domain.bo.PerformanceManageScoreReqBo;
import com.figure.op.performance.domain.bo.PerformanceManageScoreSecondBo;
import com.figure.op.performance.domain.vo.date.PerformanceDataExcelVo;
import com.figure.op.performance.domain.vo.date.PerformanceDataTreeVo;
import com.figure.op.performance.domain.vo.manage.*;
import com.figure.op.performance.mapper.PerformanceDataMapper;
import com.figure.op.performance.mapper.PerformanceManageMapper;
import com.figure.op.performance.service.PerformanceDataService;
import com.figure.op.performance.service.PerformanceManageService;
import com.figure.op.performance.service.PerformanceTemplateInfoService;
import com.figure.op.performance.service.PerformanceTemplateService;
import com.figure.op.system.domain.SysAgency;
import com.figure.op.system.service.ISysAgencyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 绩效评分管理 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class PerformanceManageServiceImpl implements PerformanceManageService {

    @Resource
    private PerformanceManageMapper manageMapper;

    @Resource
    private PerformanceDataMapper performanceDataMapper;

    @Resource
    private PerformanceTemplateService templateService;

    @Resource
    private ISysAgencyService sysAgencyService;

    @Resource
    private PerformanceTemplateInfoService templateInfoService;

    @Resource
    private PerformanceDataService performanceDataService;

    private final int cols = 10;//excel里表格列


    @Override
    public void export(HttpServletResponse response, Long id) throws IOException {

        PerformanceManageDO manageDO = manageMapper.selectById(id);
        if (manageDO == null) {
            throw new ServerException("绩效评分不存在");
        }

        // 获取数据
        List<PerformanceDataExcelVo> dataList = getDateList(id);

        String headTitle = "标题";//标题;
        // 创建excel
        Workbook book = null;
        try {
            book = new XSSFWorkbook();//excell2007
        } catch (Exception ex) {
            book = new HSSFWorkbook();//excell2003
        }
        // 在Excel工作薄中建一张工作表;
        Sheet sheet = book.createSheet(headTitle);
        //设置标题和表头;
//        createTitle(book, sheet, headTitle);
//        createHead(book, sheet);


        for (int i = 0; i < dataList.size(); i++) {
            PerformanceDataExcelVo item = dataList.get(i);
            // 每行
            Row row0 = sheet.createRow(i);

            // 第一单元格数据永远是父级
            Cell cell0 = row0.createCell(0);
            cell0.setCellValue(item.getParentTitle());
            cell0.setCellStyle(createTitleStyle(book));

            // 第二单元格数据永远是父级
            Cell cell1 = row0.createCell(1);
            cell1.setCellValue(item.getScoreRate());
            cell1.setCellStyle(createTitleStyle(book));

            // 第三单元格数据永远是父级
            Cell cell2 = row0.createCell(2);
            cell2.setCellValue(item.getTitle());
            cell2.setCellStyle(createTitleStyle(book));

            // 第四单元格数据永远是父级
            Cell cell3 = row0.createCell(3);
            cell3.setCellValue(item.getScore().toString());
            cell3.setCellStyle(createTitleStyle(book));

        }
        ServletOutputStream outputStream = response.getOutputStream();
        book.write(outputStream);
        book.close();
        outputStream.close();
    }

    @Override
    public void exportV2(HttpServletResponse response, Long id) throws IOException {
        log.info("导出评分");
        PerformanceManageDO manageDO = manageMapper.selectById(id);
        if (manageDO == null) {
            throw new ServerException("绩效评分不存在");
        }
        Long templateId = manageDO.getTemplateId();
        PerformanceTemplateDo performanceTemplateDo = templateService.selectById(templateId);
        if (performanceTemplateDo == null) {
            throw new ServerException("绩效模板不存在");
        }
        Integer agencyId = manageDO.getAgencyId();
        SysAgency sysAgency = sysAgencyService.selectById(agencyId);
        if (sysAgency == null) {
            throw new ServerException("机构不存在");
        }
        // 获取数据
        List<PerformanceDataExcelVo> dataList = getDateList(id);


        String headTitle = "标题";//标题;
        // 创建excel
        Workbook book = null;
        try {
            book = new XSSFWorkbook();//excell2007
        } catch (Exception ex) {
            book = new HSSFWorkbook();//excell2003
        }
        // 在Excel工作薄中建一张工作表;
        Sheet sheet = book.createSheet(headTitle);


        // 模板名称;
        createTitle(book, sheet, performanceTemplateDo.getName());
        // 机构名称
        createAgency(book, sheet, sysAgency.getAgencyName());
        // 表头
        createHead(book, sheet);

        String prevValue = null;
        int mergeStartRow = 3; // 从第三行开始插入数据

        for (int i = 0; i < dataList.size(); i++) {
            PerformanceDataExcelVo item = dataList.get(i);
            // 每行
            Row row0 = sheet.createRow(i + 3); // 从第三行开始插入数据

            // （父级）考核类别
            Cell cell0 = row0.createCell(0);
            String currValue = item.getParentTitle();
            cell0.setCellValue(currValue);
            cell0.setCellStyle(createTitleStyle(book));

            // （父级）占比
            Cell cell1 = row0.createCell(1);
            cell1.setCellValue(item.getParentScoreRate() + "%");
            cell1.setCellStyle(createTitleStyle(book));

            // （父级）满分
            Cell cell2 = row0.createCell(2);
            cell2.setCellValue(item.getParentFullScore().stripTrailingZeros().toPlainString());
            cell2.setCellStyle(createTitleStyle(book));

            // （父级）实际分值
            Cell cell3 = row0.createCell(3);
            cell3.setCellValue(item.getParentScore().stripTrailingZeros().toPlainString());
            cell3.setCellStyle(createTitleStyle(book));

            // （子级）考核项目
            Cell cell4 = row0.createCell(4);
            cell4.setCellValue(item.getTitle());
            cell4.setCellStyle(createTitleStyle(book));

            // （子级）说明
            Cell cell5 = row0.createCell(5);
            cell5.setCellValue(item.getInfo());
            cell5.setCellStyle(createTitleStyle(book));

            // （子级）占比
            Cell cell6 = row0.createCell(6);
            cell6.setCellValue(item.getScoreRate() + "%");
            cell6.setCellStyle(createTitleStyle(book));

            // （子级）满分
            Cell cell7 = row0.createCell(7);
            cell7.setCellValue(item.getFullScore().stripTrailingZeros().toPlainString());
            cell7.setCellStyle(createTitleStyle(book));

            // （子级）实际分值
            Cell cell8 = row0.createCell(8);
            cell8.setCellValue(item.getScore().stripTrailingZeros().toPlainString());
            cell8.setCellStyle(createTitleStyle(book));

            // （子级）打分
            Cell cell9 = row0.createCell(9);
            cell9.setCellValue(item.getMark());
            cell9.setCellStyle(createTitleStyle(book));

            // 如果当前值与前一个值不同，合并单元格
            if (prevValue != null && !prevValue.equals(currValue)) {
                sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, i + 2, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, i + 2, 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, i + 2, 2, 2));
                sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, i + 2, 3, 3));
                mergeStartRow = i + 3; // 下一组合并从下一行开始
            }

            prevValue = currValue;
        }

        // 处理最后一组相同值的合并
        if (prevValue != null) {
            sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, dataList.size() + 2, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, dataList.size() + 2, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, dataList.size() + 2, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, dataList.size() + 2, 3, 3));
        }

        // 得分
        Row totalRow = sheet.createRow(dataList.size() + 3);
        Cell totalLabelCell = totalRow.createCell(0);
        totalLabelCell.setCellValue("评分：" + manageDO.getScore().stripTrailingZeros().toPlainString());
        totalLabelCell.setCellStyle(createTitleStyle(book));


        ServletOutputStream outputStream = response.getOutputStream();
        book.write(outputStream);
        book.close();
        outputStream.close();
    }

    /**
     * 创建标题样式
     *
     * @param book
     * @return
     */
    public CellStyle createAgencyStyle(Workbook book) {
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderBottom(BorderStyle.THIN);//下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//水平居左
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        Font font = book.createFont();
        font.setFontHeightInPoints((short) 16); // 字体大小
        font.setFontName("宋体");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 创建标题样式
     *
     * @param book
     * @return
     */
    public CellStyle createTitleStyle(Workbook book) {
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderBottom(BorderStyle.THIN);//下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 20); // 字体大小
        font.setFontName("宋体");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 给excel设置标题
     *
     * @param sheet
     */
    public void createTitle(Workbook book, Sheet sheet, String headTitle) {
        CellStyle style = createTitleStyle(book);
        Row row = sheet.createRow(0);// 创建第一行,设置表的标题;
        row.setHeightInPoints(36);//设置行的高度是34个点
        Cell cell = row.createCell(0);
        cell.setCellValue(headTitle);
        cell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cols - 1));//第一行跨表所有列;
    }

    /**
     * 给excel设置机构标题
     *
     * @param sheet
     */
    public void createAgency(Workbook book, Sheet sheet, String headTitle) {
        CellStyle style = createAgencyStyle(book);
        Row row = sheet.createRow(1);// 创建第一行,设置表的标题;
        row.setHeightInPoints(36);//设置行的高度是34个点
        Cell cell = row.createCell(0);
        cell.setCellValue("机构：" + headTitle);
        cell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, cols - 1));//第一行跨表所有列;
    }

    /**
     * 给excel设置最后一行得分
     *
     * @param sheet
     */
    public void createTotalScore(Workbook book, Sheet sheet, Long score, Integer rowNum) {
        CellStyle style = createAgencyStyle(book);
        Row row = sheet.createRow(rowNum);// 创建第一行,设置表的标题;
        row.setHeightInPoints(36);//设置行的高度是34个点
        Cell cell = row.createCell(0);
        cell.setCellValue("打分：" + score);
        cell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, cols - 1));//第一行跨表所有列;
    }

    /**
     * 创建表头样式
     *
     * @param book
     * @return
     */
    public CellStyle createHeadStyle(Workbook book) {
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderBottom(BorderStyle.THIN);//下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 填充单元格
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex()); // 填浅蓝色
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 11); // 字体大小
        font.setFontName("黑体");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置导出表的表头
     *
     * @param book
     * @param sheet
     */
    private void createHead(Workbook book, Sheet sheet) {
        // 设置单元格格式(文本)
        // 第二行为表头行
        String title = "";
        CellStyle style = createHeadStyle(book);
        Row row = sheet.createRow(2);// 创建第一行
        row.setHeightInPoints(22);//设置行的高度是20个点
        for (int j = 0; j < cols; j++) {
            Cell cell = row.createCell(j);
            cell.setCellType(CellType.STRING);
            if (j == 0) {
                title = "考核类别";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }
            if (j == 1) {
                title = "占比";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }
            if (j == 2) {
                title = "满分";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }
            if (j == 3) {
                title = "实际分值";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }
            if (j == 4) {
                title = "考核项目";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }
            if (j == 5) {
                title = "说明";
                sheet.setColumnWidth(j, title.getBytes().length * 4 * 256);
            }
            if (j == 6) {
                title = "占比";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }
            if (j == 7) {
                title = "满分";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }
            if (j == 8) {
                title = "实际分值";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }
            if (j == 9) {
                title = "打分";
                sheet.setColumnWidth(j, title.getBytes().length * 2 * 256);
            }

            cell.setCellValue(title);
            cell.setCellStyle(style);
        }
    }


    /**
     * 打分
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean score(PerformanceManageScoreReqBo scoreReqBo) {

        // 查询绩效评价记录
        PerformanceManageDO performanceManageDO = manageMapper.selectById(scoreReqBo.getManageId());
        if (performanceManageDO == null) {
            throw new ServiceException("绩效评价记录不存在");
        }
        // 查询模板数据
        PerformanceTemplateDo performanceTemplateDo = templateService.selectById(performanceManageDO.getTemplateId());
        if (performanceTemplateDo == null) {
            throw new ServiceException("绩效模板不存在");
        }
        // 打分前 判定模板是否完整
        boolean completeRes = templateInfoService.validateTemplateInfoComplete(performanceTemplateDo.getId());
        if (!completeRes){
            throw new ServiceException("模板配置未完成，无法打分！");
        }

        // 绩效评价记录是否已打分 若重新打分则删除旧打分记录
        LambdaQueryWrapper<PerformanceDataDo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PerformanceDataDo::getManageId, scoreReqBo.getManageId());
        performanceDataMapper.delete(lqw);

        // 更新绩效评价
        PerformanceManageDO update = new PerformanceManageDO();
        update.setId(scoreReqBo.getManageId());
        update.setScore(scoreReqBo.getTotalScore());
        manageMapper.updateById(update);

        for (PerformanceManageScoreFirstBo performanceManageScoreFirstBo : scoreReqBo.getScoreList()) {
            // 先插入父级
            PerformanceDataDo performanceDataDo1 = new PerformanceDataDo();
            performanceDataDo1.setManageId(scoreReqBo.getManageId());
            performanceDataDo1.setTemplateId(performanceManageDO.getTemplateId());
            performanceDataDo1.setTemplateInfoId(performanceManageScoreFirstBo.getId());
            performanceDataDo1.setTitle(performanceManageScoreFirstBo.getTitle());
            performanceDataDo1.setLevel("first");
            performanceDataDo1.setParentId(null);
            performanceDataDo1.setScoreRate(performanceManageScoreFirstBo.getScoreRate());
            performanceDataDo1.setFullScore(performanceManageScoreFirstBo.getFullScore());
            performanceDataDo1.setScore(performanceManageScoreFirstBo.getScore());
            performanceDataMapper.insert(performanceDataDo1);
            // 遍历插入子级
            for (PerformanceManageScoreSecondBo performanceManageScoreSecondBo : performanceManageScoreFirstBo.getSecondList()) {
                PerformanceDataDo performanceDataDo2 = new PerformanceDataDo();
                performanceDataDo2.setManageId(scoreReqBo.getManageId());
                performanceDataDo2.setTemplateId(performanceManageDO.getTemplateId());
                performanceDataDo2.setTemplateInfoId(performanceManageScoreSecondBo.getId());
                performanceDataDo2.setTitle(performanceManageScoreSecondBo.getTitle());
                performanceDataDo2.setLevel("second");
                performanceDataDo2.setParentId(performanceDataDo1.getId());
                performanceDataDo2.setScoreRate(performanceManageScoreSecondBo.getScoreRate());
                performanceDataDo2.setFullScore(performanceManageScoreSecondBo.getFullScore());
                performanceDataDo2.setScore(performanceManageScoreSecondBo.getScore());
                performanceDataDo2.setMark(performanceManageScoreSecondBo.getMark());
                performanceDataDo2.setInfo(performanceManageScoreSecondBo.getInfo());
                performanceDataMapper.insert(performanceDataDo2);
            }
        }

        return true;
    }


    /**
     * 获取评价打分结果
     *
     * @param id
     * @return
     */
    @Override
    public PerformanceManageScoreRespVO scoreRes(Long id) {
        PerformanceManageScoreRespVO res = new PerformanceManageScoreRespVO();
        // 找到绩效评价数据
        PerformanceManageDO performanceManageDO = manageMapper.selectById(id);
        if (performanceManageDO == null) {
            throw new ServiceException("绩效评分不存在");
        }
        if (performanceManageDO.getScore() == null){
            return res;
        }

        res.setManageId(id);
        BigDecimal totalScore = performanceManageDO.getScore().stripTrailingZeros();
        String totalScoreString = totalScore.toPlainString();
        res.setTotalScore(new BigDecimal(totalScoreString));
        res.setTemplateId(performanceManageDO.getTemplateId());
        // 关联机构名称
        SysAgency sysAgency = sysAgencyService.selectById(performanceManageDO.getAgencyId());
        res.setAgencyId(performanceManageDO.getAgencyId());
        if (sysAgency != null) {
            res.setAgencyName(sysAgency.getAgencyName());
        }

        // 模板打分数据明细-父级列表
        LambdaQueryWrapper<PerformanceDataDo> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(PerformanceDataDo::getManageId, id);
        lqw1.isNull(PerformanceDataDo::getParentId);
        List<PerformanceDataDo> performanceDataFirstList = performanceDataMapper.selectList(lqw1);
        List<PerformanceManageScoreFirstVo> firstVoList = BeanCopyUtils.copyList(performanceDataFirstList, PerformanceManageScoreFirstVo.class);
        res.setScoreList(firstVoList);
        if (firstVoList != null){
            for (PerformanceManageScoreFirstVo scoreFirstVo : firstVoList) {
                // 处理父级-满分、实际分值
                BigDecimal firstFullScore = scoreFirstVo.getFullScore().stripTrailingZeros();
                String firstFullScoreString = firstFullScore.toPlainString();
                scoreFirstVo.setFullScore(new BigDecimal(firstFullScoreString));

                BigDecimal firstScore = scoreFirstVo.getScore().stripTrailingZeros();
                String firstScoreString = firstScore.toPlainString();
                scoreFirstVo.setScore(new BigDecimal(firstScoreString));

                // 查询子级
                LambdaQueryWrapper<PerformanceDataDo> lqw2 = new LambdaQueryWrapper<>();
                lqw2.eq(PerformanceDataDo::getManageId, id);
                lqw2.eq(PerformanceDataDo::getParentId, scoreFirstVo.getId());
                List<PerformanceDataDo> performanceDataSecondList = performanceDataMapper.selectList(lqw2);
                List<PerformanceManageScoreSecondVo> secondVoList = BeanCopyUtils.copyList(performanceDataSecondList, PerformanceManageScoreSecondVo.class);
                if (secondVoList != null){
                    for (PerformanceManageScoreSecondVo scoreSecondVo : secondVoList) {
                        // 处理子级-满分、实际分值
                        BigDecimal secondFullScore = scoreSecondVo.getFullScore().stripTrailingZeros();
                        String secondFullScoreString = secondFullScore.toPlainString();
                        scoreSecondVo.setFullScore(new BigDecimal(secondFullScoreString));

                        BigDecimal secondScore = scoreSecondVo.getScore().stripTrailingZeros();
                        String secondScoreString = secondScore.toPlainString();
                        scoreSecondVo.setScore(new BigDecimal(secondScoreString));
                    }
                }
                scoreFirstVo.setSecondList(secondVoList);
            }
        }
        return res;
    }

    @Override
    public Long createManage(PerformanceManageCreateReqVO createReqVO) {
        // 插入
        PerformanceManageDO manage = new PerformanceManageDO();
        BeanCopyUtils.copy(createReqVO, manage);
        manageMapper.insert(manage);

        // 返回
        return manage.getId();
    }

    /**
     * 根据模板id生成对应数据
     *
     * @param manage
     */
    private void copyByTemplateId(PerformanceManageDO manage) {
        // 根据所选模板生成数据
        // 找到模板明细列表
        List<PerformanceTemplateInfoDo> allTemplateInfo = templateInfoService.selectInfoByTemplateId(manage.getTemplateId());

        if (CollUtil.isEmpty(allTemplateInfo)) {
            throw new ServiceException("当前所选模板下暂无明细");
        }

        /**
         * 模板数据是层级的
         * 怎么根据这个层级关系插入数据呢？
         * 双重循环，根据模板插入数据
         */
        // 找到一层
        List<PerformanceTemplateInfoDo> firstList = allTemplateInfo.stream()
                .filter(item -> item.getLevel().equals(PerformanceTemplateLevelEnum.FIRST.getLevel()))
                .collect(Collectors.toList());

        // 循环第一层
        for (PerformanceTemplateInfoDo firstItem : firstList) {
            // 插入数据，并循环，记录id,作为下层的parentId
            Long firstItemId = copyTemplateInfoDate(manage.getId(), null, firstItem);
            // 循环第二层
            for (PerformanceTemplateInfoDo secondItem : allTemplateInfo) {
                if (secondItem.getParentId().equals(firstItem.getId())) {
                    // 插入数据，并且上级id设置为
                    copyTemplateInfoDate(manage.getId(), firstItemId, secondItem);
                }
            }
        }
    }

    /**
     * 基于模板复制数据
     *
     * @param manageId 关联绩效评分记录id
     * @param parentId 上级id
     * @param source   绩效模板明细模板数据
     */
    private Long copyTemplateInfoDate(Long manageId, Long parentId, PerformanceTemplateInfoDo source) {
        PerformanceDataDo createDo = new PerformanceDataDo();
        BeanCopyUtils.copy(source, createDo);
        createDo.setParentId(parentId);
        createDo.setTemplateId(source.getTemplateId());
        createDo.setTemplateInfoId(source.getId());
        createDo.setManageId(manageId);
        performanceDataService.create(createDo);
        return createDo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateManage(PerformanceManageUpdateReqVO updateReqVO) {
        // 校验存在
        PerformanceManageDO manageDO = manageMapper.selectById(updateReqVO.getId());
        if (manageDO == null) {
            throw new ServiceException("数据不存在");
        }
        // 判断是否已进行打分
        LambdaQueryWrapper<PerformanceDataDo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PerformanceDataDo::getManageId, updateReqVO.getId());
        lqw.eq(PerformanceDataDo::getTemplateId, manageDO.getTemplateId());
        List<PerformanceDataDo> dataList = performanceDataMapper.selectList(lqw);
        if (dataList != null && dataList.size() != 0){
            throw new ServiceException("该记录已经进行打分，无法进行编辑");
        }
        // 更新
        PerformanceManageDO updateObj = new PerformanceManageDO();
        BeanCopyUtils.copy(updateReqVO, updateObj);
        manageMapper.updateById(updateObj);
    }

    @Override
    public void deleteManage(Long id) {
        // 校验存在
        validateManageExists(id);
        // 删除
        manageMapper.deleteById(id);
    }

    private void validateManageExists(Long id) {
        if (manageMapper.selectById(id) == null) {
            throw new ServiceException("数据不存在");
        }
    }

    @Override
    public PerformanceManageDO getManage(Long id) {
        return manageMapper.selectById(id);
    }

    @Override
    public List<PerformanceDataExcelVo> getDateList(Long id) {

        // 模板明细列表
        PerformanceManageDO performanceManageDO = manageMapper.selectById(id);

        // 找到绩效评分数据
        List<PerformanceDataDo> performanceDataDos = performanceDataService.selectInfoByManageId(performanceManageDO.getId());


        if (CollUtil.isEmpty(performanceDataDos)) {
            return Collections.emptyList();
        }

        List<PerformanceDataExcelVo> voList = BeanCopyUtils.copyList(performanceDataDos, PerformanceDataExcelVo.class);

        List<PerformanceDataExcelVo> secondList = voList.stream()
                .filter(item -> item.getParentId() != null)
                .sorted(Comparator.comparingLong(PerformanceDataExcelVo::getParentId))
                .collect(Collectors.toList());

        // 循环第一层
        for (PerformanceDataExcelVo item : secondList) {
            // 插入数据，并循环，记录id,作为下层的parentId
            PerformanceDataDo parentDo = performanceDataDos.stream()
                    .filter(data -> data.getId().equals(item.getParentId()))
                    .findFirst() // 找到第一个满足条件的元素
                    .orElse(null);

            if (parentDo != null) {
                item.setParentTitle(parentDo.getTitle());
                item.setParentScoreRate(parentDo.getScoreRate());
                item.setParentFullScore(parentDo.getFullScore());
                item.setParentScore(parentDo.getScore());
            }

        }

        return secondList;
    }


    @Override
    public List<PerformanceDataTreeVo> getDateTreeById(Long id) {

        // 模板明细列表
        PerformanceManageDO performanceManageDO = manageMapper.selectById(id);
        // 找到绩效评分数据
        List<PerformanceDataDo> performanceDataDos = performanceDataService.selectInfoByManageId(performanceManageDO.getId());


        if (CollUtil.isEmpty(performanceDataDos)) {
            return Collections.emptyList();
        }

        List<PerformanceDataTreeVo> allTreeVos = BeanCopyUtils.copyList(performanceDataDos, PerformanceDataTreeVo.class);

        // 构建树结构，因为只会有两层没必要递归了

        // 找到一层
        List<PerformanceDataTreeVo> firstList = allTreeVos.stream()
                .filter(item -> item.getLevel().equals(PerformanceTemplateLevelEnum.FIRST.getLevel()))
                .collect(Collectors.toList());

        // 循环第一层
        for (PerformanceDataTreeVo firstItem : firstList) {
            // 插入数据，并循环，记录id,作为下层的parentId
            List<PerformanceDataTreeVo> secondList = allTreeVos.stream()
                    .filter(item -> item.getParentId() == firstItem.getId())
                    .collect(Collectors.toList());

            // 设置子集
            firstItem.setChildren(secondList);
        }

        return firstList;
    }

    @Override
    public List<PerformanceDataTreeVo> getDateListById(Long id) {
        // 模板明细列表
        PerformanceManageDO performanceManageDO = manageMapper.selectById(id);
        // 找到绩效评分数据
        List<PerformanceDataDo> performanceDataDos = performanceDataService.selectInfoByManageId(performanceManageDO.getId());
        List<PerformanceDataTreeVo> excelVos = BeanCopyUtils.copyList(performanceDataDos, PerformanceDataTreeVo.class);
        return excelVos;
    }

    @Override
    public List<PerformanceManageDO> getManageList(Collection<Long> ids) {
        return manageMapper.selectBatchIds(ids);
    }

    @Override
    public TableDataInfo<PerformanceManagePageReqVO> getManagePage(PerformanceManagePageReqVO pageReqVO, PageQuery pageQuery) {

        TableDataInfo<PerformanceManagePageReqVO> tableDataList = manageMapper.selectPage(pageReqVO, pageQuery);
        List<PerformanceManagePageReqVO> rows = tableDataList.getRows();
        if (CollUtil.isNotEmpty(rows)) {
            for (PerformanceManagePageReqVO row : rows) {
                // 关联模板名称
                PerformanceTemplateDo performanceTemplateDo = templateService.selectById(row.getTemplateId());
                if (performanceTemplateDo != null) {
                    row.setTemplateName(performanceTemplateDo.getName());
                }
                // 关联机构名称
                SysAgency sysAgency = sysAgencyService.selectById(row.getAgencyId());
                if (sysAgency != null) {
                    row.setAgencyName(sysAgency.getAgencyName());
                }
                // 处理评分
                BigDecimal score = row.getScore().stripTrailingZeros();
                String scoreString = score.toPlainString();
                row.setScore(new BigDecimal(scoreString));
            }
        }

        return tableDataList;
    }

    @Override
    public List<PerformanceManageDO> selectInfoByTemplateId(Long templateId) {
        LambdaQueryWrapper<PerformanceManageDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PerformanceManageDO::getTemplateId, templateId);
        return manageMapper.selectList(lambdaQueryWrapper);
    }
}
