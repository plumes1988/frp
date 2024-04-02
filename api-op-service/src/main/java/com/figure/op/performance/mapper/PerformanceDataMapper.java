package com.figure.op.performance.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.performance.domain.PerformanceDataDo;
import com.figure.op.performance.domain.PerformanceManageDO;
import com.figure.op.performance.domain.PerformanceTemplateInfoDo;
import com.figure.op.performance.domain.vo.manage.PerformanceManagePageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 绩效评分数据 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PerformanceDataMapper extends BaseMapper<PerformanceDataDo> {


    /**
     * 查询绩效模板明细根据关联模板id
     *
     * @param id 绩效模板主键
     * @return 绩效模板明细
     */
    default List<PerformanceDataDo> selectInfoByManageId(Long id){
        LambdaQueryWrapper<PerformanceDataDo> queryWrapper = new LambdaQueryWrapper<>();
        return this.selectList(queryWrapper.eq(PerformanceDataDo::getManageId, id));
    }

}
