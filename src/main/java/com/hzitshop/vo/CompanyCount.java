package com.hzitshop.vo;


/**
 * Created by xianyaoji on 2017/3/6.
 */
public class CompanyCount {
    private int value;       //数量
    private String name;//名称
    public CompanyCount(){
        
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyCount{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
