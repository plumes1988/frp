package com.figure.system.controller;


import com.figure.core.base.BaseController;
import com.figure.core.base.QuerySqlRequest;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.entity.DeviceCurIndicatorValue;
import com.figure.core.service.others.ICommonService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.util.BeanUtils;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.StringUtils;
import io.debezium.util.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.figure.core.constant.ConstantsForSysPara.ALARM_STATUS_INDICATOR_CODE;
import static com.figure.core.constant.ConstantsForSysPara.ONLINE_STATUS_INDICATOR_CODE;

@RestController
@RequestMapping("/commmon")
@Api(value = "公共接口" , tags = "公共接口")
public class CommonController extends BaseController {

    public static final String FILTER_BY_FRONTS = "filter_by_fronts";

    public static final String  FILTER_DEVICE_BY_CONTROLLABLE_FRONTS = "filter_device_by_controllable_fronts";

    public static final String EXCLUDE_ABSTRACT_INDICATORS = "exclude_abstract_indicators";

    public static final String DEVICE_INDICATOR_PARAM_REL_FILL_VALUE_FROM_CACHE = "device_indicator_param_rel_fill_value_from_cache:";

    @Autowired
    ICommonService commonService;

    @Autowired
    ISysParaService sysParaService;

    @GetMapping("/queryTable")
    /**
     * 查询表数据列表
     *
     * @param t 表名
     * @param fields 查询字段，多个字段","分开
     * @param 其他 过滤条件 (column=value)eg.(name=zhangshan)
     * @return 表数据集合
     */
    @ApiOperation(value = "查询表数据" , notes = "查询表数据")
    public List<Map<String, Object>> queryTable(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        String tableName = request.getParameter("t");
        String columns_Sql =  request.getParameter("fields");
        String append_sql =  request.getParameter("other");
        if(StringUtils.isEmpty(columns_Sql)){
            columns_Sql = "*";
        }
        Map<String,String> conditions_map = new HashMap<>();
        while(enumeration.hasMoreElements()) {
            String paraName = enumeration.nextElement();
            String paraValue = request.getParameter(paraName);
            if(!"t".equals(paraName)&&!"fields".equals(paraName)&&!"other".equals(paraName)){
                conditions_map.put(paraName,paraValue);
            }
        }

        List<String> conditions = new ArrayList<>();
        for(String key:conditions_map.keySet()){
            String value = conditions_map.get(key);
            if(value.indexOf(",")!=-1){
                conditions.add(key+" in  ('"+value.replaceAll(",","','")+"')");
            }else if(key.indexOf("lt_")!=-1){
                conditions.add(key.replace("lt_","")+"<='"+value+"'");
            }else if(key.indexOf("gt_")!=-1){
                conditions.add(key.replace("gt_","")+">='"+value+"'");
            }else{
                conditions.add(key+"='"+value+"'");
            }
        }
        String conditions_Sql = String.join(" and ",conditions);
        if(StringUtils.isEmpty(conditions_Sql)){
            conditions_Sql = "1=1";
        }
        if(!StringUtils.isEmpty(append_sql)){
            conditions_Sql += " "+ append_sql;
        }
        return commonService.queryTable(columns_Sql,tableName,conditions_Sql);
    }


    @PostMapping("/querySql")
    /**
     * 查询表数据列表
     *
     * @param querySqlRequest 请求
     * @return 表数据集合
     */
    @ApiOperation(value = "通过sql查询表数据" , notes = "通过sql查询表数据")
    public List<Map<String, Object>> querySql(@RequestBody QuerySqlRequest querySqlRequest,HttpServletRequest request) {
        String sql = querySqlRequest.getSql();
        return specialForDeviceCurIndicatorValueFillProcess(sql,request);
    }

    private List<Map<String, Object>> specialForDeviceCurIndicatorValueFillProcess(String sql,HttpServletRequest request){
        boolean flag = false;
        if(sql.indexOf(DEVICE_INDICATOR_PARAM_REL_FILL_VALUE_FROM_CACHE)!=-1){
            flag = true;
            sql = sql.replace(DEVICE_INDICATOR_PARAM_REL_FILL_VALUE_FROM_CACHE,"");
        }
        sql = processSql(sql,request);
        List<Map<String, Object>> list =  commonService.querySql(sql);
        if(flag){
            for (Map<String, Object> map:list){
                String deviceCode = map.get("deviceCode").toString();
                String indicatorCode = map.get("indicatorCode").toString();
                String key = BusinessUtils.getLiveDeviceIndicatorKey(deviceCode,indicatorCode);
                DeviceCurIndicatorValue deviceCurIndicatorValue = JavaMemoryDb.DEVICE_CUR_INDICATOR_STATUS.get(key);
                if(deviceCurIndicatorValue!=null){
                   map.put("indicatorValue",deviceCurIndicatorValue.getIndicatorValue());
                   map.put("collectTime",deviceCurIndicatorValue.getCollectTime());
                   map.put("updateTime",deviceCurIndicatorValue.getUpdateTime());
                }
                Integer alarmStatus =  BusinessUtils.getDeviceIndicatorAlarmStatusByKey(key);
                map.put("alarmStatus",alarmStatus);
            }
        }
        return list;
    }


    @PostMapping("/querySqls")
    /**
     * 查询表数据列表
     *
     * @param querySqlRequest 请求
     * @return 表数据集合
     */
    @ApiOperation(value = "通过sql查询表数据" , notes = "通过sql查询表数据")
    public List<List<Map<String, Object>>> querySqls(@RequestBody QuerySqlRequest querySqlRequest,HttpServletRequest request) {
        List<List<Map<String, Object>>> results = new ArrayList<>();
        for(String sql:querySqlRequest.getSqls()){
            List<Map<String, Object>> list =  specialForDeviceCurIndicatorValueFillProcess(sql,request);
            results.add(list);
        }
        return results;
    }

    public String processSql(String sql,HttpServletRequest request){
        if(sql.indexOf(FILTER_BY_FRONTS)!=-1){
            List<Integer> frontIds = getPermissionsFrontIds(request,false);
            if(frontIds.size()==0){
                frontIds.add(-1);
            }
            sql = sql.replace(FILTER_BY_FRONTS, Strings.join(",",frontIds));
        }
        if(sql.indexOf(FILTER_DEVICE_BY_CONTROLLABLE_FRONTS)!=-1){
            List<Integer> frontIds = getPermissionsFrontIds(request,true);
            if(frontIds.size()==0){
                frontIds.add(-1);
            }
            sql = sql.replace(FILTER_DEVICE_BY_CONTROLLABLE_FRONTS, Strings.join(",",frontIds));
        }
        if(sql.indexOf(EXCLUDE_ABSTRACT_INDICATORS)!=-1){
            String onlineStatusIndicatorCode = sysParaService.getParamByName(ONLINE_STATUS_INDICATOR_CODE);
            String alarmStatusIndicatorCode = sysParaService.getParamByName(ALARM_STATUS_INDICATOR_CODE);
            List<String> exclude_abstract_indicators = new ArrayList<>();
            if(onlineStatusIndicatorCode!=null){
                exclude_abstract_indicators.add(onlineStatusIndicatorCode);
            }
            if(alarmStatusIndicatorCode!=null){
                exclude_abstract_indicators.add(alarmStatusIndicatorCode);
            }
            if(exclude_abstract_indicators.size()>0){
                sql = sql.replace(EXCLUDE_ABSTRACT_INDICATORS, "'"+Strings.join("','",exclude_abstract_indicators)+"'");
            }
        }

        return sql;
    }

}
