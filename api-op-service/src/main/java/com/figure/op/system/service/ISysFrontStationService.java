package com.figure.op.system.service;

import com.figure.op.system.domain.SysFrontStation;
import com.figure.op.system.domain.bo.SysFrontStationBo;
import com.figure.op.system.domain.vo.SysFrontStationListVo;
import com.figure.op.system.domain.vo.SysFrontStationVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface ISysFrontStationService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<SysFrontStationListVo> queryList(SysFrontStation SysFrontStation);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    SysFrontStationVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(SysFrontStationBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(SysFrontStationBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

}
