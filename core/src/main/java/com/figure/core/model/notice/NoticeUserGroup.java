package com.figure.core.model.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notice_user_group")
public class NoticeUserGroup implements Serializable {


    /**
     * 用户组ID
     */
    @TableId(value = "groupId", type = IdType.AUTO)
    private Integer groupId;

    /**
     * 用户组名称
     */
    @TableField("groupName")
    private String groupName;

    /**
     * 用户组描述
     */
    @TableField("mark")
    private String mark;

    /**
     * 关联用户ID
     */
    @TableField("userIds")
    private String userIds;


}
