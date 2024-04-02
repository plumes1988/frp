package com.figure.core.util.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.constant.HttpStatusConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author Frank
 */
@Data
@ToString
@NoArgsConstructor
@ApiModel(value = "分页数据", description = "分页数据")
public class TableDataInfo<T> {

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("列表数据")
    private List<T> data;

    @ApiModelProperty("消息状态码")
    private int code;


    @ApiModelProperty("当前记录起始索引")
    private int current;

    @ApiModelProperty("每页显示记录数")
    private int pageSize;

    @ApiModelProperty("总页数")
    private int totalPage;

    public TableDataInfo(IPage<T> page) {
        this.total = page.getTotal();
        this.data = page.getRecords();
        this.code = HttpStatusConstant.OK_CODE;
        this.current = (int)page.getCurrent();
        this.pageSize = (int)page.getSize();
        this.totalPage = (int)page.getTotal();
    }
}
