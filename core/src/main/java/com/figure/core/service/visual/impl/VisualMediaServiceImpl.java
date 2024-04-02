package com.figure.core.service.visual.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.visual.VisualMedia;
import com.figure.core.repository.visual.VisualMediaMapper;
import com.figure.core.service.visual.IVisualMediaService;
import com.figure.core.util.SvgUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-06-22
 */
@Service
public class VisualMediaServiceImpl extends ServiceImpl<VisualMediaMapper, VisualMedia> implements IVisualMediaService {

    @Override
    public void save(String fullPath, String filename,String originalName) {
        String fullFile = fullPath+filename;
        VisualMedia visualMedia = new VisualMedia();
        visualMedia.setName(originalName);
        visualMedia.setUrl(filename);
        Path filePath = Paths.get(fullFile);
        try {
            visualMedia.setSize((double) Files.size(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String capitalsStr = filename.toLowerCase();
        if(capitalsStr.endsWith("jpg") || capitalsStr.endsWith("png")|| capitalsStr.endsWith("gif")) {
            visualMedia.setType("图片");
        }
        if(capitalsStr.endsWith("mp4")) {
            visualMedia.setType("视频");
        }
        if(capitalsStr.endsWith("mp3")) {
            visualMedia.setType("音频");
        }
        if(capitalsStr.endsWith("svg")) {
            visualMedia.setType("矢量图");
        }
        if("矢量图".equals(visualMedia.getType())) {
            int svgHeight = 0;
            int svgWidth = 0;
            try {
                svgHeight = SvgUtil.getSvgHeight(fullFile);
                svgWidth = SvgUtil.getSvgWidth(fullFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            visualMedia.setResolution(svgWidth+"X"+svgHeight);
        }
        if("图片".equals(visualMedia.getType())) {
            File file = new File(fullFile);
            BufferedImage bi = null;
            try {
                bi = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int width = bi.getWidth();
            int height = bi.getHeight();
            visualMedia.setResolution(width+"X"+height);
        }
        save(visualMedia);
    }
}
