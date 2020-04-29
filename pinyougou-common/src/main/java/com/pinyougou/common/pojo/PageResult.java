package com.pinyougou.common.pojo;

import java.io.Serializable;
import java.util.List;
/** 分页结果实体 */
public class PageResult implements Serializable {
    private Long total; //总记录数
    private List<?> rows; //一页的数据

    public PageResult() {
    }

    public PageResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }


    public void setTotal(Long total) {
        this.total = total;
    }


    public List<?> getRows() {
        return rows;
    }


    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
