package com.minrui.jwt.utils;

import java.util.List;

/**
 * Created by Gale on 1/17/18.
 */

public class CountResult<T> {
    private int count;
    private List<T> data;

    public CountResult(int count, List data) {
        this.count = count;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
