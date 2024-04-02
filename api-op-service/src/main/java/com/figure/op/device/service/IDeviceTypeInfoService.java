package com.figure.op.device.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.device.domain.DeviceTypeInfo;
import com.figure.op.device.domain.bo.DeviceTypeInfoBo;
import com.figure.op.device.domain.bo.DeviceTypeInfoQueryBo;
import com.figure.op.device.domain.vo.DeviceTypeInfoListVo;
import com.figure.op.device.domain.vo.DeviceTypeInfoPageVo;
import com.figure.op.device.domain.vo.DeviceTypeInfoVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IDeviceTypeInfoService {

    /**
     * 分页列表1
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<DeviceTypeInfoPageVo> page(DeviceTypeInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 分页列表2（多表）
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<DeviceTypeInfoPageVo> queryPageList2(DeviceTypeInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 全部列表
     * @return 全部列表
     */
    List<DeviceTypeInfoListVo> queryList(DeviceTypeInfo deviceTypeInfo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    DeviceTypeInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(DeviceTypeInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(DeviceTypeInfoBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

}
