package com.atguigu.atcrowdfunding.util;

import java.util.List;

/**
 * @Auther: wuwenshuai
 * @Date: 2018/9/18 17:24
 * @Description:
 */
public class PageResult {

    private Integer total;//总条数
    private List list;//总记录数

    public PageResult(Integer total, List list) {
        this.total = total;
        this.list = list;
    }

    public PageResult() {
    }

    public Integer getTotal() {
        return total;
    }

    public List getList() {
        return list;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setList(List list) {
        this.list = list;
    }
}
