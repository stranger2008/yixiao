package com.yixiao.index.model;

/**
 * 文档权重
 * Created by lilianglin on 2016/7/29.
 */
public class DocWeight implements Comparable<DocWeight>{
    private Long docId;
    private Double weight;

    public DocWeight() {
    }

    public DocWeight(long docId, Double weight) {
        this.docId = docId;
        this.weight = weight;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "DocWeight{" +
                "docId=" + docId +
                ", weight=" + weight +
                '}';
    }

    /**
     * 按照权重排序
     * @param docWeight
     * @return
     */
    @Override
    public int compareTo(DocWeight docWeight) {
        return this.getWeight().compareTo(docWeight.getWeight());
    }
}
