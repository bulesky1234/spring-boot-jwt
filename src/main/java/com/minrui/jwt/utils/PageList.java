package com.minrui.jwt.utils;


import java.util.List;

public class PageList<T> {

    private int pageIndex;

    private int count;

    private List<T> data;

    public PageList(int pageIndex, int count, List<T> data) {
        this.pageIndex = pageIndex;
        this.count = count;
        this.data = data;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getCount() {
        return count;
    }

    public List<T> getData() {
        return data;
    }
}
