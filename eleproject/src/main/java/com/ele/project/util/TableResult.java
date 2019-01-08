package com.ele.project.util;

/**
 * 
 * 分页工具类<包含分页的数据列表信息、数据量>
 * @param <T> 泛型
 * @date 2018/09/02 10:28
 * @version 1.0
 * 
 */
public class TableResult<T> {

    /** 数据总条数 */
    private Long total;

    /** 数据列 */
    private T rows;

    /** 无参构造方法 */
    public TableResult() {
        super();
    }

    /** 有参构造方法 */
    public TableResult(Long total, T rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    /**
     * 获取分页信息数据量
     * @return 查询信息量
     */
    public Long getTotal() {
        return total;
    }

    /**
     * 设置分页信息数据量
     * @param total 查询信息量
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * 获取查询数据列表信息
     * @return 查询数据列表信息
     */
    public T getRows() {
        return rows;
    }

    /**
     * 设置查询数据列表信息
     * @param rows 数据列表信息
     */
    public void setRows(T rows) {
        this.rows = rows;
    }

    /**
     * 重写toString方法
     */
    @Override
    public String toString() {
        return "TableResult [total=" + total + ", rows=" + rows + "]";
    }

}
