package com.hzitshop.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 冼耀基 on 2016/11/10.
 */
public class BootstrapTable<T> {
    private int total = 0;
    private List<T> rows = new ArrayList<T>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
