package com.figure.core.helper;

import com.figure.core.entity.TreeEntity;
import com.figure.core.model.sys.SysModuleInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuHelper {

    public static List<Map<String, Object>> coverToAntMenu(List<SysModuleInfo> treeList,List<Map<String, Object>> menus) {
        for (SysModuleInfo moduleInfo:treeList){
            Map<String, Object> map = new HashMap<>();
            map.put("path",moduleInfo.getModuleURL());
            map.put("icon",moduleInfo.getImgUrl());
            map.put("name",moduleInfo.getModuleName());
            map.put("id",moduleInfo.getId());
            map.put("parentId",moduleInfo.getParentId());
            menus.add(map);
            List<TreeEntity> list  = moduleInfo.getChildren();
            List<SysModuleInfo> children = new ArrayList<>();
            for (TreeEntity l:list){
                children.add((SysModuleInfo) l);
            }
            if(children.size()>0){
                List<Map<String, Object>> childrenMenus = new ArrayList<>();
                map.put("children",childrenMenus);
                coverToAntMenu(children,childrenMenus);
            }
        }
        return menus;
    }

}
