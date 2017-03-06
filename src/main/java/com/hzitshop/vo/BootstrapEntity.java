package com.hzitshop.vo;

/**
 * Created by 冼耀基 on 2016/11/10.
 */
public class BootstrapEntity {
    private String sort;//排序字段名称
    private String order;//排序规则 'desc' 'asc'
    private Integer offset;//每页数据条数
    private Integer limit;//分页页数
    private String search;//搜索
    private String condition;//搜索条件
    private String value;//搜索值
    private String searchValue;
    private String isconsultant;
    public BootstrapEntity(){}

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getOffset() {
        return offset;
    }
     
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsconsultant() {
        return isconsultant;
    }

    public void setIsconsultant(String isconsultant) {
        this.isconsultant = isconsultant;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public String toString() {
        return "BootstrapEntity{" +
                "sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", search='" + search + '\'' +
                '}';
    }
}
