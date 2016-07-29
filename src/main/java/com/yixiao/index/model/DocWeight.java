package com.yixiao.index.model;

/**
 * 文档权重
 * Created by lilianglin on 2016/7/29.
 */
public class DocWeight {
    private long docId;
    private double weight;

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "DocWeight{" +
                "docId=" + docId +
                ", weight=" + weight +
                '}';
    }
}
