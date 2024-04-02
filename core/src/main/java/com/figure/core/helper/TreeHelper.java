package com.figure.core.helper;

import com.figure.core.entity.TreeEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TreeHelper<T extends TreeEntity>{

    public List<T> listConver2Tree(List<T> list) {
        List<T> treeList = new ArrayList<>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            String parentId = t.getEntityParentId();
            if (parentId==null||("0").equals(parentId)) {
                t.setParentId(0);
                treeList.add(t);
                iterator.remove();
            }
        }
        appendChildren(treeList, list);
        return treeList;
    }



    protected void appendChildren(List<T> parents, List<T> list) {
        List<T> treeList = new ArrayList<>();
        if (parents.size() == 0) {
            return;
        }
        for (T p : parents) {
            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                T t = iterator.next();
                String parentId = t.getEntityParentId();
                if (p.getEntityId().equals(parentId)) {
                    treeList.add(t);
                    p.getChildren().add(t);
                    t.setParentId(p.getId());
                }
            }
        }
        appendChildren(treeList, list);
    }


    public void initRelOfParentAndChildren(List<T> list) {
          for (T t:list){
               String entityParentId= t.getEntityParentId();
               Integer parentId  = getParentIdByEntityParentId(t,list);
               t.setParentId(parentId);
          }
    }

    private Integer getParentIdByEntityParentId(T t,List<T> list) {
        String entityParentId= t.getEntityParentId();
        if(entityParentId==null){
             return 0;
        }
        for (T p:list){
            String entityId= p.getEntityId();
            if(entityParentId.equals(entityId)){
                  return p.getId();
            }
        }
        return 0;
    }
}

