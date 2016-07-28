package com.yixiao.index.model;

/**
 * 词元
 * Created by lilianglin on 2016/7/28.
 */
public class Term {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
