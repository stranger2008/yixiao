package com.yixiao.index;

import com.yixiao.index.model.Posting;
import org.apache.commons.io.FileUtils;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import com.yixiao.util.FileUtil;
import java.io.*;
import java.util.*;

/**
 * Created by lilianglin on 2016/7/22.
 */
public class SimpleIndex {

    public static void main(String[] args) throws IOException {
        SimpleIndex simpleIndex = new SimpleIndex();
        Map<String,List<Posting>> mapp = simpleIndex.createIndex("D:\\workspace_github\\yixiao\\crawler\\page");
        FileUtil.writeObject(mapp,"D:\\workspace_github\\yixiao\\crawler\\index\\index");
    }

    public Map<String,List<Posting>> createIndex(String fileDir){
        Map<String,List<Posting>> mapp = new HashMap<String, List<Posting>>();
        File dir = new File(fileDir);
        File[] files = dir.listFiles();
        for(int i=0;i<1;i++){
            String fileCont = null;
            try {
                fileCont = FileUtils.readFileToString(files[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String,Integer> map = getWord(fileCont);
            packPosting(map, i+1, mapp);
        }
        return mapp;
    }

    /**
     * 构造倒排列表
     * @param map
     * @param docId
     * @param mapp
     */
    public void packPosting(Map<String,Integer> map,int docId,Map<String,List<Posting>> mapp){
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Posting posting = new Posting();
            posting.setDocId(docId);
            posting.setFreq(new Integer(value));
            List<Posting> postingList;
            if(mapp.get(key) == null){
                postingList = new ArrayList<Posting>();
            }else{
                postingList = mapp.get(key);
            }
            postingList.add(posting);
            mapp.put(key,postingList);
        }

    }

    /**
     * 分词并获取词频
     * @param cont
     * @return
     */
    public Map<String,Integer> getWord(String cont){
        StringReader re = new StringReader(cont);
        IKSegmentation ik = new IKSegmentation(re,true);
        Lexeme lex = null;
        Map<String,Integer> wordMap = new HashMap<String,Integer>();
        try {
            while((lex=ik.next())!=null){
                String word=lex.getLexemeText();
                if (!wordMap.containsKey(word)) {
                    wordMap.put(word, 1);
                }else{
                    wordMap.put(word, wordMap.get(word)+1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordMap;
    }

}
