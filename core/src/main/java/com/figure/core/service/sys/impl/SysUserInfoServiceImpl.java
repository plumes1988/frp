package com.figure.core.service.sys.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.PermissionConstants;
import com.figure.core.model.sys.*;
import com.figure.core.repository.sys.SysUserInfoMapper;
import com.figure.core.service.sys.*;
import io.debezium.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.figure.core.constant.Constants.DATATABLE_ISENABLE_ENABLED;
import static com.figure.core.constant.PermissionConstants.NORMAL_USER;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>
 * 用户信息Service业务层处理
 * </p>
 * 
 * @author feather
 * @date 2021-03-18 16:07:45
 */
@Service
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoMapper, SysUserInfo> implements ISysUserInfoService {

    @Resource
    private ISysUserRoleRelService sysUserRoleRelService;

    @Resource
    private ISysRoleInfoService sysRoleInfoService;

    @Resource
    ISysModuleInfoService sysModuleInfoService;

    @Resource
    ISysModulePermissionService  sysModulePermissionService;

    @Resource
    ISysRolePermissionRelService  sysRolePermissionRelService;

    @Resource
    private ISysRoleFrontStationRelService sysRoleFrontStationRelService;

    @Autowired
    AuthService authService;

    @Override
    public void fetchRoleInfo(SysUserInfo sysUserInfo){

        List<Integer> roleIds = new ArrayList<>();
        List<String> roleNames = new ArrayList<>();

        QueryWrapper<SysUserRoleRel> queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId",sysUserInfo.getUserId());
        List<SysUserRoleRel> sysUserRoleRels = sysUserRoleRelService.list(queryWrapper);

        if(sysUserRoleRels!=null&&sysUserRoleRels.size()>0){
            for (int i = 0; i < sysUserRoleRels.size(); i++) {
                Integer roleId = sysUserRoleRels.get(i).getRoleId().intValue();
                roleIds.add(roleId);
                SysRoleInfo sysRoleInfo = sysRoleInfoService.getById(roleId);
                if(sysRoleInfo!=null){
                   roleNames.add(sysRoleInfo.getRoleName());
                }
            }

        }
        sysUserInfo.setRoleIds(roleIds);
        sysUserInfo.setRoleNames(Strings.join(",",roleNames));
    }

    @Override
    public void addRoleInfo(SysUserInfo sysUserInfo){

        List<Integer> roleIds =  sysUserInfo.getRoleIds();

        if(roleIds==null){
            return;
        }

        for (int i = 0; i < roleIds.size(); i++) {

            int roleId = roleIds.get(i);

            SysUserRoleRel sysUserRoleRel = new SysUserRoleRel();

            sysUserRoleRel.setRoleId((long)roleId);

            sysUserRoleRel.setUserId(sysUserInfo.getUserId());

            sysUserRoleRelService.save(sysUserRoleRel);
        }

    }

    @Override
    public void updateRoleInfo(SysUserInfo sysUserInfo){

        QueryWrapper<SysUserRoleRel> queryWrapper = new QueryWrapper();

        queryWrapper.eq("userId",sysUserInfo.getUserId());

        sysUserRoleRelService.remove(queryWrapper);

        addRoleInfo(sysUserInfo);
    }

    @Override
    public List<SysModuleInfo> getUserModuleInfos(Integer userId) {

        SysUserInfo sysUserInfo = getById(userId);
        fetchRoleInfo(sysUserInfo);

        Set<String> moduleIds = new HashSet();

        List<Integer> roleIds =  sysUserInfo.getRoleIds();

        for (int i = 0; i < roleIds.size(); i++) {

            int roleId = roleIds.get(i);

            QueryWrapper<SysRolePermissionRel> queryWrapper = new QueryWrapper();
            queryWrapper.eq("roleId",roleId);

            List<SysRolePermissionRel> sysRolePermissionRels =  sysRolePermissionRelService.list(queryWrapper);

            List<String> permissionIds = new ArrayList();
            for(SysRolePermissionRel sysRolePermissionRel:sysRolePermissionRels){
                permissionIds.add(sysRolePermissionRel.getPermissionId().toString());
            }
            if(permissionIds.size()==0){
                permissionIds.add("-1");
            }

            QueryWrapper<SysModulePermission> queryWrapper_01 = new QueryWrapper();
            queryWrapper_01.in("id",permissionIds);
            queryWrapper_01.eq("permissionCode", PermissionConstants.PAGE);

            List<SysModulePermission> sysModulePermissions =  sysModulePermissionService.list(queryWrapper_01);


            for(SysModulePermission sysModulePermission:sysModulePermissions){
                moduleIds.add(sysModulePermission.getModuleId().toString());
            }

        }




        if(moduleIds.size()==0){
            moduleIds.add("-1");
        }

        QueryWrapper<SysModuleInfo> queryWrapper_02 = new QueryWrapper<>();
        queryWrapper_02.eq("isEnable",DATATABLE_ISENABLE_ENABLED).eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);

        if(sysUserInfo.getType() == NORMAL_USER){
            queryWrapper_02.in("id",moduleIds);
        }

        queryWrapper_02.orderByAsc("sort");
        List<SysModuleInfo> list =  sysModuleInfoService.list(queryWrapper_02);
        for (SysModuleInfo sysModuleInfo:list){
            sysModuleInfo.setModuleURL(sysModuleInfo.getModuleURL()==null?"":sysModuleInfo.getModuleURL());
        }


        Map<Integer,SysModuleInfo> listWithParent = new HashMap();

        for(SysModuleInfo sysModuleInfo:list){
            fetchParentModuleInfo(sysModuleInfo,listWithParent);
        }

        List<SysModuleInfo> sysModuleInfos = listWithParent.values().stream().collect(toCollection(ArrayList::new));


        Collections.sort(sysModuleInfos, new Comparator<SysModuleInfo>() {
            @Override
            public int compare(SysModuleInfo o1, SysModuleInfo o2) {
                int value = 0;
                int other = 0;
                if(o1.getSort()!=null){
                    value = o1.getSort();
                }
                if(o2.getSort()!=null){
                    other = o2.getSort();
                }
                if (value < other)
                    return -1;
                else if (value >= other)
                    return 1;
                else
                    return 0;
            }
        });


        return sysModuleInfos;
    }

    private void fetchParentModuleInfo(SysModuleInfo sysModuleInfo,Map<Integer,SysModuleInfo> listWithParent) {
            listWithParent.put(sysModuleInfo.getId(),sysModuleInfo);
            Integer parentId =  sysModuleInfo.getParentId();
            if(parentId!=0){
                SysModuleInfo parentSysModuleInfo = sysModuleInfoService.getById(parentId);
                if(parentSysModuleInfo.getIsDelete()==com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED&&parentSysModuleInfo.getIsEnable()==1){
                    fetchParentModuleInfo(parentSysModuleInfo,listWithParent);
                }
            }
    }

    @Override
    public List<Integer> getFrontIds(SysUserInfo sysUserInfo,boolean onlyControllable) {
        Set<Integer> frontIds = new HashSet<>();
        fetchRoleInfo(sysUserInfo);
        List<Integer> roleIds =  sysUserInfo.getRoleIds();

        for (int i = 0; i < roleIds.size(); i++) {

            int roleId = roleIds.get(i);

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("roleId",roleId);
            if(onlyControllable){
               queryWrapper.eq("controllable",1);
            }
            List<SysRoleFrontStationRel> rels = sysRoleFrontStationRelService.list(queryWrapper);
            List<Integer> list0 = rels.stream().map(SysRoleFrontStationRel::getFrontId).collect(Collectors.toList());
            frontIds.addAll(list0);
        }
        Object[] list1 = frontIds.toArray();

        List<Integer> list2 = new ArrayList<>();
        for (Object obj : list1) {
            if (obj instanceof Integer) {
                list2.add((Integer) obj);
            }
        }

        return list2;
    }

    @Override
    public SysUserInfo getUserInfoByHttpRequest(HttpServletRequest request) {
        String jwt = request.getAttribute("jwt").toString();
        SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);
        tUserInfo = this.getById(tUserInfo.getUserId());
        return tUserInfo;
    }

}