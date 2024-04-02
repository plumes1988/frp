package com.figure.op.system.controller;

import cn.hutool.core.io.FileUtil;
import com.figure.op.common.domain.R;
import com.figure.op.common.enums.CommonStatusEnum;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.service.DictDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/sysInfo")
public class SysInfoController {

    private static final Logger log = LoggerFactory.getLogger(SysInfoController.class);

    private static final int MAX_POST_SIZE = 5 * 1024 * 1024;

    @Value("${file.url_prefix}")
    private String url_prefix;

    @Value("${file.path}")
    private String path;

    @Resource
    private DictDataService dictDataService;

    @PostMapping("/upload")
    public R uploadFile(MultipartFile multipartFile) throws Exception
    {
        try {
            // 参数检验
            if (multipartFile == null) {
                return R.fail("文件不能为空");
            }
            // 文件限制10M
            long size = multipartFile.getSize();
            if (size > MAX_POST_SIZE) {
                return R.fail("文件大小不能超过5M");
            }
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")) + multipartFile.getOriginalFilename();

            String folder = path + File.separator + time + File.separator;
            if (!FileUtil.exist(folder)) {
                FileUtil.mkdir(folder);
            }
            String path = folder + fileName;
            File file = new File(path);
            if (FileUtil.exist(file)) {
                throw new Exception("文件已存在");
            }
            File file1 = FileUtil.writeBytes(multipartFile.getBytes(), path);
            if (file1.length() < 0) {
                throw new Exception("文件上传失败");
            }
            // 从数据字典中 获取<文件上传路径>前缀第一个正常的字典数据 赋值给url_prefix 若无则使用配置文件中的默认值
            List<DictDataDO> dictDataList = dictDataService.getDictDataList("file_upload_url_prefix");
            for (DictDataDO dictDataDO : dictDataList) {
                if (dictDataDO.getStatus().equals(CommonStatusEnum.ENABLE.getStatus())){
                    url_prefix = dictDataDO.getValue();
                }
                break;
            }
            // 上传并返回新文件名称
            String url = url_prefix + "/"+ time + "/" + fileName;
            HashMap<String, String> res = new HashMap<>();
            res.put("fileName", fileName);
            res.put("url", url);
            return R.ok(res);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
