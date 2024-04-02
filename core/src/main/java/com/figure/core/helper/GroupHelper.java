package com.figure.core.helper;

import com.figure.core.model.sys.SysPara;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupHelper {

    public static void groupParas(List<SysPara> list, Map<String, List<SysPara>> groupParas) {
          Map<String,String> idNameMap = new HashMap<>();
          for (SysPara sysPara:list){
              if("1".equals(sysPara.getParaType())){
                  groupParas.put(sysPara.getRemark(),new ArrayList<SysPara>());
                  idNameMap.put(sysPara.getParaId().toString(),sysPara.getRemark());
              }
          }
          for (SysPara sysPara:list){
            if("2".equals(sysPara.getParaType())){
                if(sysPara.getParentParaId()==null){
                    continue;
                }
                String paraId = sysPara.getParentParaId().toString();
                String remark = idNameMap.get(paraId);
                if(remark!=null && groupParas.get(remark)!=null){
                    groupParas.get(remark).add(sysPara);
                }
            }
          }
    }
}
