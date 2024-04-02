package com.figure.core.service.op;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.op.OpScheduleHandover;

import java.util.Map;

/**
 * <p>
 * 交接班 服务类
 * </p>
 *
 * @author jobob
 * @since 2023-06-02
 */
public interface IOpScheduleHandoverService extends IService<OpScheduleHandover> {

    IPage<Map<String,Object>> selectPage(Page<Map<String,Object>> page, Map<String, String> map);
}
