package com.figure.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.model.sys.SysPara;
import com.figure.core.service.others.IStorageService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.service.visual.IVisualMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

@Controller
public class FileUploadController {

    @Autowired
    private IStorageService storageService;

    @Autowired
    ISysParaService sysParaService;

    @Resource
    private IVisualMediaService visualMediaService;

    @PostMapping("/uploadFormFile")
    @ResponseBody
    public Map<String,Object> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        QueryWrapper<SysPara> queryWrapper = new QueryWrapper<SysPara>();
        queryWrapper.eq("paraName","uploadPath");
        String uploadPath = sysParaService.list(queryWrapper).get(0).getParaValue();
        Map<String,Object> result = storageService.store(file,uploadPath);
        String type = request.getParameter("type");
        if("media".equals(type)){
            String fullPath = result.get("fullPath").toString();
            String filename = result.get("filename").toString();
            String originalName = result.get("originalName").toString();
            visualMediaService.save(fullPath,filename,originalName);
        }
        return result;
    }

    @PostMapping( "/uploadBinaryFile")
    @ResponseBody
    public Map<String,Object> acceptData(InputStream dataStream,String fileName) throws Exception {
        QueryWrapper<SysPara> queryWrapper = new QueryWrapper<SysPara>();
        queryWrapper.eq("paraName","uploadPath");
        String uploadPath = sysParaService.list(queryWrapper).get(0).getParaValue();
        Map<String,Object> result = storageService.store(dataStream,uploadPath,fileName);
        return result;
    }
}
