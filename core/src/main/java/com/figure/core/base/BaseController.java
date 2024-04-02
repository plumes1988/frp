package com.figure.core.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.entity.AjaxResult;
import com.figure.core.entity.AjaxResult.Type;
import com.figure.core.model.audit.AuditAlarmInfo;
import com.figure.core.model.front.FrontStationInfo;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.service.front.IFrontStationInfoService;
import com.figure.core.service.sys.ISysUserInfoService;
import com.figure.core.service.sys.impl.AuthService;
import com.figure.core.util.DateUtils;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.PageDomain;
import com.figure.core.util.page.TableDataInfo;
import com.figure.core.util.page.TableSupport;
import com.figure.core.util.sql.SqlUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.eventbus.EventBus;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

import static com.figure.core.constant.PermissionConstants.SUPER_ADMIN;

/**
 * web层通用数据处理
 *
 * @author feather
 */
@Data
@PropertySources({
        @PropertySource("classpath:app.properties")
})
public class BaseController {

    @Value("${monitorService_url}")
    protected String monitorService_url;

    @Resource
    protected ISysUserInfoService sysUserInfoService;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected AuthService authService;

    @Autowired
    IFrontStationInfoService frontStationInfoService;

    /**
     * 初始化新增用户、时间
     */
    public void initCreateProps(BaseModel model){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String jwt = request.getAttribute("jwt").toString();
        SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);
        model.setCreateUserId(tUserInfo.getUserId().intValue());
        model.setCreateTime(new Date());
    }

    /**
     * 初始化更新用户、时间
     */
    public void initUpdateProps(BaseModel model){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String jwt = request.getAttribute("jwt").toString();
        SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);
        model.setUpdateUserId(tUserInfo.getUserId().intValue());
        model.setUpdateTime(new Date());
    }


    public UpdateWrapper  getSoftDeleteUpdateWrapperByIds(String idName,String ids,Integer isDelete){
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.in(idName,Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids)));
        updateWrapper.set("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_DELETED);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String jwt = request.getAttribute("jwt").toString();
        SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);
        updateWrapper.set("updateUserId",1);
        updateWrapper.set("updateTime",new Date());
        return updateWrapper;
    }

    /**
     * 初始化用户信息
     */
    public void initUserInfo(List objs,String ...userFields){
        for(Object obj:objs){
            for(String userField:userFields){

                Field field_id =  ReflectionUtils.findField(obj.getClass(),userField);

                field_id.setAccessible(true);

                Integer userId = (Integer)ReflectionUtils.getField(field_id, obj);

                String token = sysUserInfoService.getById(userId).getUserName();

                Field field_name =  ReflectionUtils.findField(obj.getClass(),userField.replace("UserId","UserName"));

                field_name.setAccessible(true);

                ReflectionUtils.setField(field_name,obj,token);

            }
        }
    }


    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });

    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 处理返回分页数据
     */
    protected <T> TableDataInfo<T> toPageResult(List<T> records) {
        TableDataInfo<T> tableInfo = new TableDataInfo<>();
        tableInfo.setCode(0);
        PageInfo<T> pageInfo = new PageInfo<>(records);
        tableInfo.setData(pageInfo.getList());
        tableInfo.setCurrent(pageInfo.getPageNum());
        tableInfo.setPageSize(pageInfo.getPageSize());
        tableInfo.setTotal(pageInfo.getTotal());
        tableInfo.setTotalPage(pageInfo.getPages());
        return tableInfo;
    }

    /**
     * 处理返回分页数据
     */
    protected <T> TableDataInfo<T> toPageResult(List<T> records,Integer pageNo, Integer pageSize, Integer count) {
        TableDataInfo<T> tableInfo = new TableDataInfo<>();
        if(pageNo==null){
            tableInfo.setCode(0);
            tableInfo.setData(records);
            tableInfo.setCurrent(1);
            tableInfo.setPageSize(records.size());
            tableInfo.setTotal(records.size());
            tableInfo.setTotalPage(1);
        }else{
            tableInfo.setCode(0);
            tableInfo.setData(records);
            tableInfo.setCurrent(pageNo);
            tableInfo.setPageSize(pageSize);
            tableInfo.setTotal(count);
            tableInfo.setTotalPage((count+pageSize-1)/pageSize);
        }
        return tableInfo;
    }

    protected Map<String, Object> returnMap(boolean status, Integer pk) {
        Map<String, Object> result = new HashMap<>();
        if (status) {
            result.put("status", 1);
        } else {
            result.put("status", 0);
        }
        result.put("pk", pk);
        return result;
    }

    protected Map<String,Object> returnMap(boolean status,String msg){
        Map<String,Object> result = new HashMap<>();
        if(status){
            result.put("status",1);
        }else{
            result.put("status",0);
        }

        result.put("msg",msg);
        return result;
    }

    protected Map<String,Object> returnMap(boolean status,String msg, Object data){
        Map<String,Object> result = new HashMap<>();
        if(status){
            result.put("status",1);
        }else{
            result.put("status",0);
        }

        result.put("msg",msg);
        result.put("data", data);
        return result;
    }

    protected Map<String, Object> returnMap(boolean status) {
        Map<String, Object> result = new HashMap<>();
        if (status) {
            result.put("status", 1);
        } else {
            result.put("status", 0);
        }
        result.put("pk", 0);
        return result;
    }

    protected Map<String, Object> returnMap(boolean status, Object data) {
        Map<String, Object> result = new HashMap<>();
        if (status) {
            result.put("status", 1);
        } else {
            result.put("status", 0);
        }
        result.put("data", data);
        return result;
    }

    protected Map<String, Object> returnMap(List<Object> data) {
        Map<String, Object> result = new HashMap<>();
        if (data.size() > 0) {
            result.put("status", 1);
        } else {
            result.put("status", 0);
        }
        result.put("data", data);
        result.put("pk", 0);
        return result;
    }


    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected <T> AjaxResult<T> toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected <T> AjaxResult<T> toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public <T> AjaxResult<T> success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public <T> AjaxResult<T> error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public <T> AjaxResult<T> success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public <T> AjaxResult<T> error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public <T> AjaxResult<T> error(Type type, String message) {
        return new AjaxResult<>(type, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    public void permissionsFilterByFrontIds(HttpServletRequest request,QueryWrapper queryWrapper){
        permissionsFilterByFrontIds(request,queryWrapper, "frontId");
    }

    public void permissionsFilterByFrontIds(HttpServletRequest request,QueryWrapper queryWrapper, String frontIdColumn) {
        SysUserInfo sysUserInfo = sysUserInfoService.getUserInfoByHttpRequest(request);
        if(sysUserInfo.getType()!=SUPER_ADMIN){
            List<Integer> frontIds = sysUserInfoService.getFrontIds(sysUserInfo,false);
            if(frontIds.size()==0){
                frontIds.add(-1);
            }
            queryWrapper.in(frontIdColumn,frontIds);
        }
    }

    public List<Integer> getPermissionsFrontIds(HttpServletRequest request,boolean onlyControllable) {
        SysUserInfo sysUserInfo = sysUserInfoService.getUserInfoByHttpRequest(request);
        List<Integer> frontIds = new ArrayList<>();
        if(sysUserInfo.getType()!=SUPER_ADMIN){
            frontIds = sysUserInfoService.getFrontIds(sysUserInfo,onlyControllable);
            return frontIds;
        }else{
            List<Integer> finalFrontIds = frontIds;
            frontStationInfoService.list().forEach(new Consumer<FrontStationInfo>() {
                @Override
                public void accept(FrontStationInfo frontStationInfo) {
                    finalFrontIds.add(frontStationInfo.getFrontId());
                }
            });
            return finalFrontIds;
        }
    }

    public void fillEntityProps(Object obj){

    }

}
