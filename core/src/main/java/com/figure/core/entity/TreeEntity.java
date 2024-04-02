package com.figure.core.entity;

import java.util.List;

public abstract class TreeEntity<TreeEntity> {

    public abstract List<TreeEntity> getChildren();

    public abstract String getEntityId();

    public abstract String getEntityParentId();

    public abstract void setId(Integer id);

    public abstract void setParentId(Integer parentId);

    public abstract Integer getId();

}
