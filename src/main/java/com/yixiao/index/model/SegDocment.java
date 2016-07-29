package com.yixiao.index.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lilianglin on 2016/7/29.
 */
public class SegDocment implements Serializable{

    private Map<Long,Document> docIdDocMap = new HashMap<Long, Document>();

    private long docNum;

    public Map<Long, Document> getDocIdDocMap() {
        return docIdDocMap;
    }

    public void setDocIdDocMap(Map<Long, Document> docIdDocMap) {
        this.docIdDocMap = docIdDocMap;
    }

    public long getDocNum() {
        return docNum;
    }

    public void setDocNum(long docNum) {
        this.docNum = docNum;
    }

    public SegDocment() {
    }

    @Override
    public String toString() {
        return "SegDocment{" +
                "docIdDocMap=" + docIdDocMap +
                ", docNum=" + docNum +
                '}';
    }
}
