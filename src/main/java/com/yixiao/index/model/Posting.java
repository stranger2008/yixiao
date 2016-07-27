package com.yixiao.index.model;

import java.io.Serializable;

/**
 * Created by lilianglin on 2016/7/26.
 */
public class Posting implements Serializable {

    private long docId;
    private int freq;

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public String toString() {
        return "Posting{" +
                "docId=" + docId +
                ", freq=" + freq +
                '}';
    }
}
