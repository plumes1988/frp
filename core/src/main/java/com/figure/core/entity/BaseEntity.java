package com.figure.core.entity;

import java.util.Date;


/**
 * Company : Figure Information Technology Co. Ltd.
 * 说明：基类实体
 * @author xiaodongx
 * @create Jun 30, 2009 6:13:09 PM
 */
public class BaseEntity {
	
	public static final Integer DELETED_YES = 1; //已删除
	
	public static final Integer DELETED_No = 0; //未删除
	
	private transient Integer pageSize = 10;	//每页显示记录数(default)
	
	private transient Integer pageNo = 1;		//页码(default)
	
	private Integer status;			//状态
	
	private Date createTime;		//创建时间
	
	private Date updateTime;		//最后更新时间
	
	private Integer createUserId;	//创建者id
	
	private Integer updateUserId;	//最后最新者Id
	
	private String optLog;			//操作变更日志
	
	private Integer deleted;       //删除状态
	
	private Date deleteTime;     //删除时间

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getOptLog() {
		return optLog;
	}

	public void setOptLog(String optLog) {
		this.optLog = optLog;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
