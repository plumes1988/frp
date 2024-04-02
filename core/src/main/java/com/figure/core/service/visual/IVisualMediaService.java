package com.figure.core.service.visual;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.visual.VisualMedia;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-06-22
 */
public interface IVisualMediaService extends IService<VisualMedia> {

    void save(String fullPath, String filename,String originalName);
}
