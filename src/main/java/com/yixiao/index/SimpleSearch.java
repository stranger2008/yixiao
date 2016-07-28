package com.yixiao.index;

import com.yixiao.crawler.common.CrawlerConstants;
import com.yixiao.index.model.Document;
import com.yixiao.index.model.Posting;
import com.yixiao.util.FileUtil;
import com.yixiao.util.WordSegment;
import org.apache.commons.collections.CollectionUtils;

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
        simpleSearch.search("笔记本电脑电话手机苹果阿里巴巴");
    }

    public void search(String query){
        //获取倒排列表
        Map<String,List<Posting>> postingMap = (Map<String, List<Posting>>) FileUtil.getObject(CrawlerConstants.indexPath + CrawlerConstants.postingList);
        Map<Long,Document> docIdDocMap = (Map<Long,Document>)FileUtil.getObject(CrawlerConstants.indexPath + CrawlerConstants.documentName);
        //给查询字符串分词
        Map<String,Integer> tfMap = WordSegment.getWordFreq(query);
        //计算词频
        Map<String,Float> tfFreqMap = new HashMap<String, Float>();
        for (Map.Entry<String, Integer> entry : tfMap.entrySet()) {
            String key = entry.getKey();
            List<Posting> postingList = postingMap.get(key);
            System.out.println(key + "===" + postingList);
            if(CollectionUtils.isEmpty(postingList)){
                continue;
            }
            for(Posting posting : postingList){
                long _docId = posting.getDocId();
                int _freq = posting.getFreq();
                long _docMaxFreq = docIdDocMap.get(_docId).getMaxTf();
                double realFreq = 0.4 + (1 - 0.4) * (_freq / _docMaxFreq);
                float _rf = (float)realFreq;
                System.out.println(realFreq + "====" + _freq + "===" + _docMaxFreq + "====" + _rf);
            }
        }
    }

}
