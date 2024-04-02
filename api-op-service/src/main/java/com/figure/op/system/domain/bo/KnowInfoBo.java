package com.figure.op.system.domain.bo;

import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 知识库对象增改对象
 * @author fsn
 */
@Data
public class KnowInfoBo {

    /**
     * 记录ID
     */
    @NotNull(message = "记录ID不能为空", groups = {EditGroup.class})
    private Integer knowId;

    /**
     * 知识主题
     */
    @NotBlank(message = "知识主题不能为空", groups = {AddGroup.class, EditGroup.class})
    private String topic;

    /**
     * 关键词
     */
    @NotBlank(message = "关键词不能为空", groups = {AddGroup.class, EditGroup.class})
    private String knowKeywords;

    /**
     * 设备ID
     */
    @NotNull(message = "设备ID不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer deviceId;

    /**
     * 知识记录
     */
    @Length(max = 10000, message = "知识记录最大长度10000个字符", groups = {AddGroup.class, EditGroup.class})
    private String content;

}
