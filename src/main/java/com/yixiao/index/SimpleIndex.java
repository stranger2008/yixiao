package com.yixiao.index;

import com.yixiao.crawler.common.CrawlerConstants;
import com.yixiao.index.model.Document;
import com.yixiao.index.model.Posting;
import com.yixiao.util.WordSegment;
import org.apache.commons.io.FileUtils;

import com.yixiao.util.FileUtil;
import java.io.*;
import java.util.*;

/**
 * 创建索引
 * Created by lilianglin on 2016/7/22.
 */
public class SimpleIndex {

    //倒排列表
    private Map<String,List<Posting>> postingMap = new HashMap<String, List<Posting>>();

    //文档信息
    private Map<Long,Document> docIdDocMap = new HashMap<Long, Document>();

    public static void main(String[] args) throws IOException {
        SimpleIndex simpleIndex = new SimpleIndex();
        simpleIndex.createIndex(CrawlerConstants.pagePath);
    }

    public void createIndex(String fileDir){
        File dir = new File(fileDir);
        File[] files = dir.listFiles();
        //构造索引对象
        for(int i=0;i<files.length;i++){
            String fileCont = null;
            try {
                fileCont = FileUtils.readFileToString(files[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String,Integer> map = WordSegment.getWordFreq(fileCont);
            packIndexFile(map, i+1);
        }
        //持久化索引对象
        FileUtil.writeObject(postingMap,CrawlerConstants.indexPath + CrawlerConstants.postingList);
        FileUtil.writeObject(docIdDocMap,CrawlerConstants.indexPath + CrawlerConstants.documentName);
    }

    /**
     * 构造倒排列表、文档对象
     * @param map
     * @param docId
     */
    public void packIndexFile(Map<String,Integer> map,long docId){
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            //构造倒排列表
            Posting posting = new Posting();
            posting.setDocId(docId);
            posting.setFreq(new Integer(value));
            List<Posting> postingList;
            if(postingMap.get(key) == null){
                postingList = new ArrayList<Posting>();
            }else{
                postingList = postingMap.get(key);
            }
            postingList.add(posting);
            postingMap.put(key,postingList);

            //构造document对象，设置文档中词频率的最大值
            Document document = docIdDocMap.get(docId);
            if(document != null){
                int freqTmp = document.getMaxTf();
                if(freqTmp < value){
                    document.setMaxTf(value);
                }
            }else{
                Document docTmp = new Document();
                docTmp.setDocId(docId);
                docTmp.setMaxTf(value);
                docIdDocMap.put(docId,docTmp);
            }
        }
    }



}
