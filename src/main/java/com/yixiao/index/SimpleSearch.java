package com.yixiao.index;

import com.yixiao.crawler.common.CrawlerConstants;
import com.yixiao.index.model.Document;
import com.yixiao.index.model.Posting;
import com.yixiao.index.model.SegDocment;
import com.yixiao.util.FileUtil;
import com.yixiao.util.WordSegment;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 搜索
 * Created by lilianglin on 2016/7/27.
 */
public class SimpleSearch {
    public static void main(String[] args) {
        SimpleSearch simpleSearch = new SimpleSearch();
        simpleSearch.search("研究科技含量安装快递");
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

        for (Map.Entry<String, Integer> entry : tfMap.entrySet()) {
            String key = entry.getKey();
            Long termId = termMap.get(key);
            List<Posting> postingList = postingMap.get(termId);
            if(CollectionUtils.isEmpty(postingList)){
                continue;
            }
            System.out.println(key + "===" + postingList);
            //idf
            double idf = Math.log(1 + docNum / postingList.size());
            for(Posting posting : postingList){
                long _docId = posting.getDocId();
                float _rf = getRealFreq(posting,docIdDocMap);
                //tf
                System.out.println(termId + "====" + _docId + "====" + _rf*idf);
            }


        }
    }

    /**
     * 计算出一个词在一个文档中的词频
     * @param posting
     * @param docIdDocMap
     * @return
     */
    public float getRealFreq(Posting posting,Map<Long,Document> docIdDocMap){
        long _docId = posting.getDocId();
        int _freq = posting.getFreq();
        long _docMaxFreq = docIdDocMap.get(_docId).getMaxTf();
        double realFreq = 0.4 + (1 - 0.4) * ( (double)_freq / (double)_docMaxFreq);
        return (float)realFreq;
    }

}
