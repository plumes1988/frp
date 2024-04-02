package com.figure.core.service.others.impl;

import com.figure.core.helper.CommonUtil;
import com.figure.core.service.others.IStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class StorageServiceImpl implements IStorageService {
    @Override
    public Map<String, Object> store(MultipartFile mfile, String uploadPath) {
        Map<String, Object> model = new HashMap<>();
        String originalName = mfile.getOriginalFilename();
        String extTypeName = originalName.substring(
                originalName.lastIndexOf(".")).toLowerCase();

        String imgName = CommonUtil.formatByDate(new Date(), "YYYYMMddHHmmss"); // request.getParameter("ImgName");
        String newImgName = imgName + extTypeName;
        String goalPath = uploadPath;
        File folder = new File(goalPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        try {
            saveFileFromInputStream(mfile.getInputStream(), goalPath + "/",
                    newImgName);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            model.put("status",0);
            model.put("msg", e.getMessage());
            return model;
        } catch (IOException e) {
            e.printStackTrace();
            model.put("status",0);
            model.put("msg", e.getMessage());
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            model.put("status",0);
            model.put("msg", e.getMessage());
            return model;
        }
        model.put("status",1);
        model.put("msg", "图片上传成功");
        model.put("fullPath", goalPath + "/");
        model.put("filename", newImgName);
        model.put("originalName", originalName);
        return model;
    }

    @Override
    public Map<String, Object> store(InputStream dataStream, String uploadPath, String fileName) {
        Map<String, Object> model = new HashMap<>();
        try {
            saveFileFromInputStream(dataStream, uploadPath + "/",
                    fileName);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            model.put("status",0);
            model.put("msg", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            model.put("status",0);
            model.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            model.put("status",0);
            model.put("msg", e.getMessage());
        }
        model.put("status",1);
        model.put("msg", "文件上传成功");
        model.put("fullPath", uploadPath + "/");
        model.put("filename", fileName);
        return model;
    }

    private void saveFileFromInputStream(InputStream inputStream, String path,
                                         String fileName) throws IOException {
        FileOutputStream fs = new FileOutputStream(path + "/" + fileName);
        byte[] buffer = new byte[1024 * 1024];
        int byteread = 0;
        while ((byteread = inputStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
            fs.flush();
        }
        fs.close();
        inputStream.close();
    }
}
