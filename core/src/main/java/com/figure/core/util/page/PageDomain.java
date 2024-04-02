package com.figure.core.util.page;

import com.figure.core.constant.PageConstants;
import com.figure.core.util.StringUtils;
import com.figure.core.util.text.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分页查询参数（为空或其他字符时不进行分页）
 * </p>
 *
 * @author feather
 * @date 2021-04-06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "分页参数", description = "分页查询所需参数（为空或其他字符时不进行分页）")
public class PageDomain {

    @ApiModelProperty(value = "当前记录起始索引", example = "1")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示记录数", example = "10")
    private Integer pageSize;

    @ApiModelProperty(value = "排序列（多个用逗号分隔）", example = "createTime")
    private String orderByColumn;

    @ApiModelProperty(value = "排序的方向(desc或asc)，默认倒序", example = "desc")
    private String isAsc;

    public String getIsAsc() {
        return ("asc".equals(isAsc) || "desc".equals(isAsc)) ? isAsc : PageConstants.DEFAULT_IS_ASC;
    }

    /**
     * 获取排序sql
     */
    @ApiModelProperty(hidden = true)
    public String getOrderBy() {
        return StringUtils.isEmpty(orderByColumn) ? "" : orderByColumn + " " + getIsAsc();
    }

    /**
     * 返回排序列（默认采用下划线格式）
     */
    public String[] columns() {
        String[] columns = Convert.toStrArray(orderByColumn);
//        for (int i = 0; i < columns.length; i++) {
//            columns[i] = StringUtils.underlineToHump(columns[i]);
//        }
        return columns.length > 0 ? columns : new String[0];
    }

}
