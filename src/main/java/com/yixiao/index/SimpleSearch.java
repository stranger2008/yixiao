package com.yixiao.index;

import com.yixiao.crawler.common.CrawlerConstants;
import com.yixiao.index.model.Document;
import com.yixiao.index.model.Posting;
import com.yixiao.index.model.SegDocment;
import com.yixiao.index.model.VectorMatrix;
import com.yixiao.util.FileUtil;
import com.yixiao.util.WordSegment;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索
 * Created by lilianglin on 2016/7/27.
 */
public class SimpleSearch {
    public static void main(String[] args) {
        SimpleSearch simpleSearch = new SimpleSearch();
        simpleSearch.search("科学研究煤炭");
    }

    public void search(String query){
        //获取倒排列表
        Map<Long,List<Posting>> postingMap = (Map<Long,List<Posting>>) FileUtil.getObject(CrawlerConstants.indexPath + CrawlerConstants.postingList);
        SegDocment segDocment = (SegDocment)FileUtil.getObject(CrawlerConstants.indexPath + CrawlerConstants.documentName);
        Map<Long,Document> docIdDocMap = segDocment.getDocIdDocMap();
        Map<String,Long> termMap = (Map<String,Long>)FileUtil.getObject(CrawlerConstants.indexPath + CrawlerConstants.termName);
        //给查询字符串分词
        Map<String,Integer> tfMap = WordSegment.getWordFreq(query);
        //文库文档总数
        long docNum = segDocment.getDocNum();

        List<VectorMatrix> vectorMatrixes = new ArrayList<VectorMatrix>();
        for (Map.Entry<String, Integer> entry : tfMap.entrySet()) {
            String key = entry.getKey();
            Long termId = termMap.get(key);
            List<Posting> postingList = postingMap.get(termId);
            if(CollectionUtils.isEmpty(postingList)){
                continue;
            }
            System.out.println(key + "===" + postingList);
            //出现该词的文档数
            int termInDocNum = postingList.size() + 1;
            //idf
            double idf = Math.log(1 + docNum / termInDocNum);
            for(Posting posting : postingList){
                long _docId = posting.getDocId();
                double _rf = getRealFreq(posting,docIdDocMap);
                //tf
                //System.out.println(termId + "====" + _docId + "====" + _rf*idf);
                vectorMatrixes.add(new VectorMatrix(termId,_docId,_rf*idf));
            }

            //query tf
            double realFreq = termFreq(entry.getValue(),getQueryMaxFreq(tfMap));
            //query tf*idf
            //System.out.println(termId + "==== -999 ====" + realFreq*idf);
            vectorMatrixes.add(new VectorMatrix(termId,-1L,realFreq*idf));
        }

        //矩阵翻转
        Map<Long,List<VectorMatrix>> vectorMap = new HashMap<Long, List<VectorMatrix>>(tfMap.size());
        for(VectorMatrix vectorMatrix : vectorMatrixes){
            long __docId = vectorMatrix.getDocId();
            List<VectorMatrix> vectorMatrixList;
            if(vectorMap.get(__docId) != null){
                vectorMatrixList = vectorMap.get(__docId);
            }else{
                vectorMatrixList = new ArrayList<VectorMatrix>();
                vectorMap.put(__docId,vectorMatrixList);
            }
            vectorMatrixList.add(vectorMatrix);
        }
        System.out.println(vectorMap);

        //计算文档权重
        List<VectorMatrix> queryMatrixes = vectorMap.get(-1L);
        Map<Long,Double> docWeightMap = new HashMap<Long, Double>(queryMatrixes.size());
        for (Map.Entry<Long,List<VectorMatrix>> entry : vectorMap.entrySet()) {
            List<VectorMatrix> docMatrixes = entry.getValue();
            Long _docId = entry.getKey();
            if(_docId == -1L){
                continue;
            }
            setDocWeight(docWeightMap,_docId,queryMatrixes,docMatrixes);
        }



    }

    public void setDocWeight(Map<Long,Double> docWeightMap,long docId,List<VectorMatrix> queryMatrixes, List<VectorMatrix> docMatrixes){
        double weight = 0D;
        //分母
        double denominator = 0D,deLeft = 0D,deRight = 0D;
        //分子
        double molecule = 0D;
        for(VectorMatrix vectorMatrix : queryMatrixes){
            double qTfidf = vectorMatrix.getTfidf();
            long qTermId = vectorMatrix.getTermId();
            molecule += qTfidf * getDocMatrixesTfidf(qTermId,docMatrixes);
            deLeft += qTfidf * qTfidf;
        }
        deLeft = Math.sqrt(deLeft);
        for(VectorMatrix vectorMatrix : docMatrixes){
            double qTfidf = vectorMatrix.getTfidf();
            deRight += qTfidf * qTfidf;
        }
        deRight = Math.sqrt(deRight);
        denominator = deLeft * deRight;
        weight = molecule / denominator;
        docWeightMap.put(docId,weight);
    }

    /**
     * 根据termId获取指定文档里对应的词tfidf值
     * @param termId
     * @param docMatrixes
     * @return
     */
    public double getDocMatrixesTfidf(long termId,List<VectorMatrix> docMatrixes){
        if(CollectionUtils.isEmpty(docMatrixes)){
            return 0D;
        }
        for(VectorMatrix vectorMatrix : docMatrixes){
            if(termId == vectorMatrix.getTermId()){
                return vectorMatrix.getTfidf();
            }
        }
        return 0D;
    }

    /**
     * 获取查询字符串中最大词频数
     * @param tfMap
     * @return
     */
    public long getQueryMaxFreq(Map<String,Integer> tfMap){
        int tmp = 0;
        for (Map.Entry<String, Integer> entry : tfMap.entrySet()) {
            int queryTermFreq = entry.getValue();
            if( queryTermFreq > tmp ){
                tmp = queryTermFreq;
            }
        }
        return tmp;
    }

    /**
     * 计算出一个词在一个文档中的词频
     * @param posting
     * @param docIdDocMap
     * @return
     */
    public double getRealFreq(Posting posting,Map<Long,Document> docIdDocMap){
        long _docId = posting.getDocId();
        int _freq = posting.getFreq();
        long _docMaxFreq = docIdDocMap.get(_docId).getMaxTf();
        double realFreq = termFreq(_freq,_docMaxFreq);
        return realFreq;
    }

    /**
     * 词频算法
     * @param _freq
     * @param _docMaxFreq
     * @return
     */
    public double termFreq(int _freq,long _docMaxFreq){
        return 0.4 + (1 - 0.4) * ( (double)_freq / (double)_docMaxFreq);
    }

}
