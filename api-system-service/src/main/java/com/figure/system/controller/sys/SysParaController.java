package com.figure.system.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.constant.HttpStatusConstant;
import com.figure.core.helper.GroupHelper;
import com.figure.core.model.sys.SysPara;
import com.figure.core.service.spectrum.ISpectrumRecordMessageService;
import com.figure.core.service.sys.ISysParaService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统参数表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-03-22
 */
@Controller
@RequestMapping("/para")
@Api(value = "全局参数设置相关接口" , tags = "全局参数设置相关接口")
public class SysParaController{

    @Autowired
    ISysParaService sysParaService;

    /**
     * 获取分组全局参数列表
     * @param
     * @return 全局参数分组列表
     */
    @RequestMapping("/queryPara")
    @ResponseBody
    @ApiOperation(value = "获取分组全局参数列表" , notes = "获取分组全局参数列表、页面编辑模式为分组编辑")
    public Map<String, List<SysPara>> queryPara(HttpServletRequest request, HttpServletResponse response) {
        List<SysPara> list =  sysParaService.list();
        Map<String, List<SysPara>> groupParas = new LinkedHashMap<>();
        GroupHelper.groupParas(list,groupParas);
        return groupParas;
    }
    @Resource
    private ISpectrumRecordMessageService spectrumRecordMessageService;

    /**
     * 保存全局参数
     * @param
     * @return 操作结果 ok：成功
     */
    @PostMapping("/savePara")
    @ResponseBody
    @ApiOperation(value = "批量保存全局参数" , notes = "批量保存全局参数")
    public ResponseEntity savePara(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paras = new Gson().fromJson(request.getReader(), Map.class);
        for (String paraName : paras.keySet()) {
            Object value = paras.get(paraName);
            if(value!=null){
                String paraValue = value.toString();
                QueryWrapper<SysPara> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("paraName", paraName);
                SysPara sysPara = sysParaService.getOne(queryWrapper);
                sysPara.setParaValue(paraValue);
                sysParaService.updateById(sysPara);
            }
            if(paraName.equals("spectrumRecordInterval")){
                if(this.spectrumRecordMessageService.getScheduledFuture().cancel(true)){
                    this.spectrumRecordMessageService.insertSpectrumRecordMessage();
                }
            }
        }
        sysParaService.setRecordParaCache();
        sysParaService.setAlarmParaCache();
        sysParaService.updateCache();
        return new ResponseEntity<String>("ok", HttpStatus.valueOf(HttpStatusConstant.OK_CODE));
    }

    /**
     * 根据参数名称获取参数信息
     * @param paraName 参数名称
     * @return 参数信息
     */
    @RequestMapping("/queryParaByParaName")
    @ResponseBody
    @ApiOperation(value = "根据参数名称获取参数信息" , notes = "根据参数名称获取参数信息")
    public SysPara queryParaByParaName(String paraName) {
        QueryWrapper<SysPara> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paraName",paraName);
        List<SysPara> list =  sysParaService.list(queryWrapper);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取平台名称
     * @return 参数信息
     */
    @RequestMapping("/getPlatformNameAndLogo")
    @ResponseBody
    @ApiOperation(value = "获取平台名称" , notes = "获取平台名称")
    public List<SysPara> getPlatformNameAndLogo() {
        QueryWrapper<SysPara> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("paraName", new String[]{"platformName", "logo"});
        List<SysPara> list =  sysParaService.list(queryWrapper);
        return list;
    }

    /**
     * 根据参数名称获取参数值
     * @param paraName 参数名称
     * @return 参数值
     */
    @RequestMapping("/getParaValueByParaName")
    @ResponseBody
    @ApiOperation(value = "根据参数名称获取参数值" , notes = "根据参数名称获取参数值")
    public String getParaValueByParaName(String paraName) {
       return sysParaService.getParamByName(paraName);
    }

}
