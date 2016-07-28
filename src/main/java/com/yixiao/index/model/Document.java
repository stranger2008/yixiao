package com.yixiao.index.model;

import java.io.Serializable;

/**
 * 文档
 * Created by lilianglin on 2016/7/28.
 */
public class Document implements Serializable{

    private long docId;

    /**
     * 该文档中出现的最大词频
     */
    private int maxTf;

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public int getMaxTf() {
        return maxTf;
    }

    public void setMaxTf(int maxTf) {
        this.maxTf = maxTf;
    }

    @Override
    public String toString() {
        return "Document{" +
                "docId=" + docId +
                ", maxTf=" + maxTf +
                '}';
    }
}
