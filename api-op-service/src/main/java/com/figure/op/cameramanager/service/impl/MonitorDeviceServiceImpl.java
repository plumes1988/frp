package com.figure.op.cameramanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.MonitorDeviceInfo;
import com.figure.op.cameramanager.domain.PartInfo;
import com.figure.op.cameramanager.domain.bo.MonitorDeviceInfoBo;
import com.figure.op.cameramanager.domain.vo.MonitorDeviceInfoVo;
import com.figure.op.cameramanager.mapper.MonitorDeviceInfoMapper;
import com.figure.op.cameramanager.mapper.PartInfoMapper;
import com.figure.op.cameramanager.service.MonitorDeviceInfoService;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.system.controller.SysInfoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * @Author liqiang
 * @Date 2023/9/8 16:54
 * @Version 1.5
 */
@Service
public class MonitorDeviceServiceImpl implements MonitorDeviceInfoService {
    private static final Logger log = LoggerFactory.getLogger(SysInfoController.class);

    private static final int MAX_POST_SIZE = 10 * 1024 * 1024;

    @Value("${file.url_prefix}")
    private String url_prefix;

    @Value("${file.path}")
    private String path;

    @Resource
    private PartInfoMapper partInfoMapper;


    @Resource
    private MonitorDeviceInfoMapper monitorDeviceMapper;

    @Override
    public R insertByBo(MonitorDeviceInfoBo bo, MultipartFile image) {
        //首先查询设备编号是否唯一
        QueryWrapper<MonitorDeviceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("deviceCode", bo.getDeviceCode());
        MonitorDeviceInfo monitorDeviceInfo = monitorDeviceMapper.selectOne(wrapper);
        if (monitorDeviceInfo != null) {
            return R.fail("设备编号不唯一");
        }

        //上传图像到图像服务器
        if (image == null) {
            return R.fail("文件不能为空");
        }

        // 文件限制10M
        long size = image.getSize();
        if (size > MAX_POST_SIZE) {
            return R.fail("文件不能超过100M");
        }
        String oriFileName = image.getOriginalFilename();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")) +
                UUID.randomUUID().toString().substring(0,8)+ oriFileName.substring(oriFileName.lastIndexOf("."));

        String folder = path + File.separator + "monitordevice" + File.separator + time + File.separator;
        if (!FileUtil.exist(folder)) {
            FileUtil.mkdir(folder);
        }
        String path = folder + fileName;
        File file = new File(path);
        if (FileUtil.exist(file)) {
            return R.fail("文件已存在");
        }
        File file1 = null;
        try {
            file1 = FileUtil.writeBytes(image.getBytes(), path);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail("文件写入错误");
        }
        if (file1.length() < 0) {
            return R.fail("文件上传失败");
        }

        // 上传并返回新文件名称
        String url = url_prefix + "/monitordevice/" + time + "/" + fileName;

        MonitorDeviceInfo monitorDevice = new MonitorDeviceInfo();
        monitorDevice = BeanUtil.toBean(bo, MonitorDeviceInfo.class);
        monitorDevice.setImageUrl(url);
        boolean flag = monitorDeviceMapper.insert(monitorDevice) > 0;
        if (flag) {
            return R.ok("添加成功");
        }
        return R.fail("添加失败，请重试");
    }

    @Override
    public Page<MonitorDeviceInfoVo> queryList(PageQuery pageQuery, String deviceName) {

        Page<MonitorDeviceInfoVo> list = monitorDeviceMapper.queryList(pageQuery.build(), deviceName);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateByBo(MonitorDeviceInfoBo bo, MultipartFile image) {
        //拿到旧的设备编号
        MonitorDeviceInfo oldMonitorDevice = monitorDeviceMapper.selectById(bo.getDeviceId());
        //修改编号
        if (!oldMonitorDevice.getDeviceCode().equals(bo.getDeviceCode())) {
            //首先查询设备编号是否唯一
            QueryWrapper<MonitorDeviceInfo> wrapperNew = new QueryWrapper<>();
            wrapperNew.eq("deviceCode", bo.getDeviceCode());
            wrapperNew.notIn("deviceId", bo.getDeviceId());
            MonitorDeviceInfo monitorDeviceInfo = monitorDeviceMapper.selectOne(wrapperNew);
            if (monitorDeviceInfo != null) {
                return R.fail("修改的设备编号不唯一");
            }
            //修改设备下面部件的deviceId
            PartInfo deviceCode = partInfoMapper.selectOne(new QueryWrapper<PartInfo>().eq("deviceCode", bo.getDeviceCode()));
            if (deviceCode != null) {
                Boolean flag = partInfoMapper.updateDeviceID(oldMonitorDevice.getDeviceCode(), bo.getDeviceCode()) > 0;
                if (!flag) {
                    return R.fail("更新部件失败");
                }
            }

        }
        //编号没改，查看是否修改图片
        if (!(image == null)) {
            //改,上传图片，修改url
            // 文件限制10M
            long size = image.getSize();
            if (size > MAX_POST_SIZE) {
                return R.fail("文件不能超过100M");
            }
            String oriFileName = image.getOriginalFilename();
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")) +
                    UUID.randomUUID().toString().substring(0,8)+ oriFileName.substring(oriFileName.lastIndexOf("."));

            String folder = path + File.separator + "monitordevice" + File.separator + time + File.separator;
            if (!FileUtil.exist(folder)) {
                FileUtil.mkdir(folder);
            }
            String path = folder + fileName;
            File file = new File(path);
            if (FileUtil.exist(file)) {
                return R.fail("文件已存在");
            }
            File file1 = null;
            try {
                file1 = FileUtil.writeBytes(image.getBytes(), path);
            } catch (IOException e) {
                e.printStackTrace();
                return R.fail("文件写入错误");
            }
            if (file1.length() < 0) {
                return R.fail("文件上传失败");
            }
            // 上传并返回新文件名称
            String url = url_prefix + "/monitordevice/" + time + "/" + fileName;
            bo.setImageUrl(url);
        }
        MonitorDeviceInfo monitorDeviceInfo = BeanUtil.toBean(bo, MonitorDeviceInfo.class);
        Boolean result = monitorDeviceMapper.updateById(monitorDeviceInfo) > 0;
        if (result) {
            return R.ok("更新成功");
        }
        return R.fail("更新失败");
    }

    @Override
    public R deleteByDeviceCode(String deviceCode) {
        //查看该设备下是否有数据
        List<PartInfo> partInfos = partInfoMapper.selectList(new QueryWrapper<PartInfo>().eq("deviceCode", deviceCode));
        if (partInfos.size() > 0) {
            return R.fail("该设备下已绑定部件，请先删除部件！");
        }
        Boolean flag = monitorDeviceMapper.delete(new QueryWrapper<MonitorDeviceInfo>().eq("deviceCode", deviceCode)) > 0;
        if(flag){
            return R.ok("更新成功");
        }
        return R.fail("更新失败");
    }
}
