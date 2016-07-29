package com.yixiao.index.model;

/**
 * 向量矩阵
 * Created by lilianglin on 2016/7/29.
 */
public class VectorMatrix {

    //词ID
    private long termId;
    //文档ID
    private long docId;
    //tfidf值
    private double tfidf;

    public VectorMatrix() {
    }

    public VectorMatrix(long termId, long docId, double tfidf) {
        this.termId = termId;
        this.docId = docId;
        this.tfidf = tfidf;
    }

    public long getTermId() {
        return termId;
    }

    public void setTermId(long termId) {
        this.termId = termId;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public double getTfidf() {
        return tfidf;
    }

    public void setTfidf(double tfidf) {
        this.tfidf = tfidf;
    }

    @Override
    public String toString() {
        return "VectorMatrix{" +
                "termId=" + termId +
                ", docId=" + docId +
                ", tfidf=" + tfidf +
                '}';
    }
}
